package cassandra_phantom.Model

import com.outworkers.phantom.dsl._


object ConnectorProvider {

  val Connector = ContactPoint.local.keySpace(KeySpace("my_keyspace")
    .ifNotExists().`with`(replication eqs SimpleStrategy.replication_factor(1)))

}
