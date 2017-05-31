package io.allquantor.nstream.flows

import akka.NotUsed
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Flow
import io.allquantor.nstream.messaging.MessagingConfig
import org.apache.kafka.clients.producer.ProducerRecord

trait MessagingFlow[K, V] {

  self: MessagingConfig[K, V] =>

  lazy val makeKafkaRecord: Flow[V, ProducerRecord[K, V], NotUsed] = Flow[V].map(new ProducerRecord[K, V]("test", _))

  lazy val kafkaSink = Producer.plainSink(self.producerSettings)
}
