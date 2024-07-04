case class DirectedGraph[V, E](adjacencyList: Map[V, Map[V, E]]) extends Graph[V, E] {
  def vertices: Set[V] = adjacencyList.keySet

  def edges: Set[(V, V, E)] = 
    adjacencyList.flatMap { case (v, neighbors) => 
      neighbors.map { case (n, e) => (v, n, e) } 
    }.toSet

  def neighbors(vertex: V): Set[V] = 
    adjacencyList.getOrElse(vertex, Map.empty).keySet

  def addEdge(source: V, destination: V, edge: E): DirectedGraph[V, E] = {
    val neighbors = adjacencyList.getOrElse(source, Map.empty)
    val updatedNeighbors = neighbors + (destination -> edge)
    DirectedGraph(adjacencyList + (source -> updatedNeighbors))
  }

  def removeEdge(source: V, destination: V): DirectedGraph[V, E] = {
    val neighbors = adjacencyList.getOrElse(source, Map.empty)
    val updatedNeighbors = neighbors - destination
    DirectedGraph(adjacencyList + (source -> updatedNeighbors))
  }
}

