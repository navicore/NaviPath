package navicore.data.navipath

import com.fasterxml.jackson.databind.node._
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import navicore.data.jsonpath._

import scala.reflect.ClassTag

object FieldByPath {

  def jsonToBaseType[T](ids: Seq[JsonNode])(implicit ct: ClassTag[T]): Option[T] = {
    ids match {
      case _ if ids.nonEmpty && ids.head.getClass == classOf[IntNode] && ct.runtimeClass == classOf[Int] =>
        ids.headOption.map(_.asInt().asInstanceOf[T])
      case _ if ids.nonEmpty && ids.head.getClass == classOf[TextNode] && ct.runtimeClass == classOf[String] =>
        ids.headOption.map(_.asText().asInstanceOf[T])
      case _ if ids.nonEmpty && ids.head.getClass == classOf[IntNode] && ct.runtimeClass == classOf[Long] =>
        ids.headOption.map(_.asLong().asInstanceOf[T])
      case _ if ids.nonEmpty && ids.head.getClass == classOf[LongNode] && ct.runtimeClass == classOf[Long] =>
        ids.headOption.map(_.asLong().asInstanceOf[T])
      case _ if ids.nonEmpty && ids.head.getClass == classOf[BooleanNode] && ct.runtimeClass == classOf[Boolean] =>
        ids.headOption.map(_.asBoolean().asInstanceOf[T])
      case _ if ids.nonEmpty && ids.head.getClass == classOf[DoubleNode] && ct.runtimeClass == classOf[Double] =>
        ids.headOption.map(_.asDouble().asInstanceOf[T])
      case _ =>
        ids.headOption.map(_.asInstanceOf[T])
    }
  }

  def apply[T: ClassTag](json: JsonNode, path: String): Option[T] = {

    val r: Seq[JsonNode] = JsonPath
    .query(path, json)
    .map(_.toVector.toSeq).right.getOrElse(Seq())

    jsonToBaseType[T](r)

  }

  val mapper = new ObjectMapper
  def parseJson(s: String): JsonNode = mapper.readValue(s, classOf[JsonNode])

  def apply[T: ClassTag](data: String, path: String): Option[T] = {

    val json: JsonNode = parseJson(data)

    apply[T](json, path)

  }

  def apply[T: ClassTag](data: String, paths: List[String]): List[Option[T]] = {

    val json: JsonNode = parseJson(data)

    paths.map(path => {

      apply[T](json, path)

    })

  }

}
