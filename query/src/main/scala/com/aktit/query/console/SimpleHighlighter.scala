package com.aktit.query.console

import java.util.StringTokenizer
import java.util.regex.Pattern

import com.aktit.query.sqlsyntax.SqlSyntax
import org.jline.reader.{Highlighter, LineReader}
import org.jline.utils.{AttributedStringBuilder, AttributedStyle}

import scala.collection.JavaConverters.enumerationAsScalaIteratorConverter

/**
  * @author kostas.kougios
  *         05/09/2020 - 22:59
  */
class SimpleHighlighter extends Highlighter {
  private val KeywordStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold()
  private val keywords = SqlSyntax.Keywords.toSet

  override def highlight(reader: LineReader, buffer: String) = {
    val builder = new AttributedStringBuilder
    val tokenizer = new StringTokenizer(buffer, " ", true)
    val tokens = tokenizer.asScala.map(_.asInstanceOf[String])
    for (text <- tokens) {
      val t = text.toLowerCase
      if (keywords(t)) {
        builder.style(KeywordStyle)
      }
      builder.append(text)
      builder.style(AttributedStyle.DEFAULT)
    }
    builder.toAttributedString
  }

  override def setErrorPattern(errorPattern: Pattern) = {
    println(errorPattern)
  }

  override def setErrorIndex(errorIndex: Int) = {
    println(errorIndex)
  }
}
