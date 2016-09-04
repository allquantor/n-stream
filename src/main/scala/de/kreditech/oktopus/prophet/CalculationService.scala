package de.kreditech.oktopus.prophet

import akka.NotUsed
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{ActorAttributes, Graph, SourceShape, Supervision}
import de.kreditech.oktopus.prophet.ProtocolTypes.{ResourceCalculationProposalRequest, ResourceResponse}

trait CalculationService extends BaseService with Protocol {

  import System._

  protected val standardRoute = path("proposal") {
    post {
      entity(as[ResourceCalculationProposalRequest]) { req =>
        log.info(
          s"Incoming request to process resource calculation for: ${req}")

        val calculationStream = Source
          .single(req)
          .via(calcFlow)
          .runWith(Sink.head.withAttributes(
            ActorAttributes.supervisionStrategy(decider)))

        onSuccess(calculationStream) { resourceResponse =>
          complete(resourceResponse)
        }
      }
    }
  }

  // This is the resource calculation process. By changing the calculation body
  // only the inner body should be changed. Stick to the types will ensure the further calculation.
  private val recovery: PartialFunction[Throwable, Graph[SourceShape[ResourceResponse], NotUsed]] = {
    case e: Throwable => {
      log.error("An error during the calculation.", e)
      Source.single(ResourceResponse(3, 10000, 10000))
    }
  }

  // An example how recovery can work in term of stream processing.
  // Here, we inject a default response which could be an average resource response.

  private val calcFlow: Flow[ResourceCalculationProposalRequest,
    ResourceResponse, _] =
    Flow[ResourceCalculationProposalRequest].map { r =>
      ResourceResponse(1, 1, 1)
    }.recoverWithRetries(5, recovery)

  // The supervision strategy if the stream will fail.
  private val decider: Supervision.Decider = {
    case e: IllegalArgumentException =>
      log.error("An exception occured during the calculation. Supervision strategy for:", e)
      Supervision.Restart
    case _ =>
      log.error("Unexpected Error, Stream processing is stopped")
      Supervision.Stop
  }
}
