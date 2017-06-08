package flow

import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Flow
import nmap.protocol.XmlResponse
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{Serializer, StringSerializer}
import protocol.XmlResponseSerializer

trait MessagingFlow {


  val kSerializer: Option[Serializer[String]] = Some(new StringSerializer)
  val vSerializer: Option[Serializer[XmlResponse]] = Some(new XmlResponseSerializer)


  // todo config here
  val servers: String = "127.0.0.1:9092"
  // todo REuse actor system
  implicit val system: ActorSystem = ActorSystem()

  private lazy val producerSettings =
    ProducerSettings(system, kSerializer, vSerializer)
      .withBootstrapServers(servers)

  // TODO: Key Should Be Uniqe.
  lazy val kafkaXmlResponseFlow: Flow[XmlResponse, ProducerRecord[String, XmlResponse], _] =
    Flow[XmlResponse].map(new ProducerRecord[String, XmlResponse]("test", _))

  lazy val kafkaSink = Producer.plainSink(producerSettings)
}
