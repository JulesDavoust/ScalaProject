package mycore

case class DirectedGraph[V](adjacencyList: Map[V, Map[V, Long]]) 
  extends Graph[V, DirectedGraph[V]](adjacencyList)
  with GraphJsonSupport[V, DirectedGraph[V]]
  with GraphVizSupport[V, DirectedGraph[V]] {
  
  override def getAllEdges: Set[(V, V, Long)] = 
    adjacencyList.flatMap { case (v, neighbors) => 
      neighbors.map { case (n, e) => (v, n, e) } 
    }.toSet

  override def addEdge(source: V, destination: V, edge: Long = 1): DirectedGraph[V] = {
    val updatedNeighbors = adjacencyList.getOrElse(source, Map.empty) + (destination -> edge)
    DirectedGraph(adjacencyList + (source -> updatedNeighbors))
  }

  override def removeEdge(source: V, destination: V): DirectedGraph[V] = {
    val updatedNeighbors = adjacencyList.getOrElse(source, Map.empty) - destination
    DirectedGraph(adjacencyList + (source -> updatedNeighbors))
  }

  override protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): DirectedGraph[V] = DirectedGraph(adjacencyList)
}
