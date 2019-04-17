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

object FlowGraph extends App {

  implicit val system = ActorSystem("Sample")

  import system.dispatcher

  implicit val meterializer: ActorMaterializer = ActorMaterializer()

  val pairUpWithToString =
    Flow.fromGraph(GraphDSL.create() { implicit b ⇒
      import GraphDSL.Implicits._

      // prepare graph elements
      val broadcast = b.add(Broadcast[Int](2))
      val zip = b.add(Zip[Int, String]())

      // connect the graph
      broadcast.out(0).map(x => if (x == 2) throw new Exception("opss") else x) ~> zip.in0
      broadcast.out(1).map(_.toString) ~> zip.in1

      // expose ports
      FlowShape(broadcast.in, zip.out)
    })

  val res: Future[Done] =
    Source(List(1, 2)).via(pairUpWithToString).runWith(Sink.foreach(x => println(x)))

  Thread.sleep(1000)

  res.map(println)

  // >>>>>>>>Sink Combine Example >>>>>>>>>>

  val actorRef: ActorRef = ???

  val sinkActor = Sink.actorRef(actorRef, "Done")
  val sinkLocally = Sink.foreach[Int](println)

  val combine = Sink.combine(sinkActor , sinkLocally)(Broadcast[Int](_))

  Source(List(0, 1, 2)).runWith(combine)

}
