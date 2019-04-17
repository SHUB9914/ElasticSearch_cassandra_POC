package akka_stream

import java.io.{File, FileInputStream, InputStream}

import akka.{Done, NotUsed, stream}
import akka.actor.{ActorRef, ActorSystem}
import akka.stream._
import akka.stream.scaladsl.{Balance, Broadcast, Flow, GraphDSL, Keep, Merge, RunnableGraph, Sink, Source, StreamConverters, Zip}
import org.reactivestreams.Publisher
import GraphDSL.Implicits._
import akka.http.scaladsl.model.ws.TextMessage

import scala.concurrent.Future


object Test extends App {


  implicit val system = ActorSystem("Sample")

  import system.dispatcher

  implicit val meterializer: ActorMaterializer = ActorMaterializer()

  def save(line: String) = Future {
    println(s"saving line = $line")
    Thread.sleep(8000)

  }

  val saveToDb: Flow[String, Unit, NotUsed] = Flow[String].mapAsyncUnordered(3)(save)

  val parseFile: Flow[File, String, NotUsed] =
    Flow[File]
      .flatMapConcat { file =>
        val stream = new FileInputStream(file)
        StreamConverters.fromInputStream(() => stream)
          .map(_.utf8String)
          .filter(_.contains("file"))
      }

  val importSingleFile: Flow[File, Unit, NotUsed] =
    Flow[File]
      .via(parseFile)
      .via(saveToDb)

  val balancerGraph = GraphDSL.create() { implicit builder =>

    val balancer = builder.add(Balance[File](2))
    val merge = builder.add(Merge[Unit](2))


    (1 to 2).map(_ => balancer ~> importSingleFile ~> merge)

    FlowShape(balancer.in, merge.out)
  }

  val balancer: Flow[File, Unit, NotUsed] = {
    Flow.fromGraph(balancerGraph)
  }

  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      val list = d.listFiles.filter(_.isFile).toList
      list
    } else {
      println("Sorry not exist file")
      List[File]()
    }
  }

  Source(getListOfFiles("/home/shubhamagarwal/Music/Stream_files"))
    .via(balancer)
    .runWith(Sink.ignore)


  val (webSocket, publisher) = Source
    .actorRef[String](1000, OverflowStrategy.fail)
    .toMat(Sink.asPublisher(fanout = false))(Keep.both)
    .run()

  val flow = Flow.fromGraph(GraphDSL.create() { implicit b =>
    val textMsgFlow: FlowShape[String, String] = b.add(Flow[String]
      .mapAsync(1)(_ => Future("")))
    val sourcePublisher = b.add(Source.fromPublisher(publisher).map(TextMessage(_)))
    textMsgFlow ~> Sink.foreach[String](println)
    FlowShape(textMsgFlow.in, sourcePublisher.out)
  })

  //  println(">>>>>>>Starting>>>>>>")
  //
  //  val stream = new FileInputStream(new File("/home/shubhamagarwal/Music/Stream_files/first3"))
  //
  //  println("Stream = "+stream)
  //
  //  StreamConverters.fromInputStream(() => stream).map(_.utf8String).runWith(Sink.foreach(println))
  //


  val partialGraph = GraphDSL.create() { implicit builder =>

    val flow = builder.add(Broadcast[Int](2))

    stream.UniformFanOutShape(flow.in, flow.out(0), flow.out(1))

  }

  val sink1 = Sink.foreach[Int](x => println("Sink1 is printing " + x))
  val sink2 = Sink.foreach[Int](x => println("Sink2 is printing " + x))



  val runnableGraph = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>

    val b = builder.add(partialGraph)

    val source1 = Source(1 to 5)

    source1 ~> b.in
    b.out(0) ~> sink1
    b.out(1) ~> sink2

    ClosedShape

  })

  println("Heeee >>> "+runnableGraph.run())

}

