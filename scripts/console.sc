import java.io.File
import $cp.^.out.query.assembly.dest.`out.jar`
import com.aktit.query.DI._
import spark.implicits._

import consoleService._

val tables = Seq(
  mountTable("tweets", "/tmp/tweets")
)
terminal(tables)
