package com.aktit.query.service

import com.aktit.query.testmodel.ModelBuilders.tweet
import com.aktit.query.testmodel.Tweet
import com.aktit.query.{AbstractSparkSuite, TestApp}
import com.softwaremill.diffx.scalatest.DiffMatcher.matchTo

/**
  * @author kostas.kougios
  *         31/08/2020 - 15:53
  */
class ConsoleServiceTest extends AbstractSparkSuite {

  import spark.implicits._

  test("describeShort") {
    new App {
      val table = createTable("tweet", Seq(tweet()))
      consoleService.describeShort(Seq(table))
      printed should be("tweet(id bigint, by string, text string, dateTime date)")
    }
  }

  test("mount table") {
    new App {
      val table = createTable("tweet", Seq(tweet()))
      val mounted = consoleService.mountTable(table.name, table.path)
      mounted.describeColumnsWithType should be(table.describeColumnsWithType)
      tableService.load(mounted).as[Tweet].toSeq should matchTo(Seq(tweet()))
    }
  }

  test("sql") {
    new App {
      val table = createTable("tweet", Seq(tweet()))
      consoleService.mountTable(table.name, table.path)
      consoleService.sql("select * from tweet").as[Tweet].toSeq should matchTo(Seq(tweet()))
    }
  }

  class App extends TestApp with TableServiceBeans with ConsoleServiceBeans

}
