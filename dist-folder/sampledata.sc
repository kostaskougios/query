import $cp.lib.`out.jar`
import com.aktit.query.DI._
import java.time.LocalDate
import com.aktit.query.model.Table
import $ivy.`org.apache.spark::spark-avro:3.0.0`

val HowMany = 10000L


case class Tweet(id: Long, by: String, text: String, dateTime: LocalDate)

case class User(id: String, name: String)

println(s"Creating $HowMany rows")
val tweets = for (i <- 1L to HowMany) yield Tweet(i, s"user${i % 1000}", s"a random tweet by user ${i % 1000}, id=$i", LocalDate.now)
val users = for (i <- 1L to HowMany) yield User(s"user${i % 1000}", s"Username for ${i % 1000}")
println("Storing")
tableService.create(Table("tweets", "data/tweets", "parquet"), tweets)
tableService.create(Table("tweets_avro", "data/tweets_avro", "avro"), tweets)
tableService.create(Table("users", "data/users", "parquet"), users.distinct)
tableService.create(Table("users_avro", "data/users_avro", "avro"), users.distinct)
