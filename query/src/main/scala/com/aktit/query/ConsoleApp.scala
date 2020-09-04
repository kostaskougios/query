package com.aktit.query

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:16
  */
object ConsoleApp extends App {

  import DI._
  import consoleService._

  val tables = scan("dist/query/data")

  mountAll(tables)
  terminal(mountAll(tables), historyFile = "/tmp/query.history")
}
