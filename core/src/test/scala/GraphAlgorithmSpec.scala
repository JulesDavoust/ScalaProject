import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import mycore._

class GraphAlgorithmSpec extends AnyFlatSpec with Matchers {
  val graphTestUndirected = UndirectedGraph[String](Map("E" -> Map("B" -> 1), "F" -> Map("D" -> 1),
                                                        "A" -> Map("B" -> 1, "C" -> 1),
                                                        "B" -> Map("A" -> 1, "C" -> 10, "E" -> 1),
                                                        "C" -> Map("A" -> 1, "B" -> 10, "D" -> 1),
                                                        "D" -> Map("C" -> 1, "F" -> 1)))

  val graphTestDirected = DirectedGraph[String](Map("A" -> Map("B" -> 1, "C" -> 1), "B" -> Map("C" -> 10, "E" -> 1),
                                                    "C" -> Map("D" -> 1), "D" -> Map("F" -> 1)))

  "Directed graph modifications" should "add edge correctly" in {
    val graph = DirectedGraph[String](Map.empty)
    val graphWithEdge = graph.addEdge("A", "B", 1)

    graphWithEdge.getAllEdges should contain(("A", "B", 1))
    graphWithEdge.getAllEdges should not contain(("B", "A", 1))
    graphWithEdge.getNeighbors("A") should contain("B")
    graphWithEdge.getNeighbors("B") should not contain("A")
  }

  it should "remove edge correctly" in {
    val graph = DirectedGraph[String](Map.empty)
    val graphEdited = graph.addEdge("A", "B", 1).removeEdge("A", "B")

    graphEdited.getAllEdges should not contain(("A", "B", 1))
    graphEdited.getNeighbors("A") should not contain("B")
    graphEdited.getNeighbors("B") should not contain("A")
  }

  "Undirected graph modifications" should "add edge correctly" in {
    val graph = UndirectedGraph[String](Map.empty)
    val graphWithEdge = graph.addEdge("A", "B", 1)

    graphWithEdge.getAllEdges should contain(("A", "B", 1))
    graphWithEdge.getNeighbors("A") should contain("B")
    graphWithEdge.getNeighbors("B") should contain("A")
  }

  it should "remove edge correctly" in {
    val graph = UndirectedGraph[String](Map.empty)
    val graphEdited = graph.addEdge("A", "B", 1).removeEdge("A", "B")

    graphEdited.getAllEdges should not contain(("A", "B", 1))
    graphEdited.getNeighbors("A") should not contain("B")
    graphEdited.getNeighbors("B") should not contain("A")
  }

  "Graph's algorithms" should "perform depth first search correctly" in {
    GraphAlgorithms.depthFirstSearch(graphTestDirected, "A") shouldBe List("A", "B", "E", "C", "D", "F")
    GraphAlgorithms.depthFirstSearch(graphTestUndirected, "A") shouldBe List("A", "B", "E", "C", "D", "F")
  }

  it should "perform breadth first search correctly" in {
    GraphAlgorithms.breadthFirstSearch(graphTestDirected, "A") shouldBe List("A", "B", "C", "E", "D", "F")
    GraphAlgorithms.breadthFirstSearch(graphTestUndirected, "A") shouldBe List("A", "B", "C", "E", "D", "F")
  }

  it should "perform topological sort correctly" in {
    GraphAlgorithms.topologicalSort(graphTestDirected) shouldBe List("A", "B", "C", "D", "F", "E")
  }

  it should "perform cycle detection correctly" in {
    val directedWithCycle = graphTestDirected.removeEdge("A", "C").addEdge("C", "A")
    val undirectedWithouCycle = graphTestUndirected.removeEdge("B", "C")
    GraphAlgorithms.hasCycle(graphTestDirected) shouldBe false
    GraphAlgorithms.hasCycle(directedWithCycle) shouldBe true
    GraphAlgorithms.hasCycle(undirectedWithouCycle) shouldBe false
    GraphAlgorithms.hasCycle(graphTestUndirected) shouldBe true
  }

  it should "found the shortest path with Floyd algorithm" in {
    GraphAlgorithms.floydWarshall(graphTestDirected) shouldBe Map("E" -> Map("E" -> 0, "F" -> Long.MaxValue, "A" -> Long.MaxValue,
                                                                             "B" -> Long.MaxValue, "C" -> Long.MaxValue,
                                                                             "D" -> Long.MaxValue),
                                                                  "F" -> Map("E" -> Long.MaxValue, "F" -> 0, "A" -> Long.MaxValue,
                                                                             "B" -> Long.MaxValue, "C" -> Long.MaxValue,
                                                                             "D" -> Long.MaxValue),
                                                                  "A" -> Map("E" -> 2, "F" -> 3, "A" -> 0, "B" -> 1, "C" -> 1,
                                                                             "D" -> 2),
                                                                  "B" -> Map("E" -> 1, "F" -> 12, "A" -> Long.MaxValue, "B" -> 0,
                                                                             "C" -> 10, "D" -> 11),
                                                                  "C" -> Map("E" -> Long.MaxValue, "F" -> 2, "A" -> Long.MaxValue,
                                                                             "B" -> Long.MaxValue, "C" -> 0, "D" -> 1),
                                                                  "D" -> Map("E" -> Long.MaxValue, "F" -> 1, "A" -> Long.MaxValue,
                                                                             "B" -> Long.MaxValue, "C" -> Long.MaxValue, "D" -> 0))
    GraphAlgorithms.floydWarshall(graphTestUndirected) shouldBe Map("E" -> Map("E" -> 0, "F" -> 5, "A" -> 2, "B" -> 1, "C" -> 3,
                                                                               "D" -> 4),
                                                                    "F" -> Map("E" -> 5, "F" -> 0, "A" -> 3, "B" -> 4, "C" -> 2,
                                                                               "D" -> 1),
                                                                    "A" -> Map("E" -> 2, "F" -> 3, "A" -> 0, "B" -> 1, "C" -> 1,
                                                                               "D" -> 2),
                                                                    "B" -> Map("E" -> 1, "F" -> 4, "A" -> 1, "B" -> 0, "C" -> 2,
                                                                               "D" -> 3),
                                                                    "C" -> Map("E" -> 3, "F" -> 2, "A" -> 1, "B" -> 2, "C" -> 0,
                                                                               "D" -> 1),
                                                                    "D" -> Map("E" -> 4, "F" -> 1, "A" -> 2, "B" -> 3, "C" -> 1,
                                                                               "D" -> 0))
  }
  
  // Don't need to test for all the values again since already tested for Floyd's algorithm we can therfore just reuse its value
  it should "found the same shortest path with Dijkstra's algorithm and the Floyd's one" in {
    GraphAlgorithms.dijkstra(graphTestDirected, "A") shouldBe GraphAlgorithms.floydWarshall(graphTestDirected)
                                                                             .getOrElse("A", Map.empty)
    GraphAlgorithms.dijkstra(graphTestUndirected, "A") shouldBe GraphAlgorithms.floydWarshall(graphTestUndirected)
                                                                               .getOrElse("A", Map.empty)
  }
}
