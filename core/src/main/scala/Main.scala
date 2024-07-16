package mycore

@main def hello(): Unit = {
  val graph = UndirectedGraph[String](Map.empty)
  val graphWithEdge = graph.addEdge("A", "B", 1).addEdge("A", "C", 1).addEdge("B", "C", 10).addEdge("B", "E", 1).addEdge("C", "D", 1).addEdge("D", "F", 1).addEdge("F", "D", 1)
  println(graphWithEdge.getAllEdges)
  println(graphWithEdge.floydWarshall)
}
