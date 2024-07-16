package mycore

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import zio.json._

class GraphJsonSpec extends AnyFlatSpec with Matchers {

  "A DirectedGraph" should "serialize to JSON and deserialize from JSON correctly" in {
    val graph = DirectedGraph(Map(
      "A" -> Map("B" -> 1, "C" -> 2),
      "B" -> Map("C" -> 3),
      "C" -> Map.empty[String, Long]
    ))

    val json = graph.toJson
    val deserializedGraph = graph.fromJson(json)

    deserializedGraph shouldEqual Right(graph)
  }

  "An UndirectedGraph" should "serialize to JSON and deserialize from JSON correctly" in {
    val graph = UndirectedGraph(Map(
      "A" -> Map("B" -> 1, "C" -> 2),
      "B" -> Map("A" -> 1, "C" -> 3),
      "C" -> Map("A" -> 2, "B" -> 3)
    ))

    val json = graph.toJson
    val deserializedGraph = graph.fromJson(json)

    deserializedGraph shouldEqual Right(graph)
  }
}
