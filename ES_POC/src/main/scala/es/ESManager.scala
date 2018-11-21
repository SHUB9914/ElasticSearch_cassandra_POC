package es

import java.net.InetAddress

import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient


trait ESManager {

  private val port = 9300
  private val host = "localhost"
  private val address =  new InetSocketTransportAddress(InetAddress.getByName(host) , port)

  lazy private val settings = Settings.builder()
    .put("cluster.name", "my_cluster")
    .build()

  val client: TransportClient =  new PreBuiltTransportClient(settings).addTransportAddress(address)
}

object ESManager extends ESManager
