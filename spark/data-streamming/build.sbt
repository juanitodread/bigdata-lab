name := "Data Streamming"
version := "0.1.0"

lazy val root = (project in file("."))

scalaVersion := "2.12.8"

mainClass in Compile := Some("org.juanitodread.structuredstreaming.App")

val sparkVersion = "2.4.5"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.postgresql" % "postgresql" % "42.2.12",
)
