package RMQ_POC

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer

object Test extends App {

  implicit val system = ActorSystem("system")
  implicit val meterialzer =  ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val consumnerTest1 = system.actorOf(Props(new MessageConsumerTest1))
  val consumnerTest2 = system.actorOf(Props(new MessageConsumerTest2))

  consumnerTest1 ! "Start"
  consumnerTest2 ! "Start"
}
