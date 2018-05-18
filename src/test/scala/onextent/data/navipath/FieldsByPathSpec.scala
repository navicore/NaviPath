package onextent.data.navipath

import java.io.InputStream

import org.scalatest._

import scala.io.Source

class FieldsByPathSpec extends FlatSpec {

  val stream : InputStream = getClass.getResourceAsStream("/widget.json")
  val jsonString: String = Source.fromInputStream(stream).mkString

  "stuff" should "have names " in {
    val names: Option[List[String]] = FieldsByPath[String](jsonString, "$.widget.stuff[*].name")
    assert(names.contains(List("one", "two")))
  }

}
