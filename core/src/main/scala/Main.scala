@main def hello(): Unit = {
  val graph = DirectedGraph[String](Map.empty)
  val graphWithEdge = graph.addEdge("A", "B", 10)
  println(graphWithEdge.getAllEdges)
}
