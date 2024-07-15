abstract class Graph[V, G <: Graph[V, G]] (adjacencyList: Map[V, Map[V, Long]]) {
  self: G =>

  def getAllVertices: Set[V] = adjacencyList.keySet

  def getAllEdges: Set[(V, V, Long)]

  def getNeighbors(vertex: V): Set[V] = adjacencyList.getOrElse(vertex, Map.empty).keySet
  
  def addEdge(source: V, destination: V, edge: Long = 1): G

  def removeEdge(source: V, destination: V): G

  protected def newGraph(adjacencyList: Map[V, Map[V, Long]]): G

  // ALGORITHMS :
  def depthFirstSearch(start: V): List[V] = {
    def dfsRecursive(node: V, visited: Set[V], result: List[V]): (Set[V], List[V]) = {
      if (visited.contains(node)) (visited, result)
      else {
        val neighbors = this.getNeighbors(node)
        val (newVisited, newResult) = neighbors.foldLeft((visited + node, node :: result)) {
          case ((vis, res), neighbor) => dfsRecursive(neighbor, vis, res)
        }
        (newVisited, newResult)
      }
    }

    val (_, result) = dfsRecursive(start, Set(), Nil)
    result.reverse
  }

  def breadthFirstSearch(start: V): List[V] = {
    def bfs(queue: List[V], visited: Set[V], result: List[V]): List[V] = {
      queue match {
        case Nil => result.reverse
        case node :: rest =>
          if (visited.contains(node)) {
            bfs(rest, visited, result)
          } else {
            val neighbors = this.getNeighbors(node)
            bfs(rest ++ neighbors, visited + node, node :: result)
          }
      }
    }

    bfs(List(start), Set(), Nil)
  }
}