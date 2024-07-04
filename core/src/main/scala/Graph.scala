trait Graph[V, E] {
  def vertices: Set[V]
  def edges: Set[(V, V, E)]
  def neighbors(vertex: V): Set[V]
  def addEdge(source: V, destination: V, edge: E): Graph[V, E]
  def removeEdge(source: V, destination: V): Graph[V, E]
}