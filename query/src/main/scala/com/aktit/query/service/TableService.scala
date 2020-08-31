package com.aktit.query.service

import com.aktit.query.model.Table
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:23
  */
class TableService(spark: SparkSession) {
  def mount(table: Table): Table = {
    val df = load(table)
    df.createOrReplaceTempView(table.name)
    table.copy(columns = df.columns.map(df.col))
  }

  def load(table: Table): DataFrame = spark.read.format(table.format).load(table.path)
}

trait TableServiceBeans {
  def spark: SparkSession

  val tableService = new TableService(spark)
}
