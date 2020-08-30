package com.aktit.query.service

import java.util.UUID

import org.apache.commons.io.FileUtils
import org.apache.spark.sql.SparkSession

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:27
  */
trait SparkBeans {
  val spark: SparkSession = {
    val tmpDir = FileUtils.getTempDirectoryPath + "/query/" + UUID.randomUUID
    SparkSession.builder
      .master("local[*]")
      .appName("query")
      .config("spark.ui.enabled", false)
      .config("spark.sql.shuffle.partitions", 4)
      .config("spark.sql.warehouse.dir", tmpDir)
      .getOrCreate
  }

  def destroy(): Unit = spark.close()
}
