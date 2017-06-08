
import Dependencies._
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
  .aggregate(transport)
  .enablePlugins(sbtdocker.DockerPlugin, JavaServerAppPackaging)

lazy val data = project.in(file("data"))
  .settings(
    name := "n-stream-data"
  )

lazy val nlib = project.in(file("nlib"))
    .settings(
        name := "n-stream-nlib"
    )
    .settings(
        libraryDependencies ++= nlibDeps
    )

lazy val transport = project.in(file("transport"))
        .settings(
          name := "n-stream-transport")
        .settings(
          libraryDependencies ++= transportDeps
        )
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

