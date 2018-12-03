package AMQ_POC

import javax.jms.Session
import org.apache.activemq.ActiveMQConnectionFactory

object SessionProvider {

  val connectionFactory = new ActiveMQConnectionFactory()
  val conn = connectionFactory.createConnection()
  conn.start()
  val session = conn.createSession(false , Session.AUTO_ACKNOWLEDGE)

}
