name := "Message Simulator"
version := "0.0.1"

lazy val root = (project in file("."))

mainClass in assembly := Some("org.juanitodread.bigdata.messagesimulator.App")

scalaVersion := "2.12.8"

val kafkaClientVersion = "2.5.0"
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % kafkaClientVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
)
