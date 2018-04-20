package onextent.data.navipath

import com.typesafe.scalalogging.LazyLogging
import org.scalatest._

import scala.io.Source

class FieldByPathSpec extends FlatSpec with LazyLogging {

  val jsonString: String = Source.fromResource("widget.json").mkString

  "Root" should "not have debug " in {
    val debugField = FieldByPath[String](jsonString, "$.debug")
    assert(debugField.isEmpty)
  }

  "Widget" should "debug " in {
    val debugField: Option[Nothing] = FieldByPath(jsonString, "$.widget.debug")
    assert(debugField === Some("on"))
  }

  "Window" should "have int height " in {
    val heightOpt: Option[Int] = FieldByPath[Int](jsonString, "$.widget.window.height")
    assert(heightOpt === Some(500))
  }

}
