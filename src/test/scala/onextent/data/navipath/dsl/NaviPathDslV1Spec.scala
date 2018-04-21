package onextent.data.navipath.dsl

import onextent.data.navipath.dsl.NaviPathDslV1._
import com.typesafe.scalalogging.LazyLogging
import org.scalatest._

import scala.io.Source

class NaviPathDslV1Spec extends FlatSpec with LazyLogging {

  val jsonString: String = Source.fromResource("widget.json").mkString

  "A jsonpath" should "find a string" in {

    val result = query path [String] "$.widget.debug" in jsonString

    assert(result.nonEmpty)

    result.fold()(assertResult("on"))

  }

  "A jsonpath" should "find an int" in {

    val result = query path [Int] "$.widget.window.height" in jsonString

    assert(result.nonEmpty)

    result.fold()(assertResult(500))

  }

}
