import sbt._

object Dependencies {

  // Versions
  val akkaV = "2.5.0"
  val akkaHttpV = "10.0.6"
  val akkaKafkaStreamV = "0.16"
  val msgPack4zV = "0.3.7"
  val cirleV = "0.8.0"
  val scalaTestV = "3.0.3"
  val scalaXmlV = "1.0.6"

  // Libraries
  lazy val circle = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic"
  ).map(_ % cirleV)

  lazy val akkaStream = Seq(
    "com.typesafe.akka" %% "akka-stream",
    "com.typesafe.akka" %% "akka-testkit",
    "com.typesafe.akka" %% "akka-stream-testkit"
  ).map(_ % akkaV)

  lazy val akkaHttp = Seq(
    "com.typesafe.akka" %% "akka-http",
    "com.typesafe.akka" %% "akka-http-testkit",
    "com.typesafe.akka" %% "akka-http-xml",
    "com.typesafe.akka" %% "akka-http-spray-json"
  ).map(_ % akkaHttpV)

  lazy val kafkaStream = Seq(
    "com.typesafe.akka" %% "akka-stream-kafka"
  ).map(_ % akkaKafkaStreamV)

  lazy val testingDep = "org.scalatest" %% "scalatest" % scalaTestV % "test"

  lazy val scalaXml = "org.scala-lang.modules" %% "scala-xml" % scalaXmlV


  lazy val commonsDepsPack = Seq(
    testingDep,
    scalaXml
  )

  // Projects
  lazy val nlibDeps = commonsDepsPack

  lazy val dataDeps = commonsDepsPack

  lazy val restDeps = akkaHttp

  lazy val transportDeps = commonsDepsPack ++ akkaStream ++ kafkaStream ++ circle

}