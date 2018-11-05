package navicore.data.navipath

import java.io.InputStream

import org.scalatest._

import scala.io.Source

class FieldByPathSpec extends FlatSpec {

  val stream : InputStream = getClass.getResourceAsStream("/widget.json")
  val jsonString: String = Source.fromInputStream(stream).mkString

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
