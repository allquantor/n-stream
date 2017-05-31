package io.allquantor.nstream.messaging

import java.util

import io.allquantor.nstream.protocol.ProtocolTypes.PureResponse
import io.circe.{Encoder, Json}
import org.apache.kafka.common.serialization.Serializer


class PureResponseSerializer extends Serializer[PureResponse] {


  implicit val encodeFoo: Encoder[PureResponse] = new Encoder[PureResponse] {
    final def apply(a: PureResponse): Json = Json.obj(
      ("xml", Json.fromString(a.xml.mkString))
    )
  }


  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = () // Do Nothing

  // TODO: Implement me!
  override def serialize(topic: String, data: PureResponse): Array[Byte] = {
    import io.circe.syntax._
    data.asJson.noSpaces.getBytes("UTF-8")
  }


  override def close(): Unit = () // Do nothing
}
