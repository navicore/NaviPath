package onextent.data.navipath


// under construction
// under construction
// under construction

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
