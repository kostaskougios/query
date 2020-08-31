import $cp.lib.`out.jar`
import com.aktit.query.DI._
import consoleService._

val tables = Seq(
  mountTable("tweets", "data/tweets"),
  mountTable("users", "data/users")
)
terminal(tables)
