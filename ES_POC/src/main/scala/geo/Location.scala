package geo

import java.io.{BufferedReader, InputStreamReader}
import java.net.URL

class Location {

  def ipInfo(ip : String, key: String) = {
    val url = new java.net.URL("http://api.ipinfodb.com/v3/ip-city/?key=" +
      key + "&ip=" + ip + "&format=json")
    val connection = url.openConnection
    val inputStream = connection.getInputStream
    val location = new String(Stream.continually(inputStream.read)
      .takeWhile(-1 !=).map(_.toByte).toArray)
    inputStream.close()
    location
  }

  def ipAddress(): String = {
    val whatismyip = new URL("http://checkip.amazonaws.com")
    val in:BufferedReader = new BufferedReader(new InputStreamReader(
      whatismyip.openStream()))
     in.readLine()
  }

}

object LocationTest extends App {

  val key = "a0591e44bf2948f7899654bc36038d09452dc260016a6d2ada4599d3d86628c6"


  val location = new Location
  val res = location.ipInfo("47.31.82.174", key)

  println(">res>>>>>> "+res)

}
