package com.aktit.query.service

import java.io.File

import com.aktit.query.console.Out
import com.aktit.query.model.Table
import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.jline.reader.impl.DefaultParser
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.reader.{EndOfFileException, LineReader, LineReaderBuilder}
import org.jline.terminal.TerminalBuilder

import scala.annotation.tailrec
import scala.collection.JavaConverters.asJavaIterableConverter

/**
  * @author kostas.kougios
  *         31/08/2020 - 00:30
  */
class ConsoleService(out: Out, spark: SparkSession, tableService: TableService) {

  def scan(dir: String, tableNamePrefix: String = "", csvHeaders: Boolean = true) = {
    for (f <- new File(dir).listFiles) {
      val tableName = tableNamePrefix + fileToTableName(f)
      if (f.getName.endsWith(".avro")) mountTable(tableName, f.getAbsolutePath, format = "avro")
      if (f.getName.endsWith(".csv")) mountTable(tableName, f.getAbsolutePath, format = "csv", csvHeaders = csvHeaders)
    }
  }

  private def fileToTableName(f: File) = StringUtils.substringBeforeLast(f.getName.replaceAll(" ", "_").replaceAll("-", "_"), ".")

  def mountTable(
      name: String,
      path: String,
      format: String = "parquet",
      csvHeaders: Boolean = true
  ): Table = {
    out.println(s"Mounting $name from $path")
    tableService.mount(Table(name, path, format, csvHeaders))
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
    val p = new DefaultParser
    val c = autoComplete(tables)
    val reader = LineReaderBuilder
      .builder()
      .terminal(t)
      .completer(c)
      .parser(p)
      .build

    try terminalLoop(reader, tables)
    catch {
      case _: EndOfFileException =>
    }
  }

  private def autoComplete(tables: Seq[Table]) = {
    val tableAC = tables.map(_.name) ++ tables.flatMap(_.columnNames)
    val keywords = Seq("select", "from", "group", "asc", "desc", "in", "show", "with", "msck", "explain", "describe", "analyze", "refresh", "limit")
    new StringsCompleter((tableAC ++ keywords).asJava)
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
