abstract class Graph[V, G <: Graph[V, G]] (adjacencyList: Map[V, Map[V, Long]]) {
  self: G =>

  def getAllVertices: Set[V] = adjacencyList.keySet

  def getAllEdges: Set[(V, V, Long)]

  def getNeighbors(vertex: V): Set[V] = adjacencyList.getOrElse(vertex, Map.empty).keySet
  
  def addEdge(source: V, destination: V, edge: Long = 1): G

  def removeEdge(source: V, destination: V): G

  // ALGORITHMS :
  def depthFirstSearch(start: V): List[V] = {
    val (_, result) = dfsRecursive(start, Set(), Nil)
    result.reverse
  }

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

  def topologicalSort: List[V] = {
    val allNodes = getAllVertices
    val (_, sortedStack) = allNodes.foldLeft((Set.empty[V], List.empty[V])) {
      case ((visited, stack), node) =>
        val (newVisited, newStack) = dfsRecursive(node, visited, stack)
        (newVisited, newStack)
    }
    sortedStack.reverse
  }

  // TODO - Cycle Detection

  def floydWarshall: Map[V, Map[V, Long]] = {
    val vertices = getAllVertices

    // Initialize distance matrix
    val initialDistances: Map[V, Map[V, Long]] = vertices.foldLeft(Map.empty[V, Map[V, Long]]) { (acc, v) =>
      val row = vertices.foldLeft(Map.empty[V, Long]) { (rowAcc, w) =>
        if (v == w) rowAcc + (w -> 0L)
        else adjacencyList(v).get(w).map(weight => rowAcc + (w -> weight)).getOrElse(rowAcc + (w -> Long.MaxValue))
      }
      acc + (v -> row)
    }

    // Perform Floyd-Warshall algorithm
    val updatedDistances = vertices.foldLeft(initialDistances) { (distances, k) =>
      vertices.foldLeft(distances) { (d, i) =>
        vertices.foldLeft(d) { (dd, j) =>
          val ik = dd(i)(k)
          val kj = dd(k)(j)
          val newDistance = if (ik == Long.MaxValue || kj == Long.MaxValue) Long.MaxValue else ik + kj
          if (newDistance < dd(i)(j))
            dd + (i -> (dd(i) + (j -> newDistance)))
          else
            dd
        }
      }
    }

    updatedDistances
  }

  // TODO - Dijkstra's Algorithm
}