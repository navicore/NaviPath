package onextent.data.navipath

import com.typesafe.scalalogging.LazyLogging
import org.scalatest._

import scala.io.Source

class NaviPathSpec extends FlatSpec with LazyLogging {

  val jsonString: String = Source.fromResource("widget.json").mkString

  import PathReaderInstances._

  "Widget" should "have debug " in {
    //val debugField = jsonString./
    //assert(debugField.isEmpty)
  }

}
