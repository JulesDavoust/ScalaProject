package mycore

import scala.collection.immutable.SortedMap
import scala.collection.immutable.SortedSet
import scala.annotation.tailrec

object GraphAlgorithms {
  def depthFirstSearch[V, G <: Graph[V, G]](graph: G, start: V): List[V] = {
    val (_, result) = dfsRecursive(graph, start, Set(), Nil)
    result
  }

  private def dfsRecursive[V, G <: Graph[V, G]](graph: G, node: V, visited: Set[V], result: List[V]): (Set[V], List[V]) = {
    if (visited.contains(node)) (visited, result)
    else {
      val neighbors = graph.getNeighbors(node)
      val (newVisited, newResult) = neighbors.foldLeft((visited + node, result)) {
        case ((vis, res), neighbor) => dfsRecursive(graph, neighbor, vis, res)
      }
      (newVisited, node :: newResult)
    }
  }

  def breadthFirstSearch[V, G <: Graph[V, G]](graph: G, start: V): List[V] = {
    @tailrec
    def bfs(queue: List[V], visited: Set[V], result: List[V]): List[V] = {
      queue match {
        case Nil => result.reverse
        case node :: rest =>
          if (visited.contains(node)) {
            bfs(rest, visited, result)
          } else {
            val neighbors = graph.getNeighbors(node)
            bfs(rest ++ neighbors, visited + node, node :: result)
          }
      }
    }

    bfs(List(start), Set(), Nil)
  }

  def topologicalSort[V, G <: Graph[V, G]] (graph: G): List[V] = {
    val allNodes = graph.getAllVertices
    val (_, sortedStack) = allNodes.foldLeft((Set.empty[V], List.empty[V])) {
      case ((visited, stack), node) =>
        val (newVisited, newStack) = dfsRecursive(graph, node, visited, stack)
        (newVisited, newStack)
    }
    sortedStack
  }

  def hasCycle[V, G <: Graph[V, G]] (graph: G): Boolean = {
    def dfs(node: V, visited: Set[V], parent: Option[V]): Boolean = {
      if (visited.contains(node)) true
      else {
        val newVisited = visited + node
        graph.getNeighbors(node).exists { neighbor =>
          if (Some(neighbor) != parent) dfs(neighbor, newVisited, Some(node))
          else false
        }
      }
    }

    graph.getAllVertices.exists(node => dfs(node, Set.empty, None))
  }

  def floydWarshall[V, G <: Graph[V, G]] (graph: G): Map[V, Map[V, Long]] = {
    val vertices = graph.getAllVertices

    val initialDistances: Map[V, Map[V, Long]] = vertices.foldLeft(Map.empty[V, Map[V, Long]]) { (acc, v) =>
      val row = vertices.foldLeft(Map.empty[V, Long]) { (rowAcc, w) =>
        if (v == w) rowAcc + (w -> 0L)
        else graph.getEdgesFrom(v).get(w).map(weight => rowAcc + (w -> weight)).getOrElse(rowAcc + (w -> Long.MaxValue))
      }
      acc + (v -> row)
    }

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

  def dijkstra[V, G <: Graph[V, G]] (graph: G, start: V): Map[V, Long] = {
    val initialDistances: Map[V, Long] = graph.getAllVertices.map(_ -> Long.MaxValue).toMap + (start -> 0L)
    val initialQueue: SortedMap[Long, Set[V]] = SortedMap(0L -> Set(start))
    
    @tailrec
    def loop(distances: Map[V, Long], queue: SortedMap[Long, Set[V]]): Map[V, Long] = {
      if (queue.isEmpty) distances
      else {
        val (currentDist, nodes) = queue.head
        val current = nodes.head
        val remainingNodes = nodes - current
        val updatedQueue = if (remainingNodes.isEmpty) queue.tail else queue.updated(currentDist, remainingNodes)
        
        val (newDistances, newQueue) = graph.getEdgesFrom(current).foldLeft((distances, updatedQueue)) {
          case ((distAcc, queueAcc), (neighbor, weight)) =>
            val newDist = currentDist + weight
            if (newDist < distAcc(neighbor)) {
              val updatedDistances = distAcc.updated(neighbor, newDist)
              val updatedQueueAcc = queueAcc.get(newDist) match {
                case Some(nodes) => queueAcc.updated(newDist, nodes + neighbor)
                case None => queueAcc + (newDist -> Set(neighbor))
              }
              (updatedDistances, updatedQueueAcc)
            } else {
              (distAcc, queueAcc)
            }
        }
        
        loop(newDistances, newQueue)
      }
    }
    
    loop(initialDistances, initialQueue)
  }
}
