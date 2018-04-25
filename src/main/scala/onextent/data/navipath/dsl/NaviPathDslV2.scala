package onextent.data.navipath.dsl

import onextent.data.navipath.{FieldByPath, FieldsByPath}

trait NaviPathWriter[A, B] {
  def write(value: A, path: String): Option[B]
}

object NaviPath {
  def query[A, B](value: A, path: String)(
      implicit w: NaviPathWriter[A, B]): Option[B] =
    w.write(value, path)
}

object NaviPathSyntax {

  // obj ifc

  implicit val stringWriter: NaviPathWriter[String, String] =
    (value: String, path: String) => FieldByPath[String](value, path)

  implicit val intWriter: NaviPathWriter[String, Int] =
    (value: String, path: String) => FieldByPath[Int](value, path)

  implicit val longWriter: NaviPathWriter[String, Long] =
    (value: String, path: String) => FieldByPath[Long](value, path)

  implicit val stringListWriter: NaviPathWriter[String, List[String]] =
    (value: String, path: String) =>
      FieldsByPath[String](value, path) match {
        case l: List[String] if l.isEmpty => None
        case l: List[String]              => Some(l)
    } // ugh, don't wrap List with Option

  implicit val intListWriter: NaviPathWriter[String, List[Int]] =
    (value: String, path: String) =>
      FieldsByPath[Int](value, path) match {
        case l: List[Int] if l.isEmpty => None
        case l: List[Int]              => Some(l)
      } // ugh, don't wrap List with Option

  implicit val doubleListWriter: NaviPathWriter[String, List[Double]] =
    (value: String, path: String) =>
      FieldsByPath[Double](value, path) match {
        case l: List[Double] if l.isEmpty => None
        case l: List[Double]              => Some(l)
      } // ugh, don't wrap List with Option

  // syntax ifc

  implicit class NaviPathWriterOps[A](value: A) {

    def query[B](path: String)(implicit w: NaviPathWriter[A, B]): Option[B] =
      w.write(value, path)

  }

}
