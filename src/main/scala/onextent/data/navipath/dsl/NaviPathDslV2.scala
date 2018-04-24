package onextent.data.navipath.dsl

import onextent.data.navipath.FieldByPath

trait NaviPathWriter[A, B] {
  def write(value: A, path: String): Option[B]
}

object NaviPath {
  def query[A, B](value: A, path: String)(implicit w: NaviPathWriter[A, B]): Option[B] =
    w.write(value, path)
}

object NaviPathSyntax {

  // obj ifc

  implicit val stringWriter: NaviPathWriter[String, String] =
    (value: String, path: String) => FieldByPath[String](value, path)

  implicit val intWriter: NaviPathWriter[String, Int] =
    (value: String, path: String) => FieldByPath[Int](value, path)

  // syntax ifc

  implicit class NaviPathWriterOps[A](value: A) {
    def query[B](path: String)(implicit w: NaviPathWriter[A, B]): Option[B] =
      w.write(value, path)
  }

}
