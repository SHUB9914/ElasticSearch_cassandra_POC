package AMQ_POC

import AMQ_POC.SessionProvider.conn
import RMQ_POC.{MessageConsumerTest1, MessageConsumerTest2}
import akka.actor.{Actor, ActorSystem, Props}
import akka.stream.ActorMaterializer
import javax.jms._

class AmqConsumer(queueName : String) extends Actor{

  conn.start()

  val session = conn.createSession(false , Session.AUTO_ACKNOWLEDGE)
  val queue = session.createQueue(queueName)
  val consumer = session.createConsumer(queue)
//  val json = conn.acknowledge(textMessage, x)




  override def receive:Receive = {
    case "Start" => startConsuming
  }

  def startConsuming = {
    consumer.setMessageListener(new MessageListener {
      override def onMessage(message: Message): Unit = println("Hey i got message : - "+message.asInstanceOf[TextMessage].getText)
    })
  }

}

object Test extends App {
  implicit val system = ActorSystem("system")
  implicit val meterialzer =  ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val consumnerTest1 = system.actorOf(Props(new AmqConsumer("SampleQueue")))

  consumnerTest1 ! "Start"
}
