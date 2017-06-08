package protocol

import java.util

import io.circe.{Decoder, HCursor}
import nmap.protocol.XmlResponse
import org.apache.kafka.common.serialization.Deserializer

import scala.xml.XML


class XmlResponseDeserializer extends Deserializer[XmlResponse] {

  implicit val decodeFoo: Decoder[XmlResponse] = (c: HCursor) => {
    for {
      xml <- c.downField("xml").as[String]
    } yield {
      // Side effect
      XmlResponse(XML.loadString(xml))
    }
  }

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = () // Do nothing

  override def close(): Unit = () // Do nothing

  override def deserialize(topic: String, data: Array[Byte]): XmlResponse = {
    import io.circe.parser.decode
    // aHHH ONLY FOR TESTTT
    decode[XmlResponse](new String(data,"UTF-8")).right.get
  }

}
