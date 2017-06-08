package io.allquantor.nstream.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives.{as, complete, entity, onSuccess, path, post}
import akka.stream.{ActorAttributes, Supervision}
import io.allquantor.nstream.system
import nmap.protocol.BasicScanRequest


object Routes extends system.SystemConfig.LoggerExecutor {

  private val supervision = ActorAttributes.supervisionStrategy(decider)

  implicit private val materializer = system.SystemConfig.materializer

  import io.allquantor.nstream.marshallers.JsonMarshallers._

  val scanRequestRoute = path("scan") {
    post {
      entity(as[BasicScanRequest]) { req =>
        // TODO: Homogen Structure for Logs.
        log.info(s"Incoming request to process security scan ${req}")
        onSuccess( composedflow.ScanFlow(req)(materializer) ) { resourceResponse =>
          complete(resourceResponse)
        }
      }
    }
  }

  private val decider: Supervision.Decider = {
    case e: IllegalArgumentException =>
      log.error("An exception occured during the calculation. Supervision strategy for:", e)
      Supervision.Restart
    case _ =>
      log.error("Unexpected Error, Stream processing is stopped")
      Supervision.Stop
  }

}
