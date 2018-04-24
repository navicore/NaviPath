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

  "An obj" should "handle string query" in {

    val result = jsonString.query[String]("$.widget.debug")

    assert(result.nonEmpty)
    result.fold()(assertResult("on"))

  }

}
