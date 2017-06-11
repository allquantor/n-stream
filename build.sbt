
import Dependencies._
import Settings.ArtifactorySettings.{PerseusRepositoryAddress, PerseusRepositoryName}
import Settings.{ArtifactorySettings, commonSettingsPack}
import sbt.Keys.mainClass

//name := """n-stream"""
organization in Global := "io.perseus"

crossScalaVersions in Global := Seq("2.12.2", "2.11.8")

scalaVersion in Global := crossScalaVersions.value.head

lazy val nstream = project.in(file("."))
  .settings(
    name := "n-stream"
  )
  .settings(
    libraryDependencies ++= restDeps ++ nlibDeps,
    mainClass in Compile := Some("io.allquantor.nstream.Main")
  )
  .dependsOn(nlib,transport)
  .aggregate(transport,nlib)
  .enablePlugins(sbtdocker.DockerPlugin, JavaServerAppPackaging)

lazy val data = project.in(file("data"))
  .settings(
    name := "n-stream-daa"
  )

lazy val nlib = project.in(file("nlib"))
    .configs(IntegrationTest)
    .settings(commonSettingsPack: _*)
    .settings(
        name := "n-stream-nlib"
    )
    .settings(
        libraryDependencies ++= nlibDeps
    )

lazy val transport = project.in(file("transport"))
        .settings(
          name := "n-stream-transport")
        .configs(IntegrationTest)
        .settings(libraryDependencies ++= transportDeps)
        .settings(commonSettingsPack: _*)
        .dependsOn(nlib)

dockerfile in docker := {
  val appDir: File = stage.value
  val targetDir = "/app"

  new Dockerfile {
    from("java")
    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
    copy(appDir, targetDir)
  }
}


// Initialization for artifactory.

(sys.env.get("NE3_USERNAME"), sys.env.get("NE2_PASSWORD")) match {
  case (Some(username), Some(password)) =>
    println(s"Obtained credentials for ${username.drop(username.length/2)}****.")
    credentials += Credentials(PerseusRepositoryName, PerseusRepositoryAddress, username, password)
  case _ =>
    println("NE3_USERNAME or NE2_PASSWORD is missing.")
    credentials += Credentials(PerseusRepositoryName, PerseusRepositoryAddress, "", "")
}

publishTo <<= version { v: String =>
  val nexus = s"http://$PerseusRepositoryAddress:8081/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "repository/nstream-snapshots/")
  else
    Some("releases" at nexus + "repository/nstream-release/")
}




