case class DirectedGraph[V](adjacencyList: Map[V, Map[V, Long]]) extends Graph[V, DirectedGraph[V]](adjacencyList) {
  override protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): DirectedGraph[V] = DirectedGraph(adjacencyList)

  override def getAllEdges: Set[Tuple] =
    adjacencyList.flatMap { case (v, neighbors) => 
      neighbors.map { case (n, e) => (v, n) } 
    }.toSet

  override def addEdge(source: V, destination: V, edge: Long = 1): DirectedGraph[V] = super.addEdge(source, destination)
}
