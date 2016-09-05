package de.kreditech.oktopus.prophet

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import de.kreditech.oktopus.prophet.calculation.CalculationService

object System {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  trait LoggerExecutor extends BaseComponent {
    protected implicit val executor = system.dispatcher
    protected implicit val log = Logging(system, "prophet-app")
  }

}

object Main extends App with System.LoggerExecutor with CalculationService {

  import System._

  val (host, port) = ("localhost", 9000)

  log.info("Starting prophet-app........")
  Http().bindAndHandle(standardRoute, host, port).recover {
    case e: Throwable =>
      log.error(
        "The HTTP servier occur an Error, initiating gracefull shutdown",
        e) // Here the gracefull shutdown(shutdown IO, supervise, ...).
  }

}
