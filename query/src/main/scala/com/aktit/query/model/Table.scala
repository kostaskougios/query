package com.aktit.query.model

import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.{Column, DataFrame}

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:19
  */
case class Table(
    name: String,
    path: String,
    format: String,
    csvHeaders: Boolean = true,
    columns: Seq[Column] = Nil
) {
  def withDataFrame(df: DataFrame): Table = copy(columns = df.columns.map(df.col))

  def describeShort: String = s"${Console.MAGENTA}${Console.UNDERLINED}$name${Console.RESET}${Console.CYAN}($describeColumnsWithType${Console.CYAN})${Console.RESET}"

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
          s"${Console.MAGENTA}${a.name} ${Console.YELLOW}${a.dataType.sql.toLowerCase}"
      }
      .mkString(s"${Console.RESET}, ")

  def columnNames: Seq[String] = columns.map(_.expr).map {
    case a: AttributeReference =>
      a.name
  }
}
