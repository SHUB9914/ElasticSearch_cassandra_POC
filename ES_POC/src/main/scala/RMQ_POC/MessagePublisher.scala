package RMQ_POC

import com.rabbitmq.client.Channel


class MessagePublisher(channel: Channel, exchangeName: String) {

  def publishMessage(message: String, routingKey: String) = {
    channel.basicPublish(exchangeName, routingKey, null, message.getBytes("UTF-8"))
  }
}

object MessagePublisher extends App {
  val channel = ConnectionProvider.channel
  val exchangeName = "my_exchange"

  channel.exchangeDeclare(exchangeName, "topic", false)

  val obj = new MessagePublisher(channel, exchangeName)

  obj.publishMessage("This is black message", "black")
}
