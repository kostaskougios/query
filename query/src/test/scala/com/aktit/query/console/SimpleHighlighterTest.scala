package com.aktit.query.console

import com.aktit.query.console.SimpleHighlighter.split
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

/**
  * @author kostas.kougios
  *         06/09/2020 - 23:38
  */
class SimpleHighlighterTest extends AnyFunSuite {
  test("split by space") {
    split("select * from tweet") should be(Seq("select", " ", "*", " ", "from", " ", "tweet"))
  }

  test("split by parenthesis") {
    split("select count(*) from tweet") should be(Seq("select", " ", "count", "(", "*", ")", " ", "from", " ", "tweet"))
  }

  test("split mix case") {
    split("Select Count(*) From Tweet") should be(Seq("Select", " ", "Count", "(", "*", ")", " ", "From", " ", "Tweet"))
  }

  test("numbers +") {
    split("45+25") should be(Seq("45", "+", "25"))
  }

}
