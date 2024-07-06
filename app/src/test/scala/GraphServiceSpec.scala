package myapp

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import zio._
import zio.nio.file.{Files, Path}
import java.nio.charset.StandardCharsets
import java.io.IOException

class GraphServiceSpec extends AnyFlatSpec with Matchers {

  "GraphService" should "add a graph successfully" in {
    val program = for {
      service <- GraphService.create
      _ <- service.addGraph("testGraph")
      graphs <- service.ref.get
    } yield graphs.contains("testGraph")

    val runtime = Runtime.default
    val result = Unsafe.unsafe { implicit u =>
      runtime.unsafe.run(program).getOrThrowFiberFailure()
    }
    result shouldBe true
  }

  it should "add an edge to an existing graph" in {
    val program = for {
      service <- GraphService.create
      _ <- service.addGraph("testGraph")
      _ <- service.addEdge("testGraph", "A", "B", 10)
      graphs <- service.ref.get
    } yield graphs("testGraph").getAdjacencyList

    val runtime = Runtime.default
    val result = Unsafe.unsafe { implicit u =>
      runtime.unsafe.run(program).getOrThrowFiberFailure()
    }
    result shouldBe Map("A" -> Map("B" -> 10))
  }

  it should "fail to add an edge to a non-existing graph" in {
    val program = for {
      service <- GraphService.create
      result <- service.addEdge("nonExistentGraph", "A", "B", 10).either
    } yield result

    val runtime = Runtime.default
    val result = Unsafe.unsafe { implicit u =>
      runtime.unsafe.run(program).getOrThrowFiberFailure()
    }
    result shouldBe Left("Graph nonExistentGraph not found")
  }

  it should "save and load a graph from a file" in {
    val tempFilePath = java.nio.file.Files.createTempFile("", ".json").toFile.getAbsolutePath

    val program = for {
      service <- GraphService.create
      _ <- service.addGraph("testGraph")
      _ <- service.addEdge("testGraph", "A", "B", 10)
      _ <- service.saveGraphToFile("testGraph", tempFilePath)
      _ <- service.loadGraphFromFile("loadedGraph", tempFilePath)
      graphs <- service.ref.get
    } yield graphs("loadedGraph").getAdjacencyList

    val runtime = Runtime.default
    val result = Unsafe.unsafe { implicit u =>
      runtime.unsafe.run(program).getOrThrowFiberFailure()
    }
    result shouldBe Map("A" -> Map("B" -> 10))
  }
}