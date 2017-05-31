package io.allquantor.nstream.flows

import java.nio.file.Paths

import akka.stream.scaladsl.FileIO
import akka.stream.testkit.scaladsl.TestSink
import io.allquantor.nstream.messaging.MessagingConfig
import io.allquantor.nstream.protocol.ProtocolTypes.PureResponse
import org.apache.kafka.clients.producer.ProducerRecord
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._


class MessagingFlowTest extends FlatSpec with Matchers with
  MessagingFlow[Array[Byte], PureResponse]
  with MessagingConfig[Array[Byte], PureResponse]
  with TestMessageConfig {

  // Path to Test Scan Result.
  private final val path = Paths.get(getClass.getResource("/test_nmap_response.xml").getPath)

  // Get response as stream for composability.
  private val nmapResponseMock = FileIO.fromPath(path)
    .map(_.utf8String)
    .map(scala.xml.XML.loadString _ andThen PureResponse.apply)
  // Get response as stream for composability.
  private val originalScanResult = PureResponse(
    scala.xml.XML.loadString(
      scala.io.Source.fromFile(path.toFile).mkString
    )
  )

  import io.allquantor.nstream.system.SystemConfig._


  // TODO: Negative tests; exceptions.
  "A Kafka Message Flow" should "lift a Nmap Response inside a response object " in {

    val result = new ProducerRecord[Array[Byte], PureResponse]("test", originalScanResult)

    nmapResponseMock.via(makeKafkaRecord)
      .runWith(TestSink.probe[ProducerRecord[Array[Byte], PureResponse]])
      .request(1)
      .expectNext(result)
      .expectComplete()

  }

  // TODO: After MSGPACK
  it should "Serialize it correctly to a Byte Msg " in {
    it should "be deserializable up to ismorphism" in {

    }
  }

  // TODO: Migrate to integration test.
  it should "Dump messages inside of Kafka" in {
    val res = nmapResponseMock
      .via(makeKafkaRecord)
      .runWith(kafkaSink)

    Await.result(res, 2.seconds)
  }
}
