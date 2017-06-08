package nmap

import scala.xml.Node


package object protocol {

  sealed trait ScanRequest {
    val ip: String
  }

  case class BasicScanRequest(ip: String) extends ScanRequest

  sealed trait ScanResponse {
    def getStringResponse: String
  }

  case class XmlResponse(xml: Node) extends ScanResponse {
    override def getStringResponse: String = xml.mkString
  }

}
