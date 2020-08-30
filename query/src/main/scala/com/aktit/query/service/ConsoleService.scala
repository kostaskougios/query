package com.aktit.query.service

import com.aktit.query.model.Table
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.LoggerFactory

/**
  * @author kostas.kougios
  *         31/08/2020 - 00:30
  */
class ConsoleService(spark: SparkSession, tableService: TableService) {
  private val logger = LoggerFactory.getLogger(getClass)

  def mountTable(
      name: String,
      path: String,
      format: String = "parquet"
  ): Unit = {
    logger.info(s"Mounting $name from $path")
    tableService.mount(Table(name, path, format))
  }

  def sql(q: String): DataFrame = spark.sql(q)
}

trait ConsoleServiceBeans {
  def spark: SparkSession

  def tableService: TableService

  val consoleService = new ConsoleService(spark, tableService)
}
