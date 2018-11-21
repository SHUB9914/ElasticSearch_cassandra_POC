name := "ES_POC"

version := "0.1"

scalaVersion := "2.11.0"

val es = "org.elasticsearch" % "elasticsearch" % "5.6.1"
val esClient = "org.elasticsearch.client" % "transport" % "5.6.1"
val log4j = "log4j" % "log4j" % "1.2.17"


libraryDependencies ++= {
  Seq(
    "com.outworkers" % "phantom-dsl_2.11" % "2.14.5",
    "org.scala-lang" % "scala-reflect" % "2.11.0",
    es,
    esClient,
    log4j
  )
}


