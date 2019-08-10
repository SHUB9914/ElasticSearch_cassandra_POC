package graph_db

import com.datastax.driver.dse.DseSession
import com.datastax.driver.dse.graph.{SimpleGraphStatement, Vertex}

import collection.JavaConverters._


case class Address(state : String , city : String , zip : Int)
case class User(id : Long, firstName : String , middleName : String , lastName : String , address :Address)

class UsersOpt(session : DseSession) {

  def createUser(user : User): Vertex = {

    val query =
      s"""graph.addVertex(
         |label,"user","id" , "${user.id}",
         | "firstName" , "${user.firstName}",
         | "middleName" , "${user.middleName}",
         | "lastName" , "${user.lastName}",
         | "address" , "${user.address.city}",
         | "address" , "${user.address.state}"
         |)""".stripMargin

    session.executeGraph(query).one().asVertex()

  }

  def updateUser(id : Long , key : String , value : Any ) = {

  val query =   s""" g.V().hasLabel('user').has('id','$id').outE('follow').inV()
       |.property('$key','${value}')""".stripMargin

    session.executeGraph(query)

  }

  def createEdge(firstUser : User , secondUser : User)  = {

    val v1 = session.executeGraph(s"""g.V().hasLabel('user').has('id' , '${firstUser.id}')""").one().asVertex()
    val v2 = session.executeGraph(s"""g.V().hasLabel('user').has('id' , '${secondUser.id}')""").one().asVertex()

    val s = new SimpleGraphStatement(
      "def v1 = g.V(id1).next()\n" +
        "def v2 = g.V(id2).next()\n" +
        "v1.addEdge('follow', v2 , 'day' , 'monday')"
    )
      .set("id1", v1.getId())
      .set("id2", v2.getId())

    session.executeGraph(s)

  }

}



object TestUserOpt extends App {

  val user1 = User(101,"shubham","","agrawal",Address("U.P","Noida",3310))
  val user2 = User(102,"Aman","kumar","krishnatrey",Address("U.P","Noida",3310))
  val user3 = User(102,"Rahul","kumar","Agrawal",Address("U.P","Noida",3310))

  val session = ConnectionProvide.session
  val usersObj = new UsersOpt(session)

  val res = usersObj.createUser(user3)
//  val res = usersObj.createEdge(user1 , user2)

//  val res = usersObj.updateUser(101 , "firstName" , "Aman k..")
  println(">>>>success>>>>>>>>")
//  println(">>>>>>>res>>>>" + res.one().isVertex)
}
