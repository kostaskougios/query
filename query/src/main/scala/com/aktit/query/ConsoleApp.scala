package com.aktit.query

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:16
  */
object ConsoleApp extends App {

  import DI._
  import consoleService._

  val tables = Seq(
    mountTable("tweets", "/tmp/tweets")
  )
  describeShort(tables)
  //  sql("select * from tweets limit 5").show(false)
}
