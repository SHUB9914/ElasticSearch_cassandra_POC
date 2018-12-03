package AMQ_POC

import AMQ_POC.SessionProvider._
import javax.jms.{Message, MessageListener, TextMessage}

object ReceiveFromQeue extends App {

  conn.start()


  val queue = session.createQueue("SampleQueue")
  val consumer = session.createConsumer(queue)
//  val message = consumer.receive(1000)

  consumer.setMessageListener(new MessageListener {
    override def onMessage(message: Message): Unit = println("Hey i got message : - "+message.asInstanceOf[TextMessage].getText)
  })



//  println("Message received : "+message)
//  conn.close()

}
