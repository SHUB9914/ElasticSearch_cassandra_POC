package es

import java.net.InetAddress

import org.apache.http.HttpHost
import org.elasticsearch.client.{RestClient, RestHighLevelClient}
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress



trait ESManager {

  private val PORT = 9200
  private val HOST = "localhost"
//  private val address =  new TransportAddress(InetAddress.getByName(host) , port)

  lazy private val settings = Settings.builder()
    .put("cluster.name", "my-cluster")
    .build()
  val client: RestHighLevelClient = new RestHighLevelClient(

    RestClient.builder(new HttpHost(HOST, PORT, "http")))
}

object ESManager extends ESManager
