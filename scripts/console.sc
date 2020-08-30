import java.io.File
import $cp.^.out.query.assembly.dest.`out.jar`

//val path = java.nio.file.FileSystems.getDefault.getPath(new File(".").getAbsolutePath+"/out/query/assembly/dest/out.jar")
//val x = ammonite.ops.Path(path)
//println(x)
//interp.load.cp(x)
//@

import com.aktit.query.DI._
import spark.implicits._

try {
  println("OK")
} finally destroy()
