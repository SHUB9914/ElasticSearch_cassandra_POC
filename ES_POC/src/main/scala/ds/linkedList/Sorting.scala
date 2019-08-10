package ds.linkedList

class QuickSort {

  def getPartation(array : Array[Int] , low : Int , heighest : Int) = {

    val arr = array
    val pivot = arr(heighest)
    var i = low-1
    for(j <- low until heighest ){
      if(arr(j) <=  pivot) {
        i = i+1
        val temp = arr(j)
        arr(j) = arr(i)
        arr(i) = temp
      }
    }
    i = i+1
    arr(heighest) = arr(i)
    arr(i) = pivot
    i
  }

  def sortByQuick(arr : Array[Int]):Array[Int] = {

    def sort(l : Int , h : Int):Unit  = {
      if(h > l) {
        val partation = getPartation(arr , l ,h)
        sort(l , partation-1)
        sort(partation+1 , h)
      }
    }
    sort(0 , arr.length - 1)
    arr
  }
}

object Sorting extends App {

  val array: Array[Int] = Array(10,17,2,8,6,9,21,0,19,5,4,18)

  val obj = new QuickSort
  val res = obj.sortByQuick(array)
  res.foreach(println)

}
