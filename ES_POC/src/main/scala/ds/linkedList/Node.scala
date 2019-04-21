package ds.linkedList

class Node(var v : Any , var next : Node) {
  def this(value : Any) = this(value , null)
}

class DoublyNode(var prev : DoublyNode , var v : Any , var next : DoublyNode){
  def this(value : Any) = this(null , value , null)
}

class LinkedListOpt {

  def createNode(value : Any) =  new Node(value)

  def addNode(list : Node , value : Any) = {
    val newNode = new Node(value)
    list.next = newNode
    list
  }

  def addNodeInLast(previousNode : Node , value : Any) = {
    val newNode = new Node(value)
    def findLastNode(node : Node):Node = {
      node.next match {
        case null => node
        case _ => findLastNode(node.next)
      }
    }

    findLastNode(previousNode).next = newNode
    previousNode
  }
}

object Test extends App {
  val opt = new LinkedListOpt
  val node1 = opt.createNode(1)
  val node2 = opt.addNode(node1 , 2)
  var node3 = opt.addNodeInLast(node2 , 3)
//
//  println(">>>>>>node1>>>>"+node1.v)
//  println(">>>>>>node2>>>>"+node2.v)
//  println(">>>>>>node2>>>>"+node2.next.v)
//
  while(node3 != null) {
    println(node3.v)
    node3 = node3.next
  }
}
