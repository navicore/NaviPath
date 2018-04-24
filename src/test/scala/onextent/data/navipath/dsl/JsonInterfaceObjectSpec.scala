package onextent.data.navipath.dsl

import com.typesafe.scalalogging.LazyLogging
import org.scalatest._

class JsonInterfaceObjectSpec extends FlatSpec with LazyLogging {

  // Define a very simple JSON AST
  sealed trait Json
  final case class JsObject(get: Map[String, Json]) extends Json
  final case class JsString(get: String) extends Json
  final case class JsNumber(get: Double) extends Json
  case object JsNull extends Json

  // The "serialize to JSON" behaviour is encoded in this trait
  trait JsonWriter[A] {
    def write(value: A): Json
  }

  final case class Person(name: String, email: String)

  object JsonWriterInstances {
    implicit val stringWriter: JsonWriter[String] =
      (value: String) => JsString(value)

    implicit val personWriter: JsonWriter[Person] =
      new JsonWriter[Person] {
        def write(value: Person): Json =
          JsObject(
            Map(
              "name" -> JsString(value.name),
              "email" -> JsString(value.email)
            ))
      }
  }

  object Json {
    def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
      w.write(value)
  }

  "An obj" should "be json" in {

    import JsonWriterInstances._
    val result = Json.toJson(Person("Dave", "dave@example.com"))
    assert(result.toString.contains("dave"))
    assert(result.toString.contains("Dave"))
    assert(!result.toString.contains("davie"))

  }

}
