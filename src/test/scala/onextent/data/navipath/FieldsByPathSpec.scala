package onextent.data.navipath

import com.typesafe.scalalogging.LazyLogging
import org.scalatest._

import scala.io.Source

class FieldsByPathSpec extends FlatSpec with LazyLogging {

  val jsonString: String = Source.fromResource("widget.json").mkString

  "stuff" should "have names " in {
    val names: Option[List[String]] = FieldsByPath[String](jsonString, "$.widget.stuff[*].name")
    assert(names.get === List("one", "two"))
  }

}
