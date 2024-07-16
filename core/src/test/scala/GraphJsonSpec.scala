import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import mycore._
import zio.json._

class GraphJsonSupportSpec extends AnyFlatSpec with Matchers {
  "GraphJsonSupport" should "serialize and deserialize DirectedGraph to and from JSON correctly" in {
    val graph = DirectedGraph(Map(1 -> Map(2 -> 1L, 3 -> 2L), 2 -> Map(3 -> 3L), 3 -> Map.empty[Int, Long]))
    val json = graph.toJson
    val decodedGraph = graph.fromJson(json)

    decodedGraph shouldBe Right(graph)
  }

  it should "serialize and deserialize UndirectedGraph to and from JSON correctly" in {
    val graph = UndirectedGraph(Map(1 -> Map(2 -> 1L), 2 -> Map(1 -> 1L, 3 -> 2L), 3 -> Map(2 -> 2L)))
    val json = graph.toJson
    val decodedGraph = graph.fromJson(json)

    decodedGraph shouldBe Right(graph)
  }
}
