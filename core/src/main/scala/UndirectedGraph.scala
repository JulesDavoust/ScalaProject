case class UndirectedGraph[V](adjacencyList: Map[V, Map[V, Long]]) extends Graph[V, UndirectedGraph[V]](adjacencyList) {
  override protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): UndirectedGraph[V] = UndirectedGraph(adjacencyList)

  override def addEdge(source: V, destination: V, edge: Long): UndirectedGraph[V] = {
    val neighborsSource = this.getNeighbors(source)
    val neighborsDestination = this.getNeighbors(destination)

    if (neighborsSource.contains(destination) || neighborsDestination.contains(source)) this
    else super.addEdge(source, destination, edge)
  }
}
