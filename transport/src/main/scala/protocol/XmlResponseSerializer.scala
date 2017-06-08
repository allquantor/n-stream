package protocol

import java.util

import io.circe.{Encoder, Json}
import nmap.protocol.XmlResponse
import org.apache.kafka.common.serialization.Serializer


class XmlResponseSerializer extends Serializer[XmlResponse] {
  
  implicit val encodeFoo: Encoder[XmlResponse] = (a: XmlResponse) =>
    Json.obj(("xml", Json.fromString(a.xml.mkString)))


  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = () // Do Nothing

  override def serialize(topic: String, data: XmlResponse): Array[Byte] = {
    import io.circe.syntax._
    data.asJson.noSpaces.getBytes("UTF-8")
  }

  override def close(): Unit = () // Do nothing
}
