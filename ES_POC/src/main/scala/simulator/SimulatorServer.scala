package simulator

import java.io.{BufferedWriter, FileWriter, _}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.{FileIO, Source}
import akka.stream.{ActorMaterializer, Materializer}
import akka.util.ByteString
import au.com.bytecode.opencsv.CSVWriter
import com.typesafe.config.ConfigFactory

import scala.util.Random



object SimulatorServer extends App {

  implicit val system = ActorSystem("simulator")
  implicit val ec = system.dispatcher
  implicit val mat: Materializer = ActorMaterializer()

  val file = new File("hello.txt")
  val bw = new BufferedWriter(new FileWriter(file))
  bw.write("Hello world!")
  bw.close()



  //  val log = LoggerFactory.getLogger(this.getClass)
  val conf = ConfigFactory.load()
  lazy val host = "localhost"
//  lazy val host = conf.getString("localhost")
  lazy val port = 8080
//  lazy val port = conf.getInt(8080)

  val route = path("random") {
    (post & entity(as[String])) { data =>
      println("Data received in call = " + data)
      complete("<<<<<<<Hey sucesss response>>>>>>")
    }
  } ~ path("third" / Segment) { id =>
    get {
      println("request come to first path ...")


      val file =  new File("/home/shubhamagarwal/Pictures/reatailar.csv")
      val bw = new BufferedWriter(new FileWriter(file))
      bw.write("This is shubham , agrawal")
      bw.close()

      val source = FileIO.fromPath(file.toPath)
        .watchTermination() { case (_, result) =>
          result.onComplete(_ => file.delete())
        }

      complete(HttpResponse(status = StatusCodes.OK, entity = HttpEntity(ContentTypes.`text/csv(UTF-8)`, source)))







//      val outputFile = new BufferedWriter(new FileWriter("example.csv"))
//      val csvWriter = new CSVWriter(outputFile)
//      val csvFields = Array("id", "name", "age", "city")
//      val nameList = List("Deepak", "Sangeeta", "Geetika", "Anubhav", "Sahil", "Akshay")
//      val ageList = (24 to 26).toList
//      val cityList = List("Delhi", "Kolkata", "Chennai", "Mumbai")
//      val random = new Random()
//      var listOfRecords =  List[Array[String]]()
//      listOfRecords = listOfRecords ::: List(csvFields)
//      for (i <- 0 until 10) {
//        listOfRecords = listOfRecords ::: List(Array(i.toString, nameList(random.nextInt(nameList.length))
//          , ageList(random.nextInt(ageList.length)).toString, cityList(random.nextInt(cityList.length))))
//      }
//      csvWriter.writeAll(listOfRecords)
//      outputFile.close()
//
//      val str2 = scala.io.Source.fromFile("example.csv", "UTF8").mkString
//      val str = Source.single(ByteString(str2))

//      complete(HttpResponse(entity = HttpEntity.Chunked.fromData(ContentTypes.`application/octet-stream`, str)))

    }

  } ~ path("second") {
    get {
      println("request come to second path ...")
      complete("This is second request....")
    }
  }

  val bindingFuture = Http().bindAndHandle(route, host, port)
  bindingFuture onComplete {
    case scala.util.Success(value) => println("Server started on 8080")
    case scala.util.Failure(exception) => println("Some thing went wrong " + exception.printStackTrace())
  }
}



