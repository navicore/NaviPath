package navicore.data.navipath.dsl

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import navicore.data.navipath.{FieldByPath, FieldsByPath}

import scala.reflect.ClassTag

trait JsonWriter[A] {
  def write(value: A): JsonNode
}

trait NaviPathQuery[A, B] {
  def execute(value: A, path: String): Option[B]
}

object NaviPath {
  def query[A, B](value: A, path: String)(
      implicit w: NaviPathQuery[A, B]): Option[B] =
    w.execute(value, path)
}

object NaviPathSyntax {

  // obj ifc

  implicit val stringStringQuery: NaviPathQuery[String, String] =
    new NaviPathQuery[String, String] {
      override def execute(value: String, path: String): Option[String] =
        FieldByPath[String](value, path)
    }
  implicit val stringJsonNodeQuery: NaviPathQuery[JsonNode, String] =
    new NaviPathQuery[JsonNode, String] {
      override def execute(value: JsonNode, path: String): Option[String] =
        FieldByPath[String](value, path)
    }

  implicit val intStringQuery: NaviPathQuery[String, Int] =
    new NaviPathQuery[String, Int] {
      override def execute(value: String, path: String): Option[Int] =
        FieldByPath[Int](value, path)
    }

  implicit val intJsonNodeQuery: NaviPathQuery[JsonNode, Int] =
    new NaviPathQuery[JsonNode, Int] {
      override def execute(value: JsonNode, path: String): Option[Int] =
        FieldByPath[Int](value, path)
    }

  implicit val longStringQuery: NaviPathQuery[String, Long] =
    new NaviPathQuery[String, Long] {
      override def execute(value: String, path: String): Option[Long] =
        FieldByPath[Long](value, path)
    }

  implicit val longJsonNodeQuery: NaviPathQuery[JsonNode, Long] =
    new NaviPathQuery[JsonNode, Long] {
      override def execute(value: JsonNode, path: String): Option[Long] =
        FieldByPath[Long](value, path)
    }

  implicit val boolStringQuery: NaviPathQuery[String, Boolean] =
    new NaviPathQuery[String, Boolean] {
      override def execute(value: String, path: String): Option[Boolean] =
        FieldByPath[Boolean](value, path)
    }

  implicit val boolJsonNodeQuery: NaviPathQuery[JsonNode, Boolean] =
    new NaviPathQuery[JsonNode, Boolean] {
      override def execute(value: JsonNode, path: String): Option[Boolean] =
        FieldByPath[Boolean](value, path)
    }

  implicit val doubleStringQuery: NaviPathQuery[String, Double] =
    new NaviPathQuery[String, Double] {
      override def execute(value: String, path: String): Option[Double] =
        FieldByPath[Double](value, path)
    }

  implicit val doubleJsonNodeQuery: NaviPathQuery[JsonNode, Double] =
    new NaviPathQuery[JsonNode, Double] {
      override def execute(value: JsonNode, path: String): Option[Double] =
        FieldByPath[Double](value, path)
    }

  implicit val stringListStringQuery: NaviPathQuery[String, List[String]] =
    new NaviPathQuery[String, List[String]] {
      override def execute(value: String, path: String): Option[List[String]] =
        FieldsByPath[String](value, path)
    }

  implicit val stringListJsonNodeQuery: NaviPathQuery[JsonNode, List[String]] =
    new NaviPathQuery[JsonNode, List[String]] {
      override def execute(value: JsonNode,
                           path: String): Option[List[String]] =
        FieldsByPath[String](value, path)
    }

  implicit val intListStringQuery: NaviPathQuery[String, List[Int]] =
    new NaviPathQuery[String, List[Int]] {
      override def execute(value: String, path: String): Option[List[Int]] =
        FieldsByPath[Int](value, path)
    }

  implicit val intListJsonNodeQuery: NaviPathQuery[JsonNode, List[Int]] =
    new NaviPathQuery[JsonNode, List[Int]] {
      override def execute(value: JsonNode, path: String): Option[List[Int]] =
        FieldsByPath[Int](value, path)
    }

  implicit val longListStringQuery: NaviPathQuery[String, List[Long]] =
    new NaviPathQuery[String, List[Long]] {
      override def execute(value: String, path: String): Option[List[Long]] =
        FieldsByPath[Long](value, path)
    }

  implicit val longListJsonNodeQuery: NaviPathQuery[JsonNode, List[Long]] =
    new NaviPathQuery[JsonNode, List[Long]] {
      override def execute(value: JsonNode, path: String): Option[List[Long]] =
        FieldsByPath[Long](value, path)
    }

  implicit val doubleListStringQuery: NaviPathQuery[String, List[Double]] =
    new NaviPathQuery[String, List[Double]] {
      override def execute(value: String, path: String): Option[List[Double]] =
        FieldsByPath[Double](value, path)
    }

  implicit val doubleListJsonNodeQuery: NaviPathQuery[JsonNode, List[Double]] =
    new NaviPathQuery[JsonNode, List[Double]] {
      override def execute(value: JsonNode,
                           path: String): Option[List[Double]] =
        FieldsByPath[Double](value, path)
    }

  implicit val objectListStringQuery: NaviPathQuery[String, List[JsonNode]] =
    new NaviPathQuery[String, List[JsonNode]] {
      override def execute(value: String,
                           path: String): Option[List[JsonNode]] =
        FieldsByPath[JsonNode](value, path)
    }

  implicit val objectListJsonNodeQuery
    : NaviPathQuery[JsonNode, List[JsonNode]] =
    new NaviPathQuery[JsonNode, List[JsonNode]] {
      override def execute(value: JsonNode,
                           path: String): Option[List[JsonNode]] =
        FieldsByPath[JsonNode](value, path)
    }

  // for parsed JsonObject form of Json

  implicit val stringJsonWriter: JsonWriter[String] = new JsonWriter[String] {
    override def write(value: String): JsonNode =
      (new ObjectMapper).readValue(value, classOf[JsonNode])
  }

  // syntax ifc

  implicit class NaviPathWriterOps[A: ClassTag](value: A) {

    def query[B: ClassTag](path: String)(
        implicit w: NaviPathQuery[A, B]): Option[B] =
      w.execute(value, path)

    def asJson(implicit w: JsonWriter[A]): JsonNode = w.write(value)

  }

}
