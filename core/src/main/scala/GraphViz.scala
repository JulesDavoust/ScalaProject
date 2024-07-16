package mycore

trait GraphVizSupport[V, G <: Graph[V, G]] {
  self: G =>

  def toGraphViz: String = {
    val graphType = self match {
      case _: DirectedGraph[_] => "digraph"
      case _: UndirectedGraph[_] => "graph"
    }
    val edgeOp = self match {
      case _: DirectedGraph[_] => " -> "
      case _: UndirectedGraph[_] => " -- "
    }

    val builder = new StringBuilder
    builder.append(s"$graphType G {\n")
    
    for ((source, neighbors) <- getAdjacencyList) {
      for ((dest, weight) <- neighbors) {
        builder.append(s"""  "$source"$edgeOp"$dest" [weight="$weight"];\n""")
      }
    }
    
    builder.append("}\n")
    builder.toString()
  }
}

