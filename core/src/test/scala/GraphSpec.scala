import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GraphSpec extends AnyFlatSpec with Matchers {
  "A DirectedGraph" should "add and remove edges correctly" in {
    val graph = DirectedGraph[String](Map.empty)
    val graphWithEdge = graph.addEdge("A", "B", 1)
    graphWithEdge.getAllEdges should contain (("A", "B", 1))
    val graphWithoutEdge = graphWithEdge.removeEdge("A", "B")
    graphWithoutEdge.getAllEdges should not contain (("A", "B", 1))
  }

  "An UndirectedGraph" should "add and remove edges correctly" in {
    val graph = UndirectedGraph[String](Map.empty)
    val graphWithEdge = graph.addEdge("A", "B", 1)
    graphWithEdge.getNeighbors("A") should contain ("B")
    graphWithEdge.getNeighbors("B") should contain ("A")
    val edgesAllowed = graphWithEdge.getAllEdges.intersect(Set(("A", "B", 1), ("B", "A", 1)))
    edgesAllowed.size.shouldBe(1)
    val graphWithoutEdge = graphWithEdge.removeEdge("A", "B")
    graphWithoutEdge.getAllEdges should not contain allOf(("A", "B", 1), ("B", "A", 1))
  }
}
