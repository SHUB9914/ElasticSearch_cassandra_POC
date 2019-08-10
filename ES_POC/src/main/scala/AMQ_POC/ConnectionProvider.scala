package AMQ_POC

import org.apache.activemq.ActiveMQConnectionFactory

object ConnectionProvider {

  val default = "tcp://localhost:61616"
  val url = "failover://" + default
  val connectionFactory = new ActiveMQConnectionFactory()
  val conn = connectionFactory.createConnection()


}
