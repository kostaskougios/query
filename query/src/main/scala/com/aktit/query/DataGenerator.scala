package com.aktit.query

import java.time.LocalDate

import org.apache.spark.sql.SaveMode

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:30
  */
object DataGenerator extends App {
  val HowMany = 1000000L

  case class Tweet(id: Long, by: String, text: String, dateTime: LocalDate)

  import DI._
  import spark.implicits._

  try {
    println(s"Creating $HowMany tweets")
    val tweets = for (i <- 1L to HowMany) yield Tweet(i, s"user${i % 1000}", s"a random tweet by user ${i % 1000}, id=$i", LocalDate.now)
    println("Storing tweets")
    tweets.toDS.write.mode(SaveMode.Overwrite).parquet("/tmp/tweets")
  } finally destroy()
}
