package mycore

abstract class Graph[V, G <: Graph[V, G]](adjacencyList: Map[V, Map[V, Long]]) {
  self: G =>

  def getAllVertices: Set[V] = 
    adjacencyList.keySet ++ adjacencyList.values.flatMap(_.keySet)
    
  def getAdjacencyList: Map[V, Map[V, Long]] = adjacencyList

  def getAllEdges: Set[(V, V, Long)]

  def getNeighbors(vertex: V): Set[V] = adjacencyList.getOrElse(vertex, Map.empty).keySet

  def getEdgesFrom(vertex: V): Map[V, Long] = adjacencyList.getOrElse(vertex, Map.empty)
  
  def addEdge(source: V, destination: V, edge: Long = 1): G

  def removeEdge(source: V, destination: V): G

  protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): G
}
