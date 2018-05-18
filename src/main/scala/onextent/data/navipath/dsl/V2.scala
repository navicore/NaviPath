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

  implicit val stringStringQuery: NaviPathQuery[String, String] = new NaviPathQuery[String, String] {
    override def execute(value: String, path: String): Option[String] = FieldByPath[String](value, path)
  }
  implicit val stringObjectQuery: NaviPathQuery[Object, String] = new NaviPathQuery[Object, String] {
    override def execute(value: Object, path: String): Option[String] = FieldByPath[String](value, path)
  }

  implicit val intStringQuery: NaviPathQuery[String, Int] = new NaviPathQuery[String, Int] {
    override def execute(value: String, path: String): Option[Int] = FieldByPath[Int](value, path)
  }

  implicit val intObjectQuery: NaviPathQuery[Object, Int] = new NaviPathQuery[Object, Int] {
    override def execute(value: Object, path: String): Option[Int] = FieldByPath[Int](value, path)
  }

  implicit val longStringQuery: NaviPathQuery[String, Long] = new NaviPathQuery[String, Long] {
    override def execute(value: String, path: String): Option[Long] = FieldByPath[Long](value, path)
  }

  implicit val longObjectQuery: NaviPathQuery[Object, Long] = new NaviPathQuery[Object, Long] {
    override def execute(value: Object, path: String): Option[Long] = FieldByPath[Long](value, path)
  }

  implicit val doubleStringQuery: NaviPathQuery[String, Double] = new NaviPathQuery[String, Double] {
    override def execute(value: String, path: String): Option[Double] = FieldByPath[Double](value, path)
  }

  implicit val doubleObjectQuery: NaviPathQuery[Object, Double] = new NaviPathQuery[Object, Double] {
    override def execute(value: Object, path: String): Option[Double] = FieldByPath[Double](value, path)
  }

  implicit val stringListStringQuery: NaviPathQuery[String, List[String]] = new NaviPathQuery[String, List[String]] {
    override def execute(value: String, path: String): Option[List[String]] = FieldsByPath[String](value, path)
  }

  implicit val stringListObjectQuery: NaviPathQuery[Object, List[String]] = new NaviPathQuery[Object, List[String]] {
    override def execute(value: Object, path: String): Option[List[String]] = FieldsByPath[String](value, path)
  }

  implicit val intListStringQuery: NaviPathQuery[String, List[Int]] = new NaviPathQuery[String, List[Int]] {
    override def execute(value: String, path: String): Option[List[Int]] = FieldsByPath[Int](value, path)
  }

  implicit val intListObjectQuery: NaviPathQuery[Object, List[Int]] = new NaviPathQuery[Object, List[Int]] {
    override def execute(value: Object, path: String): Option[List[Int]] = FieldsByPath[Int](value, path)
  }

  implicit val longListStringQuery: NaviPathQuery[String, List[Long]] = new NaviPathQuery[String, List[Long]] {
    override def execute(value: String, path: String): Option[List[Long]] = FieldsByPath[Long](value, path)
  }

  implicit val longListObjectQuery: NaviPathQuery[Object, List[Long]] = new NaviPathQuery[Object, List[Long]] {
    override def execute(value: Object, path: String): Option[List[Long]] = FieldsByPath[Long](value, path)
  }

  implicit val doubleListStringQuery: NaviPathQuery[String, List[Double]] = new NaviPathQuery[String, List[Double]] {
    override def execute(value: String, path: String): Option[List[Double]] = FieldsByPath[Double](value, path)
  }

  implicit val doubleListObjectQuery: NaviPathQuery[Object, List[Double]] = new NaviPathQuery[Object, List[Double]]  {
    override def execute(value: Object, path: String): Option[List[Double]] = FieldsByPath[Double](value, path)
  }

  // for parsed JsonObject form of Json

  implicit val stringJsonWriter: JsonWriter[String] = new JsonWriter[String] {
    override def write(value: String): AnyRef = (new ObjectMapper).readValue(value, classOf[Object])
  }

  // syntax ifc

  implicit class NaviPathWriterOps[A](value: A) {

    def query[B](path: String)(implicit w: NaviPathQuery[A, B]): Option[B] =
      w.execute(value, path)

    def asJson(implicit w: JsonWriter[A]): Object = w.write(value)

  }

}
