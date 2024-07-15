case class DirectedGraph[V](adjacencyList: Map[V, Map[V, Long]]) extends Graph[V, DirectedGraph[V]](adjacencyList) {
  override protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): DirectedGraph[V] = DirectedGraph(adjacencyList)
  
  override def getAllEdges: Set[(V, V, Long)] = 
    adjacencyList.flatMap { case (v, neighbors) => 
      neighbors.map { case (n, e) => (v, n, e) } 
    }.toSet

  override def addEdge(source: V, destination: V, edge: Long): DirectedGraph[V] = {
    val updatedNeighbors = adjacencyList.getOrElse(source, Map.empty) + (destination -> edge)
    newGraph(adjacencyList + (source -> updatedNeighbors))
  }

  override def removeEdge(source: V, destination: V): DirectedGraph[V] = {
    val updatedNeighbors = adjacencyList.getOrElse(source, Map.empty) - destination
    newGraph(adjacencyList + (source -> updatedNeighbors))
  }
}
