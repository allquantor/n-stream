package flow

import akka.NotUsed
import akka.stream.scaladsl.{Flow, GraphDSL}
import akka.stream.{FlowShape, Graph}
import nmap.NmapHandler


trait ScannerStreamFlow {

  // TODO: FromConfig, should be alligned with the parallelism level in NmapHandler
  val ParLevel = 100

  import nmap.protocol.{ScanRequest, ScanResponse}

  lazy val webScannerStream: Graph[FlowShape[ScanRequest, ScanResponse], NotUsed] =
    GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._

      val source = Flow[ScanRequest].map(identity)
      val scan = Flow[ScanRequest].mapAsync(ParLevel) (NmapHandler.executeBaseSecurityScan)

      val input = b.add(source)
      val output = b.add(scan)

      input ~> output

      FlowShape(input.in, output.out)
    }

}
