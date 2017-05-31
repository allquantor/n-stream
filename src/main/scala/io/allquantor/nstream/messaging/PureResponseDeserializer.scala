package io.allquantor.nstream.messaging

import java.util

import io.allquantor.nstream.protocol.ProtocolTypes.PureResponse
import io.circe.Decoder.Result
import io.circe.{Decoder, HCursor}
import org.apache.kafka.common.serialization.Deserializer

import scala.xml.XML


class PureResponseDeserializer extends Deserializer[PureResponse] {


  implicit val decodeFoo: Decoder[PureResponse] = (c: HCursor) => {
    for {
      xml <- c.downField("xml").as[String]
    } yield {
      // Side effect
      PureResponse(XML.loadString(xml))
    }
  }

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = () // Do nothing

  override def close(): Unit = () // Do nothing

  override def deserialize(topic: String, data: Array[Byte]): PureResponse = {
    import io.circe.parser.decode
    // aHHH ONLY FOR TESTTT
    decode[PureResponse](new String(data,"UTF-8")).right.get
  }

}
