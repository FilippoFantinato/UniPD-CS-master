ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "ATM"
  )

libraryDependencies ++= Seq(
  "lchannels" % "lchannels_2.12" % "0.0.3",
  "org.slf4j" % "slf4j-simple" % "1.7.25"
)
