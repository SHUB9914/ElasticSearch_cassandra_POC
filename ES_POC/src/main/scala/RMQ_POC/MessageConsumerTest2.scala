package RMQ_POC

import akka.actor.Actor
import com.rabbitmq.client.{AMQP, DefaultConsumer, Envelope}

class MessageConsumerTest2 extends Actor {

  val channel = ConnectionProvider.channel
  channel.exchangeDeclare("my_exchange" , "topic" , false)

  override def receive: Receive = {
    case "Start" => println("white message start consuming....");startConsuming
  }

  def startConsuming = {


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


}

