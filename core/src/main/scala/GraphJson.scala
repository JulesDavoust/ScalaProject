package mycore

import zio.json._

case class GraphJson[V](vertices: Set[V], edges: Set[(V, V, Long)])

object GraphJson {
  implicit def encoder[V: JsonEncoder]: JsonEncoder[GraphJson[V]] = DeriveJsonEncoder.gen[GraphJson[V]]
  implicit def decoder[V: JsonDecoder]: JsonDecoder[GraphJson[V]] = DeriveJsonDecoder.gen[GraphJson[V]]
}

trait GraphJsonSupport[V] {
  self: Graph[V, ?] =>

  def toJson(implicit encoder: JsonEncoder[V]): String = {
    val graphJson = GraphJson(getAllVertices, getAllEdges)
    graphJson.toJson
  }

  def fromJson(json: String)(implicit decoder: JsonDecoder[V]): Either[String, self.type] = {
    json.fromJson[GraphJson[V]].map { graphJson =>
      val adjacencyList = graphJson.edges.groupBy(_._1).map { case (v, edges) =>
        v -> edges.map(e => e._2 -> e._3).toMap
      }
      newGraph(adjacencyList).asInstanceOf[self.type]
    }
  }
}
