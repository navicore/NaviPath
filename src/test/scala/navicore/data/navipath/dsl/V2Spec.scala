package navicore.data.navipath.dsl

import java.io.InputStream

import com.fasterxml.jackson.databind.JsonNode
import org.scalatest._
import navicore.data.navipath.dsl.NaviPathSyntax._

import scala.io.Source
import org.scalatest.flatspec.AnyFlatSpec

class V2Spec extends AnyFlatSpec {

  val stream : InputStream = getClass.getResourceAsStream("/widget.json")
  val jsonString: String = Source.fromInputStream(stream).mkString

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

  "An obj" should "handle boolean query" in {

    val isTall = jsonString.query[Boolean]("$.widget.isTall")

    assert(isTall.nonEmpty)
    isTall.fold()(assertResult(true))

  }

  "An obj" should "handle double query" in {

    val height = jsonString.query[Double]("$.widget.dval")

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

  "An obj" should "handle list objects query" in {

    val results: Option[List[JsonNode]] = jsonString.query[List[JsonNode]]("$.widget.stuff[*]")

    assert(results.nonEmpty)
    assert(results.get.size == 2)
    assert(results.get.head.query[String]("$.name").contains("one"))

  }

  "A parsed obj" should "be able to be queried multiple times" in {

    val parsedJson = jsonString.asJson

    val height = parsedJson.query[Int]("$.widget.window.height")
    assert(height.nonEmpty)
    height.fold()(assertResult(500))

    val result = parsedJson.query[String]("$.widget.debug")
    assert(result.nonEmpty)
    result.fold()(assertResult("on"))

    val results = parsedJson.query[List[String]]("$.widget.stuff[*].name")
    assert(results.nonEmpty)
    results.fold()(r => assert(r.length == 2))
    results.fold()(r => assert(r.headOption.contains("one")))

    val obj = parsedJson.query[String]("$.widget.debug")
    assert(results.nonEmpty)
    obj.fold()(r => assert(r.equals("on"), r))

  }

  "A parsed obj" should "be tested for presence of field" in {

    val parsedJson = jsonString.asJson

    val height = parsedJson.query[Object]("$.widget.window.height")
    assert(height.nonEmpty)
    height.fold()(r => assert(r.toString == 500.toString))

    assert( parsedJson.query[Object]("$.widget.window.height").nonEmpty )
    assert( parsedJson.query[Object]("$.widget.window.nothhere").isEmpty )

  }

}
