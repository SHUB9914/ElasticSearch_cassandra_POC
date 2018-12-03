
name := "ES_POC"

version := "0.1"

scalaVersion := "2.11.0"

val es = "org.elasticsearch" % "elasticsearch" % "5.6.1"
val esClient = "org.elasticsearch.client" % "transport" % "5.6.1"
val log4j = "log4j" % "log4j" % "1.2.17"
val mongoDb = "org.mongodb.scala" %% "mongo-scala-driver" % "2.2.1"
val RabbitMqClient = "com.rabbitmq"  %  "amqp-client"% "3.6.5"
val actor = "com.typesafe.akka" %% "akka-actor" % "2.5.18"
val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.18"
val amq = "org.apache.activemq" % "activemq-core" % "5.7.0"







libraryDependencies ++= {
  Seq(
    "com.outworkers" % "phantom-dsl_2.11" % "2.14.5",
    "org.scala-lang" % "scala-reflect" % "2.11.0",
    es,
    esClient,
    log4j,
    mongoDb,
    RabbitMqClient,
    actor,
    akkaStream,
    amq
  )
}


