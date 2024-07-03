import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class DirectedGraphSpec extends AnyFlatSpec with Matchers {

  "A DirectedGraph" should "start empty" in {
    val graph = new DirectedGraph[Int]()
    graph.getAllVertices shouldBe empty
    graph.getAllEdges shouldBe empty
  }

  it should "allow adding edges and vertices" in {
    val graph = new DirectedGraph[Int]()
    graph.addEdge(1, 2)
    graph.getAllVertices should contain allOf (1, 2)
    graph.getAllEdges should contain (1 -> 2)
  }

  it should "allow removing edges" in {
    val graph = new DirectedGraph[Int]()
    graph.addEdge(1, 2).addEdge(1, 3)
    graph.removeEdge(1, 2)
    graph.getAllEdges should not contain (1 -> 2)
    graph.getAllEdges should contain (1 -> 3)
  }

  it should "return correct neighbors for a given vertex" in {
    val graph = new DirectedGraph[Int]()
    graph.addEdge(1, 2).addEdge(1, 3).addEdge(2, 4)
    graph.getNeighbors(1) should contain allOf (2, 3)
    graph.getNeighbors(2) should contain only 4
  }

  it should "return empty neighbors for a vertex with no outgoing edges" in {
    val graph = new DirectedGraph[Int]()
    graph.addEdge(1, 2)
    graph.getNeighbors(2) shouldBe empty
  }
}