import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import mycore._

class GraphVizSupportSpec extends AnyFlatSpec with Matchers {
  implicit val intOrdering: Ordering[Int] = Ordering.Int

  "GraphVizSupport" should "convert DirectedGraph to GraphViz format correctly" in {
    val graph = DirectedGraph(Map(1 -> Map(2 -> 1L, 3 -> 2L), 2 -> Map(3 -> 3L), 3 -> Map.empty[Int, Long]))
    val graphViz = graph.toGraphViz.trim.replaceAll("\\s+", " ")
    val expected = 
      """digraph G {
        |  "1" -> "2" [weight="1"];
        |  "1" -> "3" [weight="2"];
        |  "2" -> "3" [weight="3"];
        |}
        |""".stripMargin.trim.replaceAll("\\s+", " ")

    graphViz shouldBe expected
  }

  it should "convert UndirectedGraph to GraphViz format correctly" in {
    val graph = UndirectedGraph(Map(1 -> Map(2 -> 1L), 2 -> Map(1 -> 1L, 3 -> 2L), 3 -> Map(2 -> 2L)))
    val graphViz = graph.toGraphViz.trim.replaceAll("\\s+", " ")
    val expected = 
      """graph G {
        |  "1" -- "2" [weight="1"];
        |  "2" -- "1" [weight="1"];
        |  "2" -- "3" [weight="2"];
        |  "3" -- "2" [weight="2"];
        |}
        |""".stripMargin.trim.replaceAll("\\s+", " ")

    graphViz shouldBe expected
  }
}
