package RMQ_POC

import com.rabbitmq.client._

class MessageConsumer(channel: Channel) {
  val exchangeName = "my_exchange"

  def build(key : String , queueName : String , consumer : Consumer) ={
    val queue = channel.queueDeclare(queueName, true, false, false, new java.util.HashMap()).getQueue
    channel.queueBind(queue, exchangeName, key)
    channel.basicConsume(queue, true, consumer)
  }
}

object MessageConsumerTest extends App {

  val channel = ConnectionProvider.channel
  channel.exchangeDeclare("my_exchange" , "topic" , false)

  val messageConsumer = new MessageConsumer(channel)

  val consumer = new DefaultConsumer(channel){
    override def handleDelivery(consumerTag: String, envelope: Envelope,
                                properties: AMQP.BasicProperties, body: Array[Byte]): Unit = {

      val message = new String(body, "UTF-8")
      println("Heyy>>>>>>>>messageReceived>>>>>"+message)
    }
  }

  messageConsumer.build("black" , "first_queue" , consumer)
}
