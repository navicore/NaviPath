package navicore.data.navipath.dsl

import java.io.InputStream

import com.fasterxml.jackson.databind.JsonNode
import navicore.data.navipath.dsl.NaviPathSyntax._
import org.scalatest.flatspec.AnyFlatSpec

import scala.io.Source

class NeoDataSpec extends AnyFlatSpec {

  val stream : InputStream = getClass.getResourceAsStream("/near_earth_objects.json")
  val jsonString: String = Source.fromInputStream(stream).mkString

  /**
   * a better example of getting a single array of child nodes from multiple parents that are all peers in the tree
   * note the .*[*] is a more correct syntax than the .*.* that works in other implementations but not gatlin
   */
  "An obj" should "handle list objects query" in {

    val results: Option[List[JsonNode]] = jsonString.query[List[JsonNode]]("$.near_earth_objects.*[*]")

    assert(results.nonEmpty)
    assert(results.get.size == 24)

    val min1 = results.get.head.query[Double]("$.estimated_diameter.meters.estimated_diameter_min").get
    val max1 = results.get.head.query[Double]("$.estimated_diameter.meters.estimated_diameter_max").get
    assert(min1 == 36.6906137531)
    assert(max1 == 82.0427064882)

    val min2 = results.get.last.query[Double]("$.estimated_diameter.meters.estimated_diameter_min").get
    val max2 = results.get.last.query[Double]("$.estimated_diameter.meters.estimated_diameter_max").get
    assert(min2 == 34.2574455887)
    assert(max2 == 76.6019770718)

  }

}
