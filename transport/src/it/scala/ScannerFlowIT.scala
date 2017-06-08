import akka.stream.scaladsl.Source
import akka.stream.testkit.scaladsl.TestSink
import flow.ScannerStreamFlow
import nmap.protocol.{BasicScanRequest, ScanResponse}
import org.scalatest.{FlatSpec, Matchers}

class ScannerFlowIT extends FlatSpec with Matchers with ScannerStreamFlow {

  implicit val testSytem = TestSystem.system
  implicit val mat = TestSystem.materializer


  // TODO: have to be something more meaningful here?
  val scanIP = "localhost"

  val NmapXMLString = "<nmaprun xmloutputversion="

  "An nmap scanner request" should "result in a scanner response" in {
    val dummyRequest = BasicScanRequest(scanIP)

    val result = Source.single(dummyRequest)
      .via(webScannerStream)
      .runWith(TestSink.probe[ScanResponse])
      .request(1)
      .expectNext()

    result.getStringResponse should startWith (NmapXMLString)
  }





}
