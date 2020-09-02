package com.aktit.query.service

import java.io.File

import com.aktit.query.model.Table
import com.aktit.query.util.DirUtils.randomFolder
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

  def load(table: Table): DataFrame = spark.read.format(table.format).option("header", table.csvHeaders).load(table.path)

  def create[A <: Product: TypeTag](
      table: Table,
      data: Seq[A]
  ): Table = {
    implicit val encoder = Encoders.product[A]
    val df = data.toDF.coalesce(1)
    prepareToWrite(table, df).save(table.path)
    table.withDataFrame(df)
  }

  def export(table: Table, targetFile: String): File = {
    val target = randomFolder
    prepareToWrite(table, load(table).coalesce(1)).save(target)
    val parts = new File(target).listFiles.filter(_.getName.startsWith("part-"))
    if (parts.length != 1) throw new IllegalStateException(s"1 part expected but got ${parts.mkString(", ")}")
    val f = parts.head
    val t = new File(targetFile)
    t.getParentFile.mkdirs()
    if (!f.renameTo(t)) throw new IllegalStateException(s"couldn't move $f to $targetFile")
    t
  }

  private def prepareToWrite(table: Table, df: DataFrame) =
    df.write
      .mode(SaveMode.Overwrite)
      .format(table.format)
      .option("header", table.csvHeaders)
}

trait TableServiceBeans {
  def spark: SparkSession

  lazy val tableService = new TableService(spark)
}
