package com.aktit.query

import com.aktit.query.service.ConsoleService

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:16
  */
object ConsoleApp extends App {
  ConsoleService.initConsole()

  import DI._
  import consoleService._

  val tables = scan("dist/query/data")

  mountAll(tables)
  terminal(mountAll(tables), historyFile = "/tmp/query.history")
}
