package com.aktit.query.service

import com.aktit.query.console.Out
import com.aktit.query.model.Table
import org.apache.spark.sql.{DataFrame, SparkSession}

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

}

trait ConsoleServiceBeans {
  def out: Out

  def spark: SparkSession

  def tableService: TableService

  lazy val consoleService = new ConsoleService(out, spark, tableService)
}
