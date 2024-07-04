@main def hello(): Unit = {
  val graph = DirectedGraph[String, Int](Map.empty)
  val graphWithEdge = graph.addEdge("A", "B", 1)
  println(graphWithEdge.edges)
}
