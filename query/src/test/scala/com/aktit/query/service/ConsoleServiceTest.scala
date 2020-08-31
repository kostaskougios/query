package com.aktit.query.service

import com.aktit.query.testmodel.ModelBuilders.tweet
import com.aktit.query.{AbstractSparkSuite, TestApp}

/**
  * @author kostas.kougios
  *         31/08/2020 - 15:53
  */
class ConsoleServiceTest extends AbstractSparkSuite {
  test("mount table") {
    new App {
      val table = createTable("tweet", Seq(tweet()))
      consoleService.describeShort(Seq(table))
      printed should be("tweet(id bigint, by string, text string, dateTime date)")
    }
  }

  class App extends TestApp with TableServiceBeans with ConsoleServiceBeans

}
