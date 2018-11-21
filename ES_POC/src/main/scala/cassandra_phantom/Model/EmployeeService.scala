package cassandra_phantom.Model

import com.outworkers.phantom.ResultSet
import com.outworkers.phantom.dsl._

import scala.concurrent.Future


trait EmployeeDao extends AppDatabaseProvider {

  def storeEmployee(emp: Employee): Future[ResultSet]

  def getEmployeeByEmail(id: String , email : String): Future[Option[Employee]]

}

class EmployeeService(val database: AppDatabase) extends EmployeeDao {

  def createTable = db.employees.create.ifNotExists().future

  def storeEmployee(emp: Employee): Future[ResultSet] = db.employees.store(emp).future()

  def getEmployeeByEmail(id: String , email : String): Future[Option[Employee]] = db.employees.select
    .where(_.id eqs id).and(_.email eqs email).one()


  def getEmployee(id: String , email : String , name : String): Future[Option[Employee]] = db.employees.select
    .where(_.id eqs id).and(_.email eqs email).and(_.name eqs name ).one()

}

object Test extends App {

  val obj = new EmployeeService(Db)
  val emp = Employee("1","shubham@gmail.com","shubham",26)

  val res = obj.getEmployee("1" , "shubham@gmail.com" , "shubham")

//  val res = for{
//    _ <- obj.createTable
//    result <- obj.storeEmployee(emp)
//  } yield result

  import scala.util._
  Thread.sleep(2000)
  res onComplete {
    case Success(e) => println("success>>>>>>"+e)
    case Failure(ex) => println("oopsssssssss>>>>>>>>>>>>"+ex.printStackTrace())
  }
}
