package io.allquantor.nstream.flows

import akka.kafka.ConsumerSettings
import io.allquantor.nstream.messaging.PureResponseSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, ByteArraySerializer, StringDeserializer}

trait TestMessageConfig {

  // Message Configs
   val kSerializer = Some(new ByteArraySerializer)
  // TODO: Should come from an Environment V
   val vSerializer = Some(new PureResponseSerializer)
   val servers: String = "127.0.0.1:9092"


  val consumerSettings = ConsumerSettings(
    io.allquantor.nstream.system.SystemConfig.system, new ByteArrayDeserializer, new StringDeserializer)
    .withBootstrapServers(servers)
    .withGroupId("group1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

}
