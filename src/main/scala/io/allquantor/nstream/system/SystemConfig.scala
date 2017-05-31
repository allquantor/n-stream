package io.allquantor.nstream.system

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import io.allquantor.nstream.BaseComponent

object SystemConfig {

  private lazy val config = ConfigFactory.load()
  implicit val system = ActorSystem("Nstream", config)
  implicit val materializer = ActorMaterializer()

  trait LoggerExecutor extends BaseComponent {
    implicit val executor = system.dispatcher
    implicit val log = Logging(system, "nstream-app")
  }


}
