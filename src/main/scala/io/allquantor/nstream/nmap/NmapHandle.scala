package io.allquantor.nstream.nmap

import io.allquantor.nstream.protocol.ProtocolTypes.ScanRequest

import scala.util.Try


trait NmapHandle{
  type Command = String
  type Result = String

  val nmapPath: String

  def executeBaseSecurityScan(request:ScanRequest): Result = {
    // TODO : Better error Handling concept.
    executeCommand(Commands.BaseScan,request.ip).getOrElse(s"Error for ${request}")
  }

  def executeCommand(command:Command, hosts:String): Try[Result] = {
    import sys.process._
    Try(s"$nmapPath $command $hosts".!!)
  }
}
