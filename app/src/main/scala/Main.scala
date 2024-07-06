package myapp

import zio._
import mycore._
import zio.Console._
import java.io.IOException

object Main extends ZIOAppDefault {

  def menu: ZIO[Any, IOException, Unit] = for {
    _ <- Console.printLine("\n1. Add Graph")
    _ <- Console.printLine("2. Add Edge")
    _ <- Console.printLine("3. Display Graph")
    _ <- Console.printLine("4. Save Graph to File")
    _ <- Console.printLine("5. Load Graph from File")
    _ <- Console.printLine("6. Exit")
  } yield ()

  def getInput(prompt: String): ZIO[Any, IOException, String] = for {
    _ <- Console.printLine(prompt)
    input <- Console.readLine
  } yield input

  def program(graphService: GraphService): IO[IOException, Unit] = for {
    _ <- menu
    choice <- getInput("\nEnter choice: ")
    _ <- choice match {
      case "1" =>
        for {
          name <- getInput("Enter graph name: ")
          _ <- graphService.addGraph(name)
          _ <- Console.printLine(s"Graph $name added.")
        } yield ()
      case "2" =>
        for {
          name <- getInput("Enter graph name: ")
          source <- getInput("Enter source vertex: ")
          destination <- getInput("Enter destination vertex: ")
          weight <- getInput("Enter weight: ").map(_.toLong)
          _ <- graphService.addEdge(name, source, destination, weight).catchAll(e => Console.printLine(e))
        } yield ()
      case "3" =>
        for {
          name <- getInput("Enter graph name: ")
          _ <- graphService.displayGraph(name).catchAll(e => Console.printLine(e))
        } yield ()
      case "4" =>
        for {
          name <- getInput("Enter graph name: ")
          filePath <- getInput("Enter file path: ")
          _ <- graphService.saveGraphToFile(name, filePath).catchAll(e => Console.printLine(e))
        } yield ()
      case "5" =>
        for {
          name <- getInput("Enter graph name: ")
          filePath <- getInput("Enter file path: ")
          _ <- graphService.loadGraphFromFile(name, filePath).catchAll(e => Console.printLine(e))
        } yield ()
      case "6" => ZIO.unit
      case _ => Console.printLine("Invalid choice!")
    }
    _ <- if (choice != "6") program(graphService) else ZIO.unit
  } yield ()

  def initialLoad(graphService: GraphService): IO[IOException, Unit] = for {
    choice <- getInput("Do you want to load a graph from a file? (yes/no): ")
    _ <- if (choice.toLowerCase == "yes") {
      for {
        name <- getInput("Enter graph name: ")
        filePath <- getInput("Enter file path: ")
        _ <- graphService.loadGraphFromFile(name, filePath).catchAll(e => Console.printLine(e))
      } yield ()
    } else {
      ZIO.unit
    }
  } yield ()

  val run = for {
    graphService <- GraphService.create
    _ <- Console.printLine("Welcome to the Graph application!").orDie
    _ <- initialLoad(graphService)
    _ <- program(graphService)
  } yield ()
}
