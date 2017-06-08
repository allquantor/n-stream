package io.allquantor.nstream.marshallers

import nmap.protocol.BasicScanRequest
import spray.json.DefaultJsonProtocol
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._




object JsonMarshallers extends DefaultJsonProtocol {
  implicit val requestUnmarshaller = jsonFormat1(BasicScanRequest)
}





