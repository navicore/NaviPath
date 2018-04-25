package onextent.data.navipath.dsl

import com.typesafe.scalalogging.LazyLogging
import org.scalatest._
import NaviPathSyntax._

import scala.io.Source

class NaviPathDslV2Spec extends FlatSpec with LazyLogging {

  val jsonString: String = Source.fromResource("widget.json").mkString

  "An obj" should "work with query" in {

    val result = NaviPath.query[String, String](jsonString, "$.widget.debug")

    assert(result.nonEmpty)
    result.fold()(assertResult("on"))

  }

  "An obj" should "handle int query" in {

    val height = jsonString.query[Int]("$.widget.window.height")

    assert(height.nonEmpty)
    height.fold()(assertResult(500))

  }

  "An obj" should "handle long query" in {

    val height = jsonString.query[Long]("$.widget.lval")

    assert(height.nonEmpty)
    height.fold()(assertResult(110000000))

  }

  "An obj" should "handle double query" in {

    val height = jsonString.query[Long]("$.widget.dval")

    assert(height.nonEmpty)
    height.fold()(assertResult(11.123))

  }

  "An obj" should "handle string query" in {

    val result = jsonString.query[String]("$.widget.debug")

    assert(result.nonEmpty)
    result.fold()(assertResult("on"))

  }

  "An obj" should "handle list of strings query" in {

    val results = jsonString.query[List[String]]("$.widget.stuff[*].name")

    assert(results.nonEmpty)
    results.fold()(r => assert(r.length == 2))
    results.fold()(r => assert(r.headOption.contains("one")))
    results.fold()(r => assert(r(1) == "two"))

  }

  "An obj" should "handle list of int query" in {

    val results = jsonString.query[List[Int]]("$.widget.stuff[*].value")

    assert(results.nonEmpty)
    results.fold()(r => assert(r.length == 2))
    results.fold()(r => assert(r.headOption.contains(1)))
    results.fold()(r => assert(r(1) == 2))

  }

  "An obj" should "handle list of doubles query" in {

    val results = jsonString.query[List[Double]]("$.widget.stuff[*].dvalue")

    assert(results.nonEmpty)
    results.fold()(r => assert(r.length == 2))
    results.fold()(r => assert(r.headOption.contains(11.01)))
    results.fold()(r => assert(r(1) == 22.0))

  }

}
