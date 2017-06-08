import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Source}
import flow.{ScannerStreamFlow, _}
import nmap.protocol.{ScanRequest, ScanResponse, XmlResponse}


package object composedflow extends ScannerStreamFlow with
  MessagingFlow {


  lazy final val composerFlow = Flow[ScanResponse].map(_.getStringResponse)
    .map(scala.xml.XML.loadString _ andThen XmlResponse.apply)


  lazy final val ScanFlow = (scanRequest: ScanRequest) => { implicit materializer: Materializer =>
    Source.single(scanRequest)
      .via(webScannerStream)
      .via(composerFlow)
      .via(kafkaXmlResponseFlow)
      .runWith(kafkaSink)
  }

}
