package io.allquantor.nstream.protocol


import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.unmarshalling.Unmarshaller
import akka.stream.Materializer
import io.allquantor.nstream.protocol.ProtocolTypes.{NmapResponse, PortReport, ServiceReport}
import spray.json.DefaultJsonProtocol

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try
import scala.xml.Elem

trait Protocol extends DefaultJsonProtocol {

  import ProtocolTypes.{ScanRequest, ScanResponse}

  // Implicit format to ensure code generation for json-class and vice-versa transformation of the protocol types.
  implicit val requestFormat = jsonFormat2(ScanRequest.apply)
  implicit val responseFormat = jsonFormat1(ScanResponse.apply)




  implicit def mjmapResponseUnmarshaller = Unmarshaller.withMaterializer {
    implicit ex: ExecutionContext ⇒
    implicit mat: Materializer ⇒
    value: Elem  ⇒

      val ip = Try(value \\ "address" \@ "addr").flatMap(x => Try(Uri(x))).getOrElse(Uri("localhost"))

      val scanResults = ((value \\ "ports") \ "port").map { portInfo =>
        val p = portInfo.\@("portid").toInt
        val protocol = portInfo.\@("protocol")
        val state = portInfo.\("state").\@("state")
        val deviceType = Try(portInfo.\("service").\@("devicetype")).toOption
        val extraInfo = Try(portInfo.\("service").\@("extrainfo")).toOption
        val version = Try(portInfo.\("service").\@("version")).toOption

        PortReport(protocol = protocol, port = p, state = state,
          report = ServiceReport(extraInfo = extraInfo, device = deviceType, version = version)

        )
      }
      NmapResponse(ip, scanResults)


      Future.unit

  }
}


/**
  * Protocol Type definition.
  * Each type describe an entity related to the communication protocol of prophet.
  */
object ProtocolTypes {

  final case class ScanRequest(ip: String, mail: String)

  final case class ScanResponse(results: String)

  final case class ServiceReport(extraInfo: Option[String], version: Option[String],device:Option[String])

  final case class PortReport(protocol: String, port: Int, state: String, report: ServiceReport)

  final case class NmapResponse(ip: Uri, portReport: Seq[PortReport])

  final case class PureResponse(xml: Elem)



}
