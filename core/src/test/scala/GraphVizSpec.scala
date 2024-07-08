// package mycore

// import org.scalatest.flatspec.AnyFlatSpec
// import org.scalatest.matchers.should.Matchers

// class GraphVizSupportSpec extends AnyFlatSpec with Matchers {

//   "A DirectedGraph" should "generate correct GraphViz representation" in {
//     val graph = DirectedGraph(Map(
//       "A" -> Map("B" -> 1, "C" -> 2),
//       "B" -> Map("C" -> 3),
//       "C" -> Map.empty[String, Long]
//     ))

//     val expectedGraphViz =
//       """digraph G {
//         |  "A" -> "B" [weight="1"];
//         |  "A" -> "C" [weight="2"];
//         |  "B" -> "C" [weight="3"];
//         |}
//         |""".stripMargin

//     val generatedGraphViz = graph.toGraphViz
//     println(s"Generated Directed GraphViz:\n$generatedGraphViz")
//     generatedGraphViz shouldEqual expectedGraphViz
//   }

// //   "An UndirectedGraph" should "generate correct GraphViz representation" in {
// //     val graph = UndirectedGraph(Map(
// //       "A" -> Map("B" -> 1, "C" -> 2),
// //       "B" -> Map("A" -> 1, "C" -> 3),
// //       "C" -> Map("A" -> 2, "B" -> 3)
// //     ))

//     // val expectedGraphViz =
//     //   """graph G {
//     //     |  "A" -- "B" [weight="1"];
//     //     |  "A" -- "C" [weight="2"];
//     //     |  "B" -- "A" [weight="1"];
//     //     |  "B" -- "C" [weight="3"];
//     //     |  "C" -- "A" [weight="2"];
//     //     |  "C" -- "B" [weight="3"];
//     //     |}
//     //     |""".stripMargin

//     // graph.toGraphViz shouldEqual expectedGraphViz
// //   }
// }
