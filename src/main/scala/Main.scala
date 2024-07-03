trait Graph[T] {
  def getAllVertices: Set[T]
  def getAllEdges: Set[(T, T)]
  def getNeighbors(vertex: T): Set[T]
  def addEdge(v1: T, v2: T): Graph[T]
  def removeEdge(v1: T, v2: T): Graph[T]
}

class DirectedGraph[T] extends Graph[T] {
  private var vertices: Set[T] = Set()
  private var edges: Map[T, Set[T]] = Map()

  override def getAllVertices: Set[T] = vertices

  override def getAllEdges: Set[(T, T)] = edges.flatMap { case (v, neighbors) =>
    neighbors.map(n => (v, n))
  }.toSet

  override def getNeighbors(vertex: T): Set[T] = edges.getOrElse(vertex, Set())

  override def addEdge(v1: T, v2: T): Graph[T] = {
    vertices += v1
    vertices += v2
    val updatedNeighbors = edges.getOrElse(v1, Set()) + v2
    edges += v1 -> updatedNeighbors
    this
  }

  override def removeEdge(v1: T, v2: T): Graph[T] = {
    edges.get(v1).foreach { neighbors =>
      edges += v1 -> (neighbors - v2)
    }
    this
  }
}

object Main extends App {
  val graph = new DirectedGraph[Int]()
  graph.addEdge(1, 2)
  graph.addEdge(2, 3)
  graph.addEdge(3, 1)
  graph.addEdge(1, 3)
  graph.addEdge(1, 1)
  graph.addEdge(2, 4)
  
  println(graph.getAllEdges)
}
