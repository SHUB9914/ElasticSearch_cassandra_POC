package RMQ_POC

import com.rabbitmq.client.{AMQP, DefaultConsumer, Envelope}

class MessageConsumerTest1 {

  val channel = ConnectionProvider.channel
  channel.exchangeDeclare("my_exchange" , "topic" , false)
  val messageConsumer = new MessageConsumer(channel)


  def receive = println("Black message start consuming....");startConsuming

  def startConsuming= {

    val consumer = new DefaultConsumer(channel){
      override def handleDelivery(consumerTag: String, envelope: Envelope,
                                  properties: AMQP.BasicProperties, body: Array[Byte]): Unit = {

        val message = new String(body, "UTF-8")
        println("Heyy>>>>>>>>messageReceived>>>>>"+message)
      }
    }

    messageConsumer.build("black" , "first_queue" , consumer)

  }

}
