package ds.linkedList

class Stack(size : Int) {
  var top = -1
  var max = size-1
  var array = new Array[Int](size)

  def push(data : Int) = {
    if(top+1 > max) {
      throw new RuntimeException("Stack is full")
    } else {
      top = top+1
      array(top) = data
    }
  }

  def pop: Int = {
    if(top == -1) {
     throw new RuntimeException("Stack is empty")
    } else {
      val value = array(top)
      top = top-1
      value
    }
  }

  def printStackData = {
    while (top != -1) {
      println(pop)

    }
  }
}

object StackTest extends App {
  val stack = new Stack(7)

  stack.push(1)
  stack.push(2)
  stack.push(3)
  stack.push(4)
  stack.push(5)
  stack.push(6)
  stack.push(7)

//  println(stack.top)
  stack.printStackData

}

