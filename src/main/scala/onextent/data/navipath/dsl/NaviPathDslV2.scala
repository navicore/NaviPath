package onextent.data.navipath.dsl

import com.fasterxml.jackson.databind.ObjectMapper
import onextent.data.navipath.{FieldByPath, FieldsByPath}

trait JsonWriter[A] {
  def write(value: A): Object
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
    (value: String, path: String) => FieldByPath[String](value, path)
  implicit val stringObjectQuery: NaviPathQuery[Object, String] =
    (value: Object, path: String) => FieldByPath[String](value, path)

  implicit val intStringQuery: NaviPathQuery[String, Int] =
    (value: String, path: String) => FieldByPath[Int](value, path)
  implicit val intObjectQuery: NaviPathQuery[Object, Int] =
    (value: Object, path: String) => FieldByPath[Int](value, path)

  implicit val longStringQuery: NaviPathQuery[String, Long] =
    (value: String, path: String) => FieldByPath[Long](value, path)
  implicit val longObjectQuery: NaviPathQuery[Object, Long] =
    (value: Object, path: String) => FieldByPath[Long](value, path)

  implicit val doubleStringQuery: NaviPathQuery[String, Double] =
    (value: String, path: String) => FieldByPath[Double](value, path)
  implicit val doubleObjectQuery: NaviPathQuery[Object, Double] =
    (value: Object, path: String) => FieldByPath[Double](value, path)

  implicit val stringListStringQuery: NaviPathQuery[String, List[String]] =
    (value: String, path: String) =>
      FieldsByPath[String](value, path)
  implicit val stringListObjectQuery: NaviPathQuery[Object, List[String]] =
    (value: Object, path: String) =>
      FieldsByPath[String](value, path)

  implicit val intListStringQuery: NaviPathQuery[String, List[Int]] =
    (value: String, path: String) =>
      FieldsByPath[Int](value, path)
  implicit val intListObjectQuery: NaviPathQuery[Object, List[Int]] =
    (value: Object, path: String) =>
      FieldsByPath[Int](value, path)

  implicit val longListStringQuery: NaviPathQuery[String, List[Long]] =
    (value: String, path: String) =>
      FieldsByPath[Long](value, path)
  implicit val longListObjectQuery: NaviPathQuery[Object, List[Long]] =
    (value: Object, path: String) =>
      FieldsByPath[Long](value, path)

  implicit val doubleListStringQuery: NaviPathQuery[String, List[Double]] =
    (value: String, path: String) =>
      FieldsByPath[Double](value, path)
  implicit val doubleListObjectQuery: NaviPathQuery[Object, List[Double]] =
    (value: Object, path: String) =>
      FieldsByPath[Double](value, path)

  // for parsed JsonObject form of Json

  implicit val stringJsonWriter: JsonWriter[String] =
    (data: String) => (new ObjectMapper).readValue(data, classOf[Object])

  // syntax ifc

  implicit class NaviPathWriterOps[A](value: A) {

    def query[B](path: String)(implicit w: NaviPathQuery[A, B]): Option[B] =
      w.execute(value, path)

    def asJson(implicit w: JsonWriter[A]): Object = w.write(value)

  }

}
