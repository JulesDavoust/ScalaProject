package mycore

@main def undirectedGraphVizExample(): Unit = {
  val graph = DirectedGraph[String](Map.empty)
  val graphWithEdge = UndirectedGraph[String](Map("E" -> Map("B" -> 1), "F" -> Map("D" -> 1),
                                                        "A" -> Map("B" -> 1, "C" -> 1),
                                                        "B" -> Map("A" -> 1, "C" -> 10, "E" -> 1),
                                                        "C" -> Map("A" -> 1, "B" -> 10, "D" -> 1),
                                                        "D" -> Map("C" -> 1, "F" -> 1)))
  
  println(GraphAlgorithms.floydWarshall(graphWithEdge).get("A"))
}
