package AMQ_POC

import AMQ_POC.SessionProvider._
import javax.jms.{Message, MessageListener, TextMessage}

object ReceiveFromQeue extends App {

//  val DEFAULT_BROKER_BIND_URL = "tcp://localhost:61616"
//  val DEFAULT_BROKER_URL = "failover://" + DEFAULT_BROKER_BIND_URL

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
