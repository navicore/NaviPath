package onextent.data.navipath.dsl

import com.typesafe.scalalogging.LazyLogging
import org.scalatest._

class JsonInterfaceSyntaxSpec extends FlatSpec with LazyLogging {

  // Define a very simple JSON AST
  sealed trait MyJson
  final case class JsObject(get: Map[String, MyJson]) extends MyJson
  final case class JsString(get: String) extends MyJson
  final case class JsNumber(get: Double) extends MyJson
  case object JsNull extends MyJson

  // The "serialize to JSON" behaviour is encoded in this trait
  trait MyJsonWriter[A] {
    def write(value: A): MyJson
  }

  final case class Human(name: String, email: String)

  object MyJsonWriterInstances {

    implicit val stringWriter: MyJsonWriter[String] =
      (value: String) => JsString(value)

    implicit val humanWriter: MyJsonWriter[Human] =
      (value: Human) => JsObject(
        Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))

  }

  object MyJsonSyntax {

    implicit class MyJsonWriterOps[A](value: A) {
      def toJson(implicit w: MyJsonWriter[A]): MyJson =
        w.write(value)
    }

  }

  "An obj" should "be json via syntax" in {

    import MyJsonWriterInstances._
    import MyJsonSyntax._

    val dave = Human("Dave", "dave@example.com")
    println(s"ejs syntax 1 ${dave.toJson}")

    val daveJson = Human("Dave", "dave@example.com").toJson
    println(s"ejs syntax 2 $daveJson")

  }

}
