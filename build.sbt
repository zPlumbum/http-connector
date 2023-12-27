ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.15"

lazy val root = (project in file("."))
  .settings(
    name := "http_client",
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-jackson" % "3.6.12"
    )
  )
