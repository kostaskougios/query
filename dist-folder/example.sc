import $cp.lib.`out.jar`
import com.aktit.query.DI._
import consoleService._

// import this for avro support
import $ivy.`org.apache.spark::spark-avro:3.0.0`

val RootDir = "data"

val tables = Seq(
  mountTable("budget", s"$RootDir/budget.csv", format = "csv", csvHeaders = true),
  mountTable("tweets", s"$RootDir/tweets"), // parquet by default
  mountTable("tweets_avro", s"$RootDir/tweets_avro", format = "avro"),
  mountTable("users", s"$RootDir/users"),
  mountTable("users_avro", s"$RootDir/users_avro", format = "avro")
)
terminal(tables)
