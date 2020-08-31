package com.aktit.query.testmodel

import java.time.LocalDate

import com.aktit.query.model.Table
import org.apache.spark.sql.Column

/**
  * @author kostas.kougios
  *         31/08/2020 - 16:16
  */
object ModelBuilders {
  def table(
      name: String = "test",
      path: String = "/tmp/table",
      format: String = "parquet",
      columns: Seq[Column] = Nil
  ) = Table(
    name,
    path,
    format,
    columns
  )

  def tweet(
      id: Long = 1,
      by: String = "by me",
      text: String = "a new day rises",
      dateTime: LocalDate = LocalDate.now
  ) = Tweet(id, by, text, dateTime)
}
