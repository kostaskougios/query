package com.aktit.query.console

import org.apache.commons.lang3.StringUtils

object ColorUtils {
  private val ConsoleFormatting = Array(Console.UNDERLINED, Console.RESET, Console.CYAN, Console.YELLOW, Console.GREEN, Console.MAGENTA, "[35m")
  private val Empty = Array.fill(ConsoleFormatting.length)("")
  def removeColor(s: String): String =
    StringUtils.replaceEach(s, ConsoleFormatting, Empty)
}
