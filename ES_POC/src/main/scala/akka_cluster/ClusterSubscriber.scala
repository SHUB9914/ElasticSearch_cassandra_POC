package akka_cluster

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import com.typesafe.config.ConfigFactory

class ClusterSubscriber extends Actor with ActorLogging{

  val cluster = Cluster(context.system)


  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[MemberEvent], classOf[UnreachableMember])
  }

  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case MemberUp(member) =>
      println(">>>>>>> Member is Up: {}", member.address)
    case UnreachableMember(member) =>
      println(">>>>>>> Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) =>
      println(">>>>>>> Member is Removed: {} after {}", member.address, previousStatus)
    case _: MemberEvent => // ignore
  }
}

object ClusterSubscriber extends App {

  val port = 8080

  val config = ConfigFactory.parseString(
    s"""
        akka.remote.netty.tcp.port=$port
        """).withFallback(ConfigFactory.load())

  val system = ActorSystem("ClusterSystem", config)
  system.actorOf(Props[ClusterSubscriber], name = "clusterSubscriber")


  Cluster(system) registerOnMemberUp {
    system.actorOf(Props(classOf[ClusterSubscriber], 200, true),
      name = "factorialFrontend")

  }
}

