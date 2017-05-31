package io.allquantor.nstream

import akka.http.scaladsl.server.Directives.{as, complete, entity, onSuccess, path, post}
import akka.stream.scaladsl.Source
import akka.stream.{ActorAttributes, Supervision}
import io.allquantor.nstream.flows.{MessagingFlow, ScannerStreamFlow}
import io.allquantor.nstream.messaging.{MessagingConfig, PureResponseSerializer}
import io.allquantor.nstream.nmap.NmapHandle
import io.allquantor.nstream.protocol.ProtocolTypes.{PureResponse, ScanRequest}
import org.apache.kafka.common.serialization.ByteArraySerializer

// TODO: Rethink this evil shit with BaseService.
package object routes extends BaseService with system.SystemConfig.LoggerExecutor
  with ScannerStreamFlow with NmapHandle
  with MessagingFlow[Array[Byte], PureResponse] with MessagingConfig[Array[Byte], PureResponse] {

  // NmapHandle
  override val nmapPath = "/usr/local/bin/nmap"
  // Message Configs
  override val kSerializer = Some(new ByteArraySerializer)
  override val vSerializer =  Some(new PureResponseSerializer)
  // TODO: Should come from an Environment Variable
  override val servers =  "localhost:9092"

  private val decider: Supervision.Decider = {
    case e: IllegalArgumentException =>
      log.error("An exception occured during the calculation. Supervision strategy for:", e)
      Supervision.Restart
    case _ =>
      log.error("Unexpected Error, Stream processing is stopped")
      Supervision.Stop
  }

  private val supervision = ActorAttributes.supervisionStrategy(decider)

  private final val ScanRequestPath = "scan"

  import io.allquantor.nstream.system.SystemConfig._

  val scanRequestRoute = path(ScanRequestPath) {
    post {
      entity(as[ScanRequest]) { req =>
        // TODO: Homogen Structure for Logs.
        log.info(s"Incoming request to process security scan ${req}")
        val scanStream = Source.single(req)
          .via(webScannerStream)
          .via(makeKafkaRecord)
          .runWith(kafkaSink)
        onSuccess(scanStream) { resourceResponse =>
          complete(resourceResponse)
        }
      }
    }
  }


}
