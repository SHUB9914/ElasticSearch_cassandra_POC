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