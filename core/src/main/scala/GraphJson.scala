package mycore

import zio.json._

case class GraphJson[V](vertices: Set[V], edges: Set[(V, V, Long)])

object GraphJson {
  implicit def encoder[V: JsonEncoder]: JsonEncoder[GraphJson[V]] = DeriveJsonEncoder.gen[GraphJson[V]]
  implicit def decoder[V: JsonDecoder]: JsonDecoder[GraphJson[V]] = DeriveJsonDecoder.gen[GraphJson[V]]
}

trait GraphJsonSupport[V, G <: Graph[V, G]] {
  self: G =>

  implicit val isDirected: Boolean

  def toJson(implicit encoder: JsonEncoder[V]): String = {
    val graphJson = GraphJson(getAllVertices, getAllEdges)
    graphJson.toJson
  }

  def fromJson(json: String)(implicit decoder: JsonDecoder[V]): Either[String, G] = {
    json.fromJson[GraphJson[V]].map { graphJson =>
      val initialAdjacencyList = graphJson.vertices.map(v => v -> Map.empty[V, Long]).toMap
      val adjacencyList = graphJson.edges.foldLeft(initialAdjacencyList) { case (acc, (source, dest, weight)) =>
        val updatedSourceNeighbors = acc(source) + (dest -> weight)
        val updatedAdjacencyList = acc + (source -> updatedSourceNeighbors)
        if (!isDirected) {
          val updatedDestNeighbors = updatedAdjacencyList(dest) + (source -> weight)
          updatedAdjacencyList + (dest -> updatedDestNeighbors)
        } else {
          updatedAdjacencyList
        }
      }
      newGraph(adjacencyList)
    }
  }
}
