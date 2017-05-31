package io.allquantor.nstream.messaging

import akka.kafka.ProducerSettings
import org.apache.kafka.common.serialization.Serializer


trait MessagingConfig[K, V]{

  val kSerializer: Option[Serializer[K]]
  val vSerializer: Option[Serializer[V]]
  val servers : String


  import io.allquantor.nstream.system.SystemConfig._

  // TODO: Avoid type Any by writing your own serializers matched to MSGPack.
  lazy val producerSettings =
    ProducerSettings(system, kSerializer, vSerializer)
    .withBootstrapServers(servers)
}
