package com.aktit.query.service

import com.aktit.query.testmodel.ModelBuilders.{table, tweet}
import com.aktit.query.testmodel.Tweet
import com.aktit.query.util.DirUtils.randomFolder
import com.aktit.query.{AbstractSparkSuite, TestApp}
import com.softwaremill.diffx.scalatest.DiffMatcher.matchTo

/**
  * @author kostas.kougios
  *         31/08/2020 - 15:53
  */
class ConsoleServiceTest extends AbstractSparkSuite {

  import spark.implicits._

  test("scan spark dir") {
    new App {
      val data = Seq(tweet(id = 1, text = "row1"), tweet(id = 2, text = "row2"))
      val dir = randomFolder
      val t = tableService.create(table("tweets", path = dir + "/tweets"), data)
      val scanned = consoleService.scan(dir, "test_")
      scanned.map(_.path) should matchTo(Seq(t.path))
      consoleService.sql("select * from test_tweets").as[Tweet].toSet should matchTo(data.toSet)
    }
  }

  test("scan avro") {
    new App {
      val data = Seq(tweet(id = 1, text = "row1"), tweet(id = 2, text = "row2"))
      val table = createTable("tweet", data, format = "avro")
      val dir = randomFolder
      tableService.export(table, dir + "/tweet.avro")
      consoleService.scan(dir, "test_")
      consoleService.sql("select * from test_tweet").as[Tweet].toSet should matchTo(data.toSet)
    }
  }

  test("scan csv") {
    new App {
      val data = Seq(tweet(id = 1, text = "row1"), tweet(id = 2, text = "row2"))
      val table1 = tableService.create(table("tweet", randomFolder, format = "csv"), data)
      val dir = randomFolder
      tableService.export(table1, dir + "/tweet.csv")
      consoleService.scan(dir, "test_")
      consoleService.sql("select id from test_tweet").as[String].toSet should matchTo(data.map(_.id.toString).toSet)
    }
  }

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
