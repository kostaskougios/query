package com.aktit.query.service

import com.aktit.query.console.Out
import com.aktit.query.model.Table
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.jline.reader.impl.DefaultParser
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.TerminalBuilder

import scala.annotation.tailrec

/**
  * @author kostas.kougios
  *         31/08/2020 - 00:30
  */
class ConsoleService(out: Out, spark: SparkSession, tableService: TableService) {

  def mountTable(
      name: String,
      path: String,
      format: String = "parquet"
  ): Table = {
    out.println(s"Mounting $name from $path")
    tableService.mount(Table(name, path, format))
  }

  def sql(q: String): DataFrame = spark.sql(q)

  def describe(tables: Seq[Table]) = out.println(
    tables.map(_.describe).mkString("\n")
  )

  def describeShort(tables: Seq[Table]) = out.println(
    tables.map(_.describeShort).mkString("\n")
  )

  def terminal(tables: Seq[Table]): Unit = {
    out.println("? or ?? for help")
    val t = TerminalBuilder.builder.build()
    val c = new StringsCompleter("foo", "bar", "baz")
    val p = new DefaultParser
    val reader = LineReaderBuilder
      .builder()
      .terminal(t)
      .completer(c)
      .parser(p)
      .build

    terminalLoop(reader, tables)
  }

  @tailrec
  private def terminalLoop(reader: LineReader, tables: Seq[Table]): Unit = {
    val line = reader.readLine("> ").trim
    try {
      line match {
        case "?"  => describeShort(tables)
        case "??" => describe(tables)
        case q =>
          sql(q).show(1000000, false)
      }
    } catch {
      case ex: Throwable => out.error(ex.getMessage)
    }
    terminalLoop(reader, tables)
  }
}

trait ConsoleServiceBeans {
  def out: Out

  def spark: SparkSession

  def tableService: TableService

  lazy val consoleService = new ConsoleService(out, spark, tableService)
}
