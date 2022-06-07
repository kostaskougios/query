/**
 * Ammonite scala script, install ammonite for scala 2.12 and
 * run with `amm sampledata.sc`
 */

import $ivy.`org.apache.spark::spark-avro:3.2.1`

import java.time.LocalDate

val HowMany = 10000L

// Tweet table via this case class
case class Tweet(id: Long, by: String, text: String, dateTime: LocalDate)

// User table via this case class
case class User(id: String, name: String)

println(s"Creating $HowMany rows")
val tweets = for (i <- 1L to HowMany) yield Tweet(i, s"user${i % 1000}", s"a random tweet by user ${i % 1000}, id=$i", LocalDate.now)
val users = for (i <- 1L to HowMany) yield User(s"user${i % 1000}", s"Username for ${i % 1000}")
println("Storing")
tableService.create(Table("tweets", "data/tweets", "parquet"), tweets)
tableService.create(Table("tweets_avro", "data/tweets_avro", "avro"), tweets)
tableService.create(Table("users", "data/users", "parquet"), users.distinct)
tableService.create(Table("users_avro", "data/users_avro", "avro"), users.distinct)

tableService.create(Table("tweets", "data-to-scan/tweets", "parquet"), tweets)
tableService.create(Table("tweets_avro", "data-to-scan/tweets_avro", "avro"), tweets)
tableService.create(Table("users", "data-to-scan/users", "parquet"), users.distinct)
tableService.create(Table("users_avro", "data-to-scan/users_avro", "avro"), users.distinct)