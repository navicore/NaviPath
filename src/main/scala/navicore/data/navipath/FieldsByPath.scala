package navicore.data.navipath

import com.fasterxml.jackson.databind.node._
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import io.gatling.jsonpath._

import scala.reflect.ClassTag

object FieldsByPath {

  def jsonToBaseType[T](ids: Seq[JsonNode])(implicit ct: ClassTag[T]): Seq[T] = ids match {
    case ids: Seq[TextNode] if ids.nonEmpty && ids.head.getClass == classOf[TextNode] && ct.runtimeClass ==  classOf[String] =>
      ids.map(_.asText().asInstanceOf[T])
    case ids: Seq[IntNode] if ids.nonEmpty && ids.head.getClass == classOf[IntNode] && ct.runtimeClass == classOf[Int] =>
      ids.map(_.asInt().asInstanceOf[T])
    case ids: Seq[IntNode] if ids.nonEmpty && ids.head.getClass == classOf[IntNode] && ct.runtimeClass == classOf[Long] =>
      ids.map(_.asLong().asInstanceOf[T])
    case ids: Seq[LongNode] if ids.nonEmpty && ids.head.getClass == classOf[LongNode] && ct.runtimeClass == classOf[Long] =>
      ids.map(_.asLong().asInstanceOf[T])
    case ids: Seq[DoubleNode] if ids.nonEmpty && ids.head.getClass == classOf[DoubleNode] && ct.runtimeClass == classOf[Double] =>
      ids.map(_.asDouble().asInstanceOf[T])
    case _ =>
      ids.map(_.asInstanceOf[T])
  }

  def apply[T: ClassTag](json: JsonNode, path: String): Option[List[T]] = {

    JsonPath
      .query(path, json)
      .right
      .map(_.toVector.toSeq) match {
      case Right(ids) if ids.nonEmpty => Some(jsonToBaseType[T](ids).toList)
      case _          => None
    }

  }

  def apply[T: ClassTag](data: String, path: String): Option[List[T]] = {

    val json: JsonNode = (new ObjectMapper).readValue(data, classOf[JsonNode])

    apply[T](json, path)

  }


}
