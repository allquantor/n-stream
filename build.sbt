name          := """prophet"""
organization  := "de.kreditech.oktopus"
version       := "0.0.1"
scalaVersion  := "2.11.8"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= {
  val scalazV          = "7.3.0-M2"
  val akkaV            = "2.4.9"
  val scalaTestV       = "3.0.0-M15"
  val scalaMockV       = "3.2.2"
  val scalazScalaTestV = "0.3.0"
  Seq(
    "org.scalaz"        %% "scalaz-core"                       % scalazV,
    "com.typesafe.akka" %% "akka-http-core"                    % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental"            % akkaV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV,
    "org.scalatest"     %% "scalatest"                         % scalaTestV       % "it,test",
    "org.scalamock"     %% "scalamock-scalatest-support"       % scalaMockV       % "it,test",
    "org.scalaz"        %% "scalaz-scalacheck-binding"         % scalazV          % "it,test",
    "org.typelevel"     %% "scalaz-scalatest"                  % scalazScalaTestV % "it,test",
    "com.typesafe.akka" %% "akka-http-testkit"                 % akkaV            % "it,test"
  )
}

lazy val root = project.in(file(".")).configs(IntegrationTest)
Defaults.itSettings
Revolver.settings
enablePlugins(JavaAppPackaging)

initialCommands := """|import scalaz._
                     |import Scalaz._
                     |import akka.actor._
                     |import akka.pattern._
                     |import akka.util._
                     |import scala.concurrent._
                     |import scala.concurrent.duration._""".stripMargin

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }
// Todo:
//publishTo := {
//}

