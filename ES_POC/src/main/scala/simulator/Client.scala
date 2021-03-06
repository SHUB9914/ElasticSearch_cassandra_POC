package simulator

import java.io.File

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, Materializer, ThrottleMode}
import com.github.tototoshi.csv.CSVReader
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import spray.json.DefaultJsonProtocol._
import spray.json._

import scala.concurrent.duration._
import scala.util.Random

case class Reports(Imei : String , Resource_path : String)

case class CallbackEvents(Reports : List[Reports])

object Client extends App {

  implicit val reports = jsonFormat2(Reports)
  implicit val callbackEvents = jsonFormat1(CallbackEvents)

  val log = LoggerFactory.getLogger(this.getClass)

  val conf = ConfigFactory.load()

  implicit val system = ActorSystem("simulator")
  implicit val ec = system.dispatcher
  implicit val mat: Materializer = ActorMaterializer()

  lazy val throttleLevel = conf.getInt("simulator.event.throttle")
  lazy val maxSimulatorCount = conf.getInt("simulator.event.maxlimit")
//  lazy val url = conf.getString("simulator.api.url")
  lazy val url = "http://172.16.2.206:8989/114/490/search"
  lazy val accessToken = conf.getString("simulator.api.access_token")

  val reader = CSVReader.open(new File("/home/shubhamagarwal/Downloads/Info.csv"))
  val elementList: List[List[String]] = reader.all()

  def random = Random.nextInt(4)

  def generateReports = {
    val randomSelectedList = elementList(random)
    Reports(randomSelectedList(0).split(":").last , randomSelectedList(1).toList.init.mkString)
  }

  def generateRandomPayload = {
    val reports = (1 to 4).toList.map(_ => generateReports)
    val event = CallbackEvents(reports)
    event.toJson
  }


  def post(data: String) =
      Http(system).singleRequest(
        HttpRequest(
          HttpMethods.POST,
          url,
          entity = HttpEntity(ContentTypes.`application/json`, data.getBytes())
        )
      )

//
//  Source.fromIterator(() => Iterator from 0)
//    .limit(maxSimulatorCount)
//    .map(_ => generateRandomPayload)
//    .throttle(throttleLevel , 1.second , throttleLevel , ThrottleMode.shaping)
//    .runForeach{
//      data =>
//        log.warn("Going to call server api with data = "+data)
//        post(data.prettyPrint)
//    }

  val a = """{
            |   "columns":[
            |      "w.id"
            |   ],
            |   "query":"w.id in (214878,214870,214890,214888,214871,214889,214880,214877,214882,214879,214892,214868,214875,214872,214896,214869,214873,214887,214884,214883,214897,214885,214874,214898) and w.status in (closed)"
            |}""".stripMargin

  val res = post(a)

  Thread.sleep(1000)

  println(">>>>>>Res>>>>>> " + res)

}
