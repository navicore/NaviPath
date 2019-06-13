package navicore.data.navipath.examples

import java.io.InputStream

import navicore.data.navipath.dsl.NaviPathSyntax._
import org.scalatest._

import scala.io.Source

class j1Spec extends FlatSpec {

  val stream : InputStream = getClass.getResourceAsStream("/examples/j1.json")
  val jsonString: String = Source.fromInputStream(stream).mkString

  "An nested obj" should "handle int query" in {

    val p2 = "$.env.body.Box.ParamOne.TotalAmount"
    val value = jsonString.query[Int](p2)

    assert(value.nonEmpty)
    value.fold()(assertResult(45))

  }

  "An nested UseMe obj" should "handle int query" in {

    val p1 = "$.env.body.Box.ParamOne[?(@['good'] == false)].TotalAmount"
    val value1 = jsonString.query[Int](p1)
    assert(value1.isEmpty)

    val p2 = "$.env.body.Box.ParamOne[?(@['good'] == true)].TotalAmount"
    val value2 = jsonString.query[Int](p2)
    assert(value2.nonEmpty)
    value2.fold()(assertResult(45))

  }

}
