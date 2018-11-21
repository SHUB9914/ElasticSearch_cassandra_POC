package cassandra_phantom.Model

import com.outworkers.phantom.dsl._
import ConnectorProvider.Connector


class AppDatabase(
  override val connector: CassandraConnection
) extends Database[AppDatabase](connector) {

  object employees extends Employees with Connector{
    override val tableName = "employees"
  }

}

object Db extends AppDatabase(Connector)

trait AppDatabaseProvider extends DatabaseProvider[AppDatabase] {
//  override val database: AppDatabase = AppDatabase
}
