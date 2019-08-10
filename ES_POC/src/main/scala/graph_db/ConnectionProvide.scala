package graph_db

import com.datastax.driver.dse.graph.{GraphOptions, SimpleGraphStatement}
import com.datastax.driver.dse.{DseCluster, DseSession}

object ConnectionProvide {

  val keyspace = "customersapi"
  val timeout = 120000

  def getDseSession(dseCluster: DseCluster): DseSession = {

    dseCluster.getConfiguration().getSocketOptions().setReadTimeoutMillis(timeout)
    val dseConnSession: DseSession = dseCluster.connect()
    dseConnSession.executeGraph(new SimpleGraphStatement("system.graph('" + keyspace + "').ifNotExists().create()").setSystemQuery())
    dseConnSession.execute("USE " + keyspace)
    val dseConnection: DseSession = dseCluster.connect(keyspace)
    dseConnection
  }

  val session: DseSession = {

    val hostName = "172.17.0.3"

    println("Connecting with DSE Cluster....")
    val dseCluster: DseCluster = DseCluster.builder()
      .addContactPoint(hostName)
      .withGraphOptions(new GraphOptions().setGraphName(keyspace))
      .build();

    getDseSession(dseCluster)
  }
}