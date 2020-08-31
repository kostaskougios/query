import java.io.File
import $cp.^.out.query.assembly.dest.`out.jar`
import com.aktit.query.DI._
import spark.implicits._

try {
  import consoleService._
  val tables = Seq(
    mountTable("tweets", "/tmp/tweets")
  )
  sql("select * from tweets limit 5").show(false)
} finally destroy()
