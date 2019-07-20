package navicore.data.navipath.cli

import navicore.data.navipath.dsl.NaviPathSyntax._
import org.rogach.scallop.{ScallopConf, ScallopOption}

object Main extends App {

  class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val jsonl: ScallopOption[Boolean] = opt[Boolean](
      required = false,
      descr = "format as jsonl (json record per line)")
    val string: ScallopOption[Boolean] =
      opt[Boolean](required = false, descr = "query is for string type")
    val int: ScallopOption[Boolean] =
      opt[Boolean](required = false, descr = "query is for integer type")
    val double: ScallopOption[Boolean] =
      opt[Boolean](required = false, descr = "query is for double type")
    val path: ScallopOption[String] =
      trailArg[String](required = true, descr = "jsonpath")
    verify()
  }

  val conf = new Conf(args)

  if (conf.jsonl()) {

    Iterator
      .continually(scala.io.StdIn.readLine())
      .takeWhile(_ != null)
      .foreach(l => {
        try {
          val r = l.query[String](conf.path.getOrElse("."))
          r match {
            case Some(text) => println(text)
            case _          =>
          }
        } catch {
          case e: Throwable =>
            System.err.println(s"failed $e ${e.getClass} on json: $l")
        }
      })

  } else {
    val lines = Iterator
      .continually(scala.io.StdIn.readLine())
      .takeWhile(_ != null)
      .mkString
    try {
      val r = lines.query[String](conf.path.getOrElse("."))
      r match {
        case Some(text) => println(text)
        case _          =>
      }
    } catch {
      case e: Throwable =>
        System.err.println(s"failed $e ${e.getClass} on json")
    }
  }

}
