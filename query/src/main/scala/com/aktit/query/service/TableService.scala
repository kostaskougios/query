package com.aktit.query.service

import com.aktit.query.model.Table
import org.apache.spark.sql.{DataFrame, Encoders, SaveMode, SparkSession}

import scala.reflect.runtime.universe.TypeTag

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:23
  */
class TableService(spark: SparkSession) {

  import spark.implicits._

  def mount(table: Table): Table = {
    val df = load(table)
    df.createOrReplaceTempView(table.name)
    table.withDataFrame(df)
  }

  def load(table: Table): DataFrame = spark.read.format(table.format).load(table.path)

  def create[A <: Product: TypeTag](
      table: Table,
      data: Seq[A]
  ): Table = {
    implicit val encoder = Encoders.product[A]
    val df = data.toDF
    df.write.mode(SaveMode.Overwrite).format(table.format).save(table.path)
    table.withDataFrame(df)
  }

}

trait TableServiceBeans {
  def spark: SparkSession

  lazy val tableService = new TableService(spark)
}
