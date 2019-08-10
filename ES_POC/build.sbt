import sbt.Keys.version


lazy val commonSettings = Seq(
  name := "ES_POC",

  version := "0.1",

  scalaVersion := "2.11.0"
)

scalaVersion in ThisBuild := "2.12.5"


val es = "org.elasticsearch" % "elasticsearch" % "6.7.0"
val esClient = "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "6.7.0"
//val log4j = "log4j" % "log4j" % "1.2.17"
//val log4j2 = "org.apache.logging.log4j" % "log4j-core" % "2.11.1"
val mongoDb = "org.mongodb.scala" %% "mongo-scala-driver" % "2.2.1"
val RabbitMqClient = "com.rabbitmq" % "amqp-client" % "3.6.5"
val actor = "com.typesafe.akka" %% "akka-actor" % "2.5.18"
val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.18"
val amq = "org.apache.activemq" % "activemq-core" % "5.7.0"
val akkahttp = "com.typesafe.akka" %% "akka-http" % "10.1.5"
val contrib = "com.typesafe.akka" %% "akka-contrib" % "2.5.19"
val sprayJson       =     "io.spray"            %%   "spray-json"       % "1.3.5"
val csvReader = "com.github.tototoshi" %% "scala-csv" % "1.3.5"
//val slf4j           =     "org.slf4j"           %    "slf4j-api"        % "1.7.25"
val logback         =     "ch.qos.logback"      %    "logback-classic"  % "1.2.3"
val sparkCore = "org.apache.spark" %% "spark-core" % "2.4.0"
val sparkStreaming = "org.apache.spark" %% "spark-streaming" % "2.4.0" % "provided"
val spark_hive = "org.apache.spark" %% "spark-hive" % "2.4.1" % "provided"
val jwt = "com.pauldijou" %% "jwt-core" % "0.5.1"
val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % "2.5.23"
val akkaUtils = "se.scalablesolutions.akka" %% "akka-util" % "0.7.1"
val dseGraph         =  "com.datastax.cassandra"     % "dse-driver"                      % "1.0.0"




lazy val DeskService: Project = {
  Project("ES_POC", file("."))
    .settings(commonSettings: _*)
    .settings(mainClass in assembly := Some("simulator.SimulatorServer"))
    .settings(assemblyJarName in assembly := s"ES_POC-${version.value}.jar")
    .settings(libraryDependencies ++= Seq(
      "com.outworkers" % "phantom-dsl_2.11" % "2.14.5",
      "org.scala-lang" % "scala-reflect" % "2.11.0",
      es,
      jwt,
      esClient,
      mongoDb,
      RabbitMqClient,
      actor,
      akkaStream,
      amq,
      contrib,
      akkahttp,
      sprayJson,
      csvReader,
      logback,
      sparkCore,
      sparkStreaming,
      spark_hive,
      dseGraph,
        "com.typesafe.akka" %% "akka-cluster" % "2.5.18",
        "com.typesafe.akka" %% "akka-cluster-metrics" % "2.5.18",
        "com.typesafe.akka" %% "akka-cluster-tools" % "2.5.18",
        "com.typesafe.akka" %% "akka-multi-node-testkit" % "2.5.18",
      "au.com.bytecode" % "opencsv" % "2.4"
    ))
}



//
//enablePlugins(AkkaGrpcPlugin)
//enablePlugins(JavaAgent)




