package akka_stream

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, FlowShape}
import akka.stream.scaladsl.{Balance, Broadcast, Flow, GraphDSL, Merge, Sink, Source}
import scala.concurrent.duration._

import scala.concurrent.Future

object Demo extends App {

  implicit val system = ActorSystem("Demo")
  implicit val mat = ActorMaterializer()
  import system.dispatcher

  val graph = GraphDSL.create() { implicit b =>


    import GraphDSL.Implicits._

    val flow1 = Flow[Int].map {
      x =>
        println("Flow1 > ")
        x + 10
    }
        val flow2 = Flow[Int].map{
          x =>
            println("Flow2 > ")
            s"$x + 10"
        }

        val balance = b.add(Balance[Int](2))
        val merge = b.add(Merge[Any](2))

        balance.out(0) ~> flow1 ~> merge.in(0)
        balance.out(1) ~> flow2 ~> merge.in(1)

        FlowShape(balance.in , merge.out)
    }


  val flow: Flow[Int, Any, NotUsed] = Flow.fromGraph(graph)

  Source(List(1,2,3,4,5)).throttle(1 , 5.seconds ).via(flow).runWith(Sink.foreach(println))

  def convert(x : String) = 1

 val a: Flow[String, Int, NotUsed] =  Flow.fromFunction(convert _)

}
