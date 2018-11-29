package RMQ_POC

object MessagePublisher2 extends App {
  val channel = ConnectionProvider.channel
  val exchangeName = "my_exchange"

  channel.exchangeDeclare(exchangeName, "topic", false)

  val obj = new MessagePublisher(channel, exchangeName)

  obj.publishMessage("This is white message", "white")
}
