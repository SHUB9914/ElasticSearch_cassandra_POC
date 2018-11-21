package es

import org.elasticsearch.action.get.{GetRequestBuilder, GetResponse}
import org.elasticsearch.action.index.{IndexRequestBuilder, IndexResponse}
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query._


class EsOperation(client: TransportClient) {

  def createUser(id : String , name : String , age : Int , address : String): IndexResponse = {
    val json =
      """{
        |"name":"shubham",
        |"age" : 23,
        |"address" : "sadabad"
        |}""".stripMargin
    client.prepareIndex("exzeo","users", id).setSource(json , XContentType.JSON).get()
  }

  def getData(indexName : String , typeName :String , id : String): GetResponse ={
    client.prepareGet(indexName , typeName , id).get()
  }

    def getDataByQuery(indexName : String , typeName :String , id : String , query : String , value : String): SearchResponse ={
    client.prepareSearch(indexName).setTypes(typeName).setQuery(QueryBuilders.termQuery(s"$query", s"$value"))
        .get()
  }

}

object Test extends App {
  val client = ESManager.client
  val obj = new EsOperation(client)

// val res =  obj.createUser("1","shubham",25,"Noida")

//  val res = obj.getData("exzeo","users","1")
  val res = obj.getDataByQuery("exzeo","users","1","name","shubham")

  println("res>>>>>"+res)
}
