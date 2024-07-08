package mycore

@main def hello(): Unit = {
  val graph = DirectedGraph[String](Map.empty)
  val graphWithEdge = graph.addEdge("A", "B", 10).addEdge("B", "A", 10).addEdge("A", "B", 10)
  println(graphWithEdge.getAllEdges)

  val graphVizRepresentation = graphWithEdge.toGraphViz
  println(s"Directed GraphViz representation:\n$graphVizRepresentation")
}
