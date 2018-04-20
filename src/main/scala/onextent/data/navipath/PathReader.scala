package onextent.data.navipath

trait PathReader {

  def /(jsonString: String): Option[String]

}

object PathReaderInstances {

  implicit val stringReader: PathReader =
    new PathReader {
      def /(jsonString: String): Option[String] =
        FieldByPath(jsonString, "")
    }

}
