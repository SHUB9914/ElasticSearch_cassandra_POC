package AMQ_POC

import org.apache.activemq.ActiveMQConnectionFactory

object ConnectionProvider {

  val connectionFactory = new ActiveMQConnectionFactory()
  val conn = connectionFactory.createConnection()

}
