import $cp.lib.`out.jar`
import com.aktit.query.DI._
import consoleService._

// import this for avro support
import $ivy.`org.apache.spark::spark-avro:3.0.0`

val tables = Seq(
  mountTable("budget", "data/budget.csv", format = "csv"),
  mountTable("tweets", "data/tweets"), // parquet by default
  mountTable("tweets_avro", "data/tweets_avro", format = "avro"),
  mountTable("users", "data/users"),
  mountTable("users_avro", "data/users_avro", format = "avro")
)
terminal(tables)
