package mycore

trait GraphVizSupport[V] {
  self: Graph[V, ?] =>

  def toGraphViz: String = {
    val graphType = if (this.isInstanceOf[DirectedGraph[?]]) "digraph" else "graph"
    val edgeOp = if (this.isInstanceOf[DirectedGraph[?]]) " -> " else " -- "
    
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
