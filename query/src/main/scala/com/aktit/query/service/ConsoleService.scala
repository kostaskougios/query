package com.aktit.query.service

import java.io.{ByteArrayOutputStream, File, PrintStream}

import ch.qos.logback.core.util.StatusPrinter
import com.aktit.query.console.{Out, SimpleHighlighter}
import com.aktit.query.model.Table
import com.aktit.query.sqlsyntax.SqlSyntax
import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.jline.builtins.Options.HelpException
import org.jline.reader.impl.DefaultParser
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.reader.impl.history.DefaultHistory
import org.jline.reader.{EndOfFileException, LineReader, LineReaderBuilder}
import org.jline.terminal.{Terminal, TerminalBuilder}

import scala.annotation.tailrec
import scala.collection.JavaConverters.asJavaIterableConverter

/**
  * @author kostas.kougios
  *         31/08/2020 - 00:30
  */
class ConsoleService(out: Out, spark: SparkSession, tableService: TableService) {
  def scan(dir: String, tableNamePrefix: String = "", csvHeaders: Boolean = true): Seq[Table] = {
    val scanned = for (f <- Option(new File(dir).listFiles).map(_.toList).getOrElse(Nil)) yield {
      val tableName = tableNamePrefix + fileToTableName(f)

      val ext = StringUtils.substringAfterLast(f.getName, ".")
      val format =
        if (f.isFile && Formats(ext))
          Some(ext)
        else if (f.isDirectory) detectFormat(f)
        else None

      format match {
        case Some(format) =>
          Some(Table(tableName, f.getAbsolutePath, format, csvHeaders))
        case None =>
          out.error(s"Can't detect format of ${f.getAbsolutePath}. These extensions/formats are supported :${Formats.mkString(", ")}")
          None
      }
    }
    scanned.flatten
  }

  private val Formats = Set("parquet", "avro", "orc", "csv")

  private def detectFormat(dir: File) = {
    val formats = dir.listFiles.map(_.getName).map(n => StringUtils.substringAfterLast(n, ".")).filter(Formats.contains).toSet
    if (formats.size == 1) formats.headOption else None
  }

  private def fileToTableName(f: File) = StringUtils.substringBeforeLast(f.getName.replaceAll(" ", "_").replaceAll("-", "_"), ".")

  def table(
      name: String,
      path: String,
      format: String = "parquet",
      csvHeaders: Boolean = true
  ) = Table(name, path, format, csvHeaders)

  def mount(tables: Table*): Seq[Table] = mountAll(tables)

  def mountAll(tables: Seq[Table]): Seq[Table] = tables.map { table =>
    out.println(s"Mounting ${Console.GREEN}${table.name}${Console.RESET} from ${Console.MAGENTA}${table.path}${Console.RESET}")
    tableService.mount(table)
  }

  def sql(q: String): DataFrame = spark.sql(q)

  def describe(tables: Seq[Table]) = out.println(
    tables.map(_.describe).mkString("\n")
  )

  def describeShort(tables: Seq[Table]) = out.println(
    tables.map(_.describeShort).mkString("\n")
  )

  def terminal(
      tables: Seq[Table],
      historyFile: String = "query.history",
      historySize: Int = 100
  ): Unit = {
    out.yellowColour()
    out.println("? or ?select or ?? for help")
    out.normalColour()
    val t = TerminalBuilder.builder.build()
    val p = new DefaultParser
    val c = autoComplete(tables)
    val history = new DefaultHistory
    val reader = LineReaderBuilder
      .builder()
      .appName("query")
      .terminal(t)
      .completer(c)
      .highlighter(new SimpleHighlighter(keywordsFromTables(tables).toSet))
      .parser(p)
      .variable(LineReader.HISTORY_FILE, historyFile)
      .variable(LineReader.HISTORY_FILE_SIZE, historySize)
      .history(history)
      .build

    try terminalLoop(t, reader, tables)
    catch {
      case _: EndOfFileException =>
    } finally {
      history.save()
    }
  }

  private def autoComplete(tables: Seq[Table]) = {
    val tableAC = keywordsFromTables(tables)
    new StringsCompleter((tableAC ++ SqlSyntax.Keywords).asJava)
  }

  private def keywordsFromTables(tables: Seq[Table]) = {
    tables.map(_.name) ++ tables.flatMap(_.columnNames)
  }

  @tailrec
  private def terminalLoop(terminal: Terminal, reader: LineReader, tables: Seq[Table]): Unit = {
    val line = reader.readLine(s"${Console.BOLD + Console.RED_B}>${Console.RESET} ").trim
    try {
      out.cyanColour()
      line match {
        case "?" =>
          out.greenColour()
          describeShort(tables)
        case "??" =>
          out.greenColour()
          describe(tables)
        case "" =>
        case "?select" =>
          tables.map(_.toSelect).foreach(out.println)
        case q =>
          sql(q).show(1000000, false)
      }
    } catch {
      case ex: Throwable =>
        HelpException.highlight(ex.getMessage, HelpException.defaultStyle()).print(terminal)
    } finally {
      out.normalColour()
    }
    terminalLoop(terminal, reader, tables)
  }

}

object ConsoleService {
  def initConsole(): Unit = {
    // mute initial logback logging
    StatusPrinter.setPrintStream(new PrintStream(new ByteArrayOutputStream))
  }
}

trait ConsoleServiceBeans {
  def out: Out

  def spark: SparkSession

  def tableService: TableService

  lazy val consoleService = new ConsoleService(out, spark, tableService)
}
