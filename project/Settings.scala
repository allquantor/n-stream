import sbt.Keys._
import sbt._

object Settings {

  resolvers in Global ++= Seq(
    "My Nexus" at "http://ec2-52-28-13-64.eu-central-1.compute.amazonaws.com:8081/repository/io.perseus/"
  )

  lazy val testSettings = Seq(
    fork in Test := false,
    parallelExecution in Test := false
  )

  lazy val integrationTestSettings = Defaults.itSettings ++ Seq(
    fork in IntegrationTest := true,
    parallelExecution in IntegrationTest := true,
    javaOptions in IntegrationTest := Seq("-Xmx2G", "-Xss256k", "-XX:+UseCompressedOops")
  )

  lazy val commonSettingsPack = testSettings ++ integrationTestSettings


  object ArtifactorySettings {

    lazy val PerseusRepositoryName = """Perseus Nexus Repository"""
    lazy val PerseusRepositoryAddress = """ec2-52-28-13-64.eu-central-1.compute.amazonaws.com"""

  }
}