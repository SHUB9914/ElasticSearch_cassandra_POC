//package graph_db
//
//import com.datastax.driver.dse.graph.{GraphOptions, SimpleGraphStatement}
//import com.datastax.driver.dse.{DseCluster, DseSession}
//import collection.JavaConverters._
//
//
//object GraphDbTest extends App {
//
//  val keyspace = "customersapi"
//  val timeout = 120000
//
//  def insertSpok : String =
//    s"""graph.addVertex(label,"spok2","id","111111")""".stripMargin
//
//
//   def getDseSession(dseCluster: DseCluster): DseSession = {
//
//    dseCluster.getConfiguration().getSocketOptions().setReadTimeoutMillis(timeout)
//    val dseConnSession: DseSession = dseCluster.connect()
//    dseConnSession.executeGraph(new SimpleGraphStatement("system.graph('" + keyspace + "').ifNotExists().create()").setSystemQuery())
//    dseConnSession.execute("USE " + keyspace)
//    val dseConnection: DseSession = dseCluster.connect(keyspace)
//    dseConnection
//  }
//
//  val session: DseSession = {
//
//    val hostName = "172.17.0.3"
//
//    println("Connecting with DSE Cluster....")
//    val dseCluster: DseCluster = DseCluster.builder()
//      .addContactPoint(hostName)
//      .withGraphOptions(new GraphOptions().setGraphName(keyspace))
//      .build();
//
//    getDseSession(dseCluster)
//  }
//
////val pop = """schema.propertyKey("contentType").Text().create()"""
//////  val a = dseConn.executeGraph(pop)
////
//////  println(vartax.getProperty("contentType"))
////  println(">>>>.everything Went correct")
//
////  session.executeGraph(new SimpleGraphStatement("schema.config().option('graph.schema_mode').set('development')")
////    .setGraphName(keyspace))
////
////  val vartax = session.executeGraph(insertSpok).one().asVertex()
//
////  println(">>>>>id>>>>>"+vartax.getProperties("id"))
////  println(">>>>>id>>>>>"+vartax.getProperty("id"))
//
//  println(">>>>>Success>>>>>>>")
//
//  val getAllVertaxes = session.executeGraph("g.V().valueMap()").asScala
//
//  getAllVertaxes.foreach(x => println(">>>>>x>>>>" + x))
//
//
//}
