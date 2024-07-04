import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GraphSpec extends AnyFlatSpec with Matchers {
  "A DirectedGraph" should "add and remove edges correctly" in {
    val graph = DirectedGraph[String, Int](Map.empty)
    val graphWithEdge = graph.addEdge("A", "B", 1)
    graphWithEdge.edges should contain (("A", "B", 1))
    val graphWithoutEdge = graphWithEdge.removeEdge("A", "B")
    graphWithoutEdge.edges should not contain (("A", "B", 1))
  }

//   "An UndirectedGraph" should "add and remove edges correctly" in {
//     val graph = UndirectedGraph[String, Int](Map.empty)
//     val graphWithEdge = graph.addEdge("A", "B", 1)
//     graphWithEdge.edges should contain allOf (("A", "B", 1), ("B", "A", 1))
//     val graphWithoutEdge = graphWithEdge.removeEdge("A", "B")
//     graphWithoutEdge.edges should not contain (("A", "B", 1))
//     graphWithoutEdge.edges should not contain (("B", "A", 1))
//   }

//   "A WeightedGraph" should "add and remove edges correctly" in {
//     val graph = WeightedGraph[String](Map.empty)
//     val graphWithEdge = graph.addEdge("A", "B", 1)
//     graphWithEdge.edges should contain (("A", "B", 1))
//     val graphWithoutEdge = graphWithEdge.removeEdge("A", "B")
//     graphWithoutEdge.edges should not contain (("A", "B", 1))
//   }
}
