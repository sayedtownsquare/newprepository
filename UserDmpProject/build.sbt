name := "bigdata-users"

organization := "tsq"

version := "0.1.0"

scalaVersion := "2.10.5"


libraryDependencies ++= Seq(
  "org.scalanlp" %% "breeze" % "0.11.2",
  "org.scalanlp" %% "breeze-viz" % "0.11.2",
  "org.scalanlp" %% "breeze-natives" % "0.11.2",
  "org.slf4j" % "slf4j-simple" % "1.7.5",
  "org.scalaj" %% "scalaj-http" % "1.1.6",
  "org.json4s" %% "json4s-native" % "3.2.11",
  "org.mongodb" %% "casbah" % "3.1.1",
  "org.apache.poi" % "poi" % "3.14",
  "org.apache.poi" % "poi-ooxml" % "3.14",
  "org.apache.poi" % "ooxml-schemas" % "1.1",
  //"org.specs2" %% "specs2" % "1.6.1",
  //"org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test",
  "org.apache.tika" % "tika-parsers" % "1.3"
)

scalacOptions ++= Seq("-unchecked", "-deprecation")