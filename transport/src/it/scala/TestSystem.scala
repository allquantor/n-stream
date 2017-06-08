import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory


object TestSystem {
  private lazy val config = ConfigFactory.load()
  implicit val system = ActorSystem("Nstream-IT", config)
  implicit val materializer = ActorMaterializer()

  trait LoggerExecutor {
    implicit val executor = system.dispatcher
    implicit val log = Logging(system, "nstream-it")
  }

}
