abstract class Graph[V, G <: Graph[V, G]] (adjacencyList: Map[V, Map[V, Long]]) {
  self: G =>

  def getAllVertices: Set[V] = adjacencyList.keySet

  def getAllEdges: Set[(V, V, Long)] = 
    adjacencyList.flatMap { case (v, neighbors) => 
      neighbors.map { case (n, e) => (v, n, e) } 
    }.toSet

  def getNeighbors(vertex: V): Set[V] = 
    adjacencyList.getOrElse(vertex, Map.empty).keySet
  
  def addEdge(source: V, destination: V, edge: Long = 1): G = {
    val neighbors = adjacencyList.getOrElse(source, Map.empty)
    val updatedNeighbors = neighbors + (destination -> edge.abs)
    newGraph(adjacencyList + (source -> updatedNeighbors))
  }

  def removeEdge(source: V, destination: V): G = {
    val neighbors = adjacencyList.getOrElse(source, Map.empty)
    val updatedNeighbors = neighbors - destination
    newGraph(adjacencyList + (source -> updatedNeighbors))
  }

  protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): G
}