package ds.linkedList

class Tree(var left: Tree = null, var data: Int, var right: Tree = null) {

  def this(data: Int) = this(null, data, null)

  def addNode(data: Int) = {
    def addNodeInBTree(tree: Tree, newNode: Tree): Unit = {
      tree match {
        case node if node.data > newNode.data => {
          if (node.left == null) {
            node.left = newNode
          } else addNodeInBTree(tree.left, newNode)

        }
        case node if node.data < newNode.data => {
          if (node.right == null) {
            node.right = newNode
          } else addNodeInBTree(tree.right, newNode)
        }
      }
    }

    addNodeInBTree(this, new Tree(data))
    this
  }

  def printTreeData: Unit = {
    def traceTree(tree: Tree): Unit = {
      if (tree != null) {
        traceTree(tree.left)
        println(tree.data)
        traceTree(tree.right)
      }
    }

    traceTree(this)
  }

  def countNodes(tree: Tree): Int = {
    if (tree != null) {
      tree match {
        case t =>
          val leftCount = countNodes(t.left)
          val rightCount = countNodes(t.right)
          leftCount + rightCount + 1
      }
    } else 0


  }


  def findParentNodeOfTwoChildNodes(tree: Tree, node1: Int, node2: Int): Tree = {

    var parentNode: Tree = null;

    def find(tree: Tree): Option[Int] = {
      tree match {
        case null => None
        case t =>
          val left = find(t.left)
          val right = find(t.right)
          val res = List(left, right).flatten
          val doesBothNodesExist = res.filter(x => if (x == node1 || x == node2) true else false)

          if (doesBothNodesExist.size == 2) {
            //println("-------I fonded parentNode ---" + t.data)
            parentNode = tree
            None
          } else if (left.contains(node1) || left.contains(node2)) {
            if (t.data == node1 || t.data == node2) {
              //println(">>>>I founded>>>" + t.data)
              parentNode = tree
              None
            } else left
          }
          else if (right.contains(node1) || right.contains(node2)) {
            if (t.data == node1 || t.data == node2) {
              //println(">>>>I founded>>>" + t.data)
              parentNode = tree
              None
            } else right
          }
          else Some(t.data)

      }
    }

    find(tree)
    parentNode


  }

  def findParentToChildDistance(tree: Tree, n: Int): Option[Int] = {
    if (tree == null) None else {
      tree.data match {
        case t if t == n => Some(0)
        case t if t > n => {
          findParentToChildDistance(tree.left, n) match {
            case None => None
            case Some(v) => Some(v + 1)
          }
        }
        case t if t < n => {
          findParentToChildDistance(tree.right, n) match {
            case None => None
            case Some(v) => Some(v + 1)
          }
        }
      }
    }
  }

  def findTwoNodesDistance(tree: Tree, n1: Int, n2: Int) = {
    val parentNode = findParentNodeOfTwoChildNodes(tree, n1, n2)
    val firstNodeDistance = findParentToChildDistance(parentNode , n1)
    val secondNodeDistance = findParentToChildDistance(parentNode , n2)
    (firstNodeDistance , secondNodeDistance) match {
      case (Some(v1) , Some(v2)) => v1+v2
      case _ => 0
    }

  }

}

object TestTree extends App {
  val obj = new Tree(10)
  obj.addNode(7)
  obj.addNode(5)
  obj.addNode(4)
  obj.addNode(6)
  obj.addNode(8)
  obj.printTreeData

  val res = obj.findTwoNodesDistance(obj, 4 , 5)
  println(">>>>distance>>>>>", res)

}
