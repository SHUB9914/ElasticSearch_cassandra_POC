package RMQ_POC

object Test extends App {

//  implicit val system = ActorSystem("system")
//  implicit val meterialzer =  ActorMaterializer()
//  implicit val executionContext = system.dispatcher

  val consumnerTest1 = new MessageConsumerTest1
//  val consumnerTest2 = system.actorOf(Props(new MessageConsumerTest2))

  consumnerTest1.receive
//  consumnerTest2 ! "Start"
}
