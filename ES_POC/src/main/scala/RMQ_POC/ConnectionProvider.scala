package RMQ_POC

import com.rabbitmq.client
import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}


object ConnectionProvider {

  val factory: ConnectionFactory = new ConnectionFactory
//  factory.setAutomaticRecoveryEnabled(true)


  factory.setHost("localhost")
  factory.setPort(5672)
  factory.setUsername("admin")
  factory.setPassword("admin")


  val connection: Connection = factory.newConnection()
  val channel: client.Channel = connection.createChannel()


//  channel.exchangeDeclare(exchangeName , "topic" , true)
//
//  val obj = new MessagePublisher(channel , exchangeName)
//
//  obj.publishMessage("This is first message" , "black")


}
