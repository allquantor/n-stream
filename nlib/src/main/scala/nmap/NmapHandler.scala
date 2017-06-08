package nmap

import nmap.protocol.{ScanRequest, ScanResponse, XmlResponse}
import nmap.util.IOThreadPool

import scala.concurrent.Future
import scala.sys.process._


object NmapHandler extends IOThreadPool{

  // todo read from config
  lazy private val nmapPath = "nmap"

  type Command = String

  // TODO : Better error Handling concept.
  def executeBaseSecurityScan[R <: ScanRequest](request: R): Future[ScanResponse] =
    executeCommand(Commands.BaseScan, request.ip).map(scala.xml.XML.loadString _ andThen XmlResponse.apply)

  // TODO....
  def executeCommand(command: Command, hosts: String): Future[String] =
    Future(s"$nmapPath $command $hosts".!!)

}
