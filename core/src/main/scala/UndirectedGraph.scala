case class UndirectedGraph[V](adjacencyList: Map[V, Map[V, Long]]) extends Graph[V, UndirectedGraph[V]](adjacencyList) {
  override protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): UndirectedGraph[V] = UndirectedGraph(adjacencyList)

  override def getAllEdges: Set[(V, V, Long)] = 
    adjacencyList.flatMap { case (v, neighbors) => 
      neighbors.collect { case (dest, weight) if v.hashCode <= dest.hashCode => (v, dest, weight) }
    }.toSet

  override def addEdge(source: V, destination: V, edge: Long): UndirectedGraph[V] = {
    val updatedNeighborsSource = adjacencyList.getOrElse(source, Map.empty) + (destination -> edge)
    val updatedNeighborsDestination = adjacencyList.getOrElse(destination, Map.empty) + (source -> edge)
    newGraph(adjacencyList + (source -> updatedNeighborsSource) + (destination -> updatedNeighborsDestination))
  }

  override def removeEdge(source: V, destination: V): UndirectedGraph[V] = {
    val updatedNeighborsSource = adjacencyList.getOrElse(source, Map.empty) - destination
    val updatedNeighborsDestination = adjacencyList.getOrElse(destination, Map.empty) - source
    newGraph(adjacencyList + (source -> updatedNeighborsSource) + (destination -> updatedNeighborsDestination))
  }
}
