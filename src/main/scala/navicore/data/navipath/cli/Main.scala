package navicore.data.navipath.cli

import navicore.data.navipath.dsl.NaviPathSyntax._
import org.rogach.scallop.{ScallopConf, ScallopOption}

object Main extends App {

  class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val jsonl: ScallopOption[Boolean] = opt[Boolean](
      required = false,
      descr = "format as jsonl (json record per line)"
    )
    val paths: ScallopOption[List[String]] =
      trailArg[List[String]](
        required = true,
        descr =
          "jsonpaths space delimited - results will be comma delimited.  prefix path with d for double and i for int.  ie: d$.mypath.mydouble"
      )
    verify()
  }

  val conf = new Conf(args)
  val paths = conf.paths.getOrElse(List("."))
  val lastPath = paths.last

  if (conf.jsonl()) {

    Iterator
      .continually(scala.io.StdIn.readLine())
      .takeWhile(_ != null)
      .foreach(l => {
        try {
          val parsedJson = l.asJson
          paths.foreach(path => {
            val r = path.charAt(0) match {
              case 'i' =>
                parsedJson.query[Int](path.substring(1))
              case 'd' =>
                parsedJson.query[Double](path.substring(1))
              case _ =>
                parsedJson.query[String](path)
            }
            r match {
              case Some(text) if path != lastPath => print(s"$text,")
              case Some(text)                     => println(s"$text")
              case _ if path != lastPath          => print(s",")
              case _                              => println("")
            }
          })
        } catch {
          case e: Throwable =>
            System.err.println(
              s"failed - exception: [${e.getClass}] - description: $e - on input: $l"
            )
        }
        l
      })

  } else {

    val lines = Iterator
      .continually(scala.io.StdIn.readLine())
      .takeWhile(_ != null)
      .mkString
    try {
      paths.foreach(path => {
        val r = lines.query[String](path)
        r match {
          case Some(text) => println(text)
          case _          =>
        }
      })
    } catch {
      case e: Throwable =>
        System.err.println(s"failed $e ${e.getClass} on json")
    }
  }

}
