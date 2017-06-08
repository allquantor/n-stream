import java.nio.file.Paths

import akka.stream.scaladsl.{FileIO, Sink, Source}
import akka.stream.testkit.scaladsl.TestSink
import flow.MessagingFlow
import nmap.protocol.XmlResponse
import org.apache.kafka.clients.producer.ProducerRecord
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

class KafkaFlowIT extends FlatSpec with Matchers with MessagingFlow {

  private implicit val testSytem = TestSystem.system
  private implicit val mat = TestSystem.materializer

  // Path to Test Scan Result.
  private final val path = Paths.get(getClass.getResource("/test_nmap_response.xml").getPath)

  // Get response as stream for composability.
  private val nmapResponseMock = FileIO.fromPath(path)
    .map(_.utf8String)
    .map(scala.xml.XML.loadString _ andThen XmlResponse.apply)
  // Get response as stream for composability.
  private val originalScanResult = XmlResponse(
    scala.xml.XML.loadString(
      scala.io.Source.fromFile(path.toFile).mkString
    )
  )

  // TODO: Negative tests; exceptions.
  "A Kafka Message Flow" should "lift a Nmap Response inside a response object " in {

    val resultProducerRecord = new ProducerRecord[String,XmlResponse]("test", originalScanResult)

    nmapResponseMock
      .via(kafkaXmlResponseFlow)
      .runWith(TestSink.probe[ProducerRecord[String, XmlResponse]])
      .request(1)
      .expectNext(resultProducerRecord)

  it should "Dump messages inside of Kafka" in {

      val futureResult = nmapResponseMock
        .via(kafkaXmlResponseFlow)
        .runWith(kafkaSink)

    Await.result(futureResult,2.second)
  }
}
