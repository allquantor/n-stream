name := """nstream"""
organization := "io.allquantor"
version := "0.0.1"
scalaVersion := "2.12.2"

crossScalaVersions := Seq("2.11.8", "2.12.2")

scalacOptions := Seq("-unchecked",
                     "-feature",
                     "-deprecation",
                     "-encoding",
                     "utf8",
                      "-Xfatal-warnings")


resolvers += Resolver.jcenterRepo




libraryDependencies ++= {


  val scalazV = "7.3.0-M2"
  val akkaV = "2.5.0"
  val scalaTestV = "3.0.0"
  val akkaHttpV = "10.0.6"
  val kafkaStreamV = "0.16"
  val msgPack4z = "0.3.7"
  val cirleVersion = "0.8.0"


  lazy val circleDep = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic"
  ).map(_ % cirleVersion)


  lazy val akkaDep = Seq(
    "com.typesafe.akka" %% "akka-stream" % akkaV,

    "com.typesafe.akka" %% "akka-testkit" % akkaV,

    // https://mvnrepository.com/artifact/com.typesafe.akka/akka-stream-testkit_2.11
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaV,

    // https://mvnrepository.com/artifact/com.typesafe.akka/akka-http_2.11
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,

    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,

    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV,

    // tauschen gegen hseebergers
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,

    "com.typesafe.akka" %% "akka-stream-kafka" % kafkaStreamV
  )

  lazy val testingDep = Seq (
    "org.scalatest" %% "scalatest" % "3.0.3" % "test"
  )

   circleDep ++ akkaDep ++ testingDep
}

lazy val root = project.in(file(".")).configs(IntegrationTest)

initialCommands :=
  """
    |import scala.concurrent._
    |import scala.concurrent.duration._""".stripMargin

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ =>
  false
}
// Todo:
//publishTo := {
//}
