package AMQ_POC

import AMQ_POC.SessionProvider._

object ProduceToQueue extends App {

  conn.start()

  val queue = session.createQueue("SampleQueue")
  val producer = session.createProducer(queue)
  val message = session.createTextMessage("This is last message")

  producer.send(message)
  conn.close()

}
