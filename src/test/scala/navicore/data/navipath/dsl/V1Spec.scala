package navicore.data.navipath.dsl

import java.io.InputStream

import navicore.data.navipath.dsl.NaviPathDslV1._
import org.scalatest._

import scala.io.Source
import org.scalatest.flatspec.AnyFlatSpec

class V1Spec extends AnyFlatSpec {

  val stream : InputStream = getClass.getResourceAsStream("/widget.json")
  val jsonString: String = Source.fromInputStream(stream).mkString

  "A jsonpath" should "find a string" in {

    val result: Option[String] = query path [String] "$.widget.debug" in jsonString

    assert(result.nonEmpty)

    result.fold()(x => assertResult("on")(x))

  }

  "A jsonpath" should "find an int" in {

    val result = query path [Int] "$.widget.window.height" in jsonString

    assert(result.nonEmpty)

    result.fold()(assertResult(500))

  }

}
