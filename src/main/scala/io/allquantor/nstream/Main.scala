package io.allquantor.nstream

import akka.http.scaladsl.Http
import io.allquantor.nstream.routes.Routes
import io.allquantor.nstream.system.SystemConfig


object Main extends App with SystemConfig.LoggerExecutor  {

  import io.allquantor.nstream.system.SystemConfig._

  val (host, port) = ("localhost", 9000)

  log.info("Starting nstream-app........")

  Http().bindAndHandle(Routes.scanRequestRoute, host, port).recover {
    case e: Throwable =>
      log.error(
        "The HTTP servier occur an Error, initiating gracefull shutdown", e)
      // Here the gracefull shutdown(shutdown IO, supervise, ...).
  }
}
