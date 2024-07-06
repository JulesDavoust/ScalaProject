package mycore

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import zio.json._

class GraphJsonSpec extends AnyFlatSpec with Matchers {

  "A DirectedGraph" should "encode and decode to/from JSON correctly" in {
    val graph = DirectedGraph(Map("a" -> Map("b" -> 1), "b" -> Map("c" -> 2)))
    
    // Encode to JSON
    val json = graph.toJson
    println(json)
    json should include ("\"vertices\":[\"a\",\"b\",\"c\"]")
    json should include ("\"edges\":[[\"a\",\"b\",1],[\"b\",\"c\",2]]")
    
    // Decode from JSON
    val decodedGraph = graph.fromJson(json).getOrElse(fail("Failed to decode JSON"))
    println(decodedGraph.getAdjacencyList)
    println(decodedGraph.getAllEdges)
    println(decodedGraph.getAllVertices)
    decodedGraph shouldEqual graph
  }

  it should "handle empty graphs" in {
    val emptyGraph = DirectedGraph(Map.empty[String, Map[String, Long]])
    
    // Encode to JSON
    val json = emptyGraph.toJson
    println(json)
    json should include ("\"vertices\":[]")
    json should include ("\"edges\":[]")
    
    // Decode from JSON
    val decodedGraph = emptyGraph.fromJson(json).getOrElse(fail("Failed to decode JSON"))
    println(decodedGraph.getAdjacencyList)
    decodedGraph shouldEqual emptyGraph
  }
}
