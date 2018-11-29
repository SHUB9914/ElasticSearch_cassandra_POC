package Mongo_POC

import org.mongodb.scala._
import org.mongodb.scala.model.Filters._
import scala.concurrent.Future

class DbProvider(database: MongoDatabase) {

  def getCollection(collectionName: String) = database.getCollection(collectionName)

}

class UserDao(database: MongoDatabase) {
  val collection = database.getCollection("users")

  def getUser(id: String) = collection.find().toFuture()
}

object Test extends App {
  val mongoClient = MongoClient("mongodb://localhost:27018")
  val datbase = mongoClient.getDatabase("abcd")

  val obj = new UserDao(datbase)
  val res = obj.getUser("5bf77808fa54a8d12532f62e")
  Thread.sleep(5000)


  println("res>>>>>"+res)

  import scala.concurrent.ExecutionContext.Implicits.global

  import scala.util._

  res onComplete {
    case Success(res) => println("res>>>>>>>" + res)
    case Failure(exception) => println(">>>>>>>opsssssss>>>>" + exception.printStackTrace())
  }

  Thread.sleep(5000)

}
