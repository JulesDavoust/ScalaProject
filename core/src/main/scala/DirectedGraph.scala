package mycore

case class DirectedGraph[V](adjacencyList: Map[V, Map[V, Long]]) extends Graph[V, DirectedGraph[V]](adjacencyList) with GraphJsonSupport[V] with GraphVizSupport[V] {
  override protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): DirectedGraph[V] = DirectedGraph(adjacencyList)
}