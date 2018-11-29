package RMQ_POC

import com.rabbitmq.client.{AMQP, DefaultConsumer, Envelope}

object MessageConsumer2 extends App {

  val channel = ConnectionProvider.channel
  channel.exchangeDeclare("my_exchange" , "topic" , false)

  val messageConsumer = new MessageConsumer(channel)

  val consumer = new DefaultConsumer(channel){
    override def handleDelivery(consumerTag: String, envelope: Envelope,
                                properties: AMQP.BasicProperties, body: Array[Byte]): Unit = {

      val message = new String(body, "UTF-8")
      println("consumer2>>>>>>>>messageReceived>>>>>"+message)
    }
  }

  messageConsumer.build("white" , "second_queue" , consumer)
}

