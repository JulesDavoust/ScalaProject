package myapp

import zio._
import zio.nio.file.{Files, Path}
import mycore._
import zio.Console._
import java.nio.charset.StandardCharsets
import java.io.IOException

case class GraphService(ref: Ref[Map[String, DirectedGraph[String]]]) {

  def addGraph(name: String): UIO[Unit] =
    ref.update(_ + (name -> DirectedGraph(Map.empty[String, Map[String, Long]])))

  def addEdge(graphName: String, source: String, destination: String, weight: Long): IO[String, Unit] = 
    ref.modify { graphs =>
      graphs.get(graphName) match {
        case Some(graph) =>
          val updatedGraph = graph.addEdge(source, destination, weight)
          (Right(()), graphs.updated(graphName, updatedGraph))
        case None => 
          (Left(s"Graph $graphName not found"), graphs)
      }
    }.absolve

  def displayGraph(graphName: String): IO[String, Unit] = for {
    graphs <- ref.get
    graph <- ZIO.fromOption(graphs.get(graphName)).orElseFail(s"Graph $graphName not found")
    _ <- Console.printLine(s"Graph $graphName: ${graph.getAdjacencyList}").orDie
  } yield ()

  def saveGraph(graphName: String): IO[String, String] = for {
    graphs <- ref.get
    graph <- ZIO.fromOption(graphs.get(graphName)).orElseFail(s"Graph $graphName not found")
  } yield graph.toJson

  def loadGraph(graphName: String, json: String): IO[String, Unit] = for {
    currentGraph <- ref.get.map(_.getOrElse(graphName, DirectedGraph(Map.empty[String, Map[String, Long]])))
    newGraph <- ZIO.fromEither(currentGraph.fromJson(json))
    _ <- ref.update(_ + (graphName -> newGraph))
  } yield ()

  def saveGraphToFile(graphName: String, filePath: String): IO[IOException, Unit] = for {
    json <- saveGraph(graphName).mapError(e => new IOException(e))
    path = Path(filePath)
    _ <- Files.writeBytes(path, Chunk.fromArray(json.getBytes(StandardCharsets.UTF_8))).mapError(e => new IOException(e.toString))
  } yield ()

  def loadGraphFromFile(graphName: String, filePath: String): IO[IOException, Unit] = for {
    path <- ZIO.succeed(Path(filePath))
    bytes <- Files.readAllBytes(path).mapError(e => new IOException(e.toString))
    json = new String(bytes.toArray, StandardCharsets.UTF_8)
    _ <- loadGraph(graphName, json).mapError(e => new IOException(e))
  } yield ()

  def dfs(graphName: String, start: String): IO[String, List[String]] = for {
    graphs <- ref.get
    graph <- ZIO.fromOption(graphs.get(graphName)).orElseFail(s"Graph $graphName not found")
  } yield graph.dfs(start)

  def bfs(graphName: String, start: String): IO[String, List[String]] = for {
    graphs <- ref.get
    graph <- ZIO.fromOption(graphs.get(graphName)).orElseFail(s"Graph $graphName not found")
  } yield graph.bfs(start)

  def topologicalSort(graphName: String): IO[String, List[String]] = for {
    graphs <- ref.get
    graph <- ZIO.fromOption(graphs.get(graphName)).orElseFail(s"Graph $graphName not found")
    sorted <- ZIO.fromEither(graph.topologicalSort)
  } yield sorted

  def hasCycle(graphName: String): IO[String, Boolean] = for {
    graphs <- ref.get
    graph <- ZIO.fromOption(graphs.get(graphName)).orElseFail(s"Graph $graphName not found")
  } yield graph.hasCycle

  def floydWarshall(graphName: String): IO[String, Map[(String, String), Long]] = for {
    graphs <- ref.get
    graph <- ZIO.fromOption(graphs.get(graphName)).orElseFail(s"Graph $graphName not found")
  } yield graph.floydWarshall

  def dijkstra(graphName: String, start: String): IO[String, Map[String, Long]] = for {
    graphs <- ref.get
    graph <- ZIO.fromOption(graphs.get(graphName)).orElseFail(s"Graph $graphName not found")
  } yield graph.dijkstra(start)
}


object GraphService {
  def create: UIO[GraphService] =
    Ref.make(Map.empty[String, DirectedGraph[String]]).map(GraphService(_))
}
