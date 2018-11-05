package navicore.data.navipath

import com.fasterxml.jackson.databind.ObjectMapper
import navicore.data.jsonpath.JsonPath

object FieldsByPath {

  def apply[T](json: Object, path: String): Option[List[T]] = {

    JsonPath
      .query(path, json)
      .right
      .map(_.toVector.toSeq) match {
      case Right(ids) if ids.nonEmpty => Some(ids.toList.asInstanceOf[List[T]])
      case _          => None
    }

  }

  def apply[T](data: String, path: String): Option[List[T]] = {

    val json: Object = (new ObjectMapper).readValue(data, classOf[Object])

    apply[T](json, path)

  }


}
