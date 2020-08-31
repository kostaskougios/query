package com.aktit.query.model

import org.apache.spark.sql.Column
import org.apache.spark.sql.catalyst.expressions.AttributeReference

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:19
  */
case class Table(
    name: String,
    path: String,
    format: String,
    columns: Seq[Column] = Nil
) {
  def describeShort: String = s"$name($describeColumnsWithType)"

  def describe: String =
    s"""
       |Table   : ${name}
       |At      : ${path}
       |Columns : ${describeColumnsWithType} 
      """.stripMargin

  def describeColumnsWithType =
    columns
      .map(_.expr)
      .map {
        case a: AttributeReference =>
          s"${a.name} ${a.dataType.sql}"
      }
      .mkString(", ")
}
