import $cp.lib.`out.jar`
import com.aktit.query.DI._
import consoleService._

val tables = Seq(
  mountTable("tweets", "data/tweets"),
  mountTable("tweets_avro", "data/tweets_avro", format = "avro"),
  mountTable("users", "data/users"),
  mountTable("users_avro", "data/users_avro", format = "avro")
)
terminal(tables)
