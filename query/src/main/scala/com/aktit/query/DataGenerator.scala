package com.aktit.query

import java.time.LocalDate

import com.aktit.query.model.Table
import org.apache.spark.sql.SaveMode

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:30
  */
object DataGenerator extends App {
  val HowMany = 10000L

  case class Tweet(id: Long, by: String, text: String, dateTime: LocalDate)

  case class User(id: String, name: String)

  import DI._

  try {
    println(s"Creating $HowMany rows")
    val tweets = for (i <- 1L to HowMany) yield Tweet(i, s"user${i % 1000}", s"a random tweet by user ${i % 1000}, id=$i", LocalDate.now)
    val users = for (i <- 1L to HowMany) yield User(s"user${i % 1000}", s"Username for ${i % 1000}")
    println("Storing")
    tableService.create(Table("tweets", "dist/build/data/tweets", "parquet"), tweets)
    tableService.create(Table("users", "dist/build/data/users", "parquet"), users.distinct)
  } finally destroy()
}
