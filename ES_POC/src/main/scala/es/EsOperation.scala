package es

import javax.sql.rowset.spi.SyncResolver
import org.elasticsearch.action.admin.indices.mapping.put.{PutMappingRequest, PutMappingRequestBuilder}
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.action.search.{SearchRequest, SearchResponse}
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.{QueryBuilders, TermsQueryBuilder}
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.{FieldSortBuilder, SortOrder}


class EsOperation(client: RestHighLevelClient) {

  def createUser(id: String): IndexResponse = {
    val json =
      """{
        |"name":"shubham",
        |"age" : 23,
        |"address" : "sadabad"
        |}""".stripMargin

    val json2 =
      """{
        |"name":"aman",
        |"age" : 27,
        |"address" : "hapur"
        |}""".stripMargin

    val request = new IndexRequest("exzeo", "users", id)
      .source(json2, XContentType.JSON)
    client.index(request,RequestOptions.DEFAULT)
  }

  def update(id: String, indexName: String, type_name: String, doc: String) = {
    val req = new UpdateRequest(indexName, type_name, id).doc(doc, XContentType.JSON)
    client.update(req, RequestOptions.DEFAULT)
  }

  def deleteIndex(indexName: String) = {
    val req = new DeleteRequest(indexName)
    client.delete(req)
  }

  def get(indexName: String, type_name: String, id: String) = {
    val req = new GetRequest(indexName, type_name, id)
    client.get(req)
  }

  def searchAll(indexName: String) = {

    val searchSpurceBuilder = new SearchSourceBuilder().query(
      QueryBuilders.matchAllQuery()
    )

    val req = new SearchRequest(indexName).source(searchSpurceBuilder)
    client.search(req)
  }

  def searchByText(index: String, fieldName: String, value: Any) = {
    val sourceQueryBuilder = new SearchSourceBuilder().query(
      QueryBuilders.matchQuery(fieldName, value)
    )
    val req = new SearchRequest(index).source(sourceQueryBuilder)
    client.search(req)

  }

  def searchByTerm(indexName: String, filedName: String, value: String) = {
    val searchQueryBuilder = new SearchSourceBuilder().query(
      QueryBuilders.termQuery(filedName, value)
    )
    val searchRequest = new SearchRequest(indexName).source(searchQueryBuilder)
    client.search(searchRequest)
  }

  def booleanSearch(indexName: String) = {
    val searchSourceBuilder = new SearchSourceBuilder().query(
      QueryBuilders.boolQuery()
        .must(QueryBuilders.matchQuery("name", "shubham2"))
        .must(QueryBuilders.matchQuery("state", "U.P"))
    )
    val searchRequest = new SearchRequest(indexName).source(searchSourceBuilder)
    client.search(searchRequest)
  }

  def sort(indexName: String, sortBy: String) = {
    val searchSourceBuilder = new SearchSourceBuilder()
      .query(QueryBuilders.matchAllQuery())
//      .sort(new FieldSortBuilder(sortBy).order(SortOrder.ASC))
      .sort(sortBy,SortOrder.ASC)
    val searchRequest = new SearchRequest(indexName).source(searchSourceBuilder)
    client.search(searchRequest)
  }

  def makeTextSearchableUsingMapping(indexName : String , fieldName : String) = {
    val setting =
      """
        |{
        |  "properties": {
        |    "my_field": {
        |      "type":     "text",
        |      "fielddata": true
        |    }
        |  }
        |}
      """.stripMargin
    val mappingRequest = new PutMappingRequest(indexName)
    mappingRequest.source(setting)
//    client.indices().putMapping(setting,RequestOptions.DEFAULT)

  }

  def simpleSearchByValue(indexName: String, value: Any) = {

    val searchRequest = new SearchRequest(indexName)
  }


}

object Test extends App {
  val client = ESManager.client
  val obj = new EsOperation(client)

  val indexName = "exzeo"
  val type_name = "users"

  //   val res =  obj.createUser("2")
//    val res = obj.searchByText(indexName,"name" , "ShUbham2")
  //  val res = obj.searchByTerm(indexName,"name" , "shubham")
  //  val res = obj.booleanSearch(indexName)

  val update =
    """{
      |"name" : "shubham2 agrawal",
      |"state" : "U.P"
      |}""".stripMargin

  //  val res = obj.update("1",indexName, type_name,update)

  //  val res = obj.getData("exzeo","users","1")
  //  val res = obj.getDataByQuery("exzeo","users","1","name","shubham")
  val res = obj.sort("exzeo", "age")

  println("res>>>>>" + res.getHits.getHits.map(_.getSourceAsString).toList)
  println("res>>>>>" + res)
}
