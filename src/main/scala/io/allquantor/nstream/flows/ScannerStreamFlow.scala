package io.allquantor.nstream.flows

import akka.NotUsed
import akka.stream.scaladsl.{Flow, GraphDSL}
import akka.stream.{FlowShape, Graph}
import io.allquantor.nstream.nmap.NmapHandle
import io.allquantor.nstream.protocol.ProtocolTypes.{PureResponse, ScanRequest, ScanResponse}


trait ScannerStreamFlow {
  // TODO: Abstract Scanner Type
  self: NmapHandle =>

  lazy val webScannerStream: Graph[FlowShape[ScanRequest, PureResponse], NotUsed] = GraphDSL.create() { implicit b =>
    import GraphDSL.Implicits._

    val source = Flow[ScanRequest].map(identity)

    val scan = Flow[ScanRequest].map {
      self.executeBaseSecurityScan _ andThen
        scala.xml.XML.loadString andThen
        PureResponse.apply
    }

    val input = b.add(source)
    val output = b.add(scan)

    input ~> scan

    FlowShape(input.in, output.out)
  }

}
