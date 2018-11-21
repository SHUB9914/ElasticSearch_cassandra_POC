package cassandra_phantom.Model

import com.outworkers.phantom.{Row, Table}
import com.outworkers.phantom.keys.PartitionKey
import com.outworkers.phantom.dsl._


case class Employee(
                     id: String,
                     email: String,
                     name: String,
                     age: Int
                   )

abstract class Employees extends Table[Employees , Employee]{

  object id extends StringColumn with PartitionKey
  object email extends StringColumn with PartitionKey
  object name extends StringColumn with ClusteringOrder with Ascending
  object age extends IntColumn with ClusteringOrder with Ascending

  override def fromRow(r: Row): Employee = Employee(
    id(r),
    email(r),
    name(r),
    age(r)
  )

  def num(emp:Employee) = insert().value(_.id , emp.id)
    .future()


}