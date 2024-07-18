# Scala Project

## Project Overview
The Scala Graph Project is a comprehensive library designed to handle graphs. It provides functionalities to add and remove edges, perform searches (DFS and BFS), sort topologically, detect cycles, and compute shortest paths using Floyd-Warshall and Dijkstra's algorithms. The project is split into two subprojects: core, which contains the main graph logic, and app, which provides a ZIO-based terminal application to interact with the graphs.

Note: The current implementation of the application is designed specifically for directed graphs. Undirected graphs are not supported in the terminal application.

### Components Presentation

**GraphJson.scala :**

The [`GraphJson.scala`](/core/src/main/scala/GraphJson.scala) file provides the functionality to serialize and deserialize graphs to and from JSON. This allows for easy storage and retrieval of graph data, for both types of graph.

**GraphViz.scala :**

The [`GraphViz.scala`](/core/src/main/scala/GraphViz.scala) file provides functionality to convert graphs into GraphViz format, which can be used to visualize the graphs (undirected or directed).

**GraphService.scala :**

The [`GraphService.scala`](/app/src/main/scala/GraphService.scala) file provides a service to manage multiple directed graphs within a ZIO environment. It includes functionalities to add graphs, add edges, display graphs, save/load graphs to/from files, and perform various graph algorithms.

## Instructions
**How to build the project :**

- Ensure you have sbt installed on your machine.
- Navigate to the root directory of the project.
- Run the following command to build the project :
```console
sbt clean compile
```
**How to run the application :**

- Navigate to the root directory of the project.
- Run the following command to start the ZIO terminal application :
```console
sbt app/run
```
(You can also run the main in the core folder doing : `sbt core/run`)

**How to run the tests :**

- Navigate to the root directory of the project.
- Run the following command to execute the tests in the core subproject:
```console
sbt core/test
```

## Design Decision

The design of the ZIO application is centered around the use of ZIO's Ref to manage state immutably. This ensures safe concurrent updates to the graph data, leveraging ZIO's effect system to handle errors and side effects through a clean API provided by GraphService. Ref offers several advantages: it guarantees immutability by returning a new state with each update without modifying the old state, simplifies reasoning and verification of the code, and prevents race conditions with atomic operations, ensuring secure state management in concurrent environments.

For more complex applications, Ref provides efficient and scalable state management, even with increased concurrency demands. It encourages a clear separation of concerns by distinguishing between effects and state management, simplifying development and maintenance. Furthermore, this approach integrates well with other components in the ZIO ecosystem, facilitating the addition of new features and extending the application.

## Usage Examples
Here are some examples of how to use the ZIO application:

Firstly, when you start the application you have this interface :
```console
Welcome to the Graph application!
Do you want to load a graph from a file? (yes/no):
```
You can choose if you want to load a graph from a file, if you put 'yes' you will have to enter the file name and to give a name to your graph. If you select 'no', you will access to the next interface :
```console
1. Add Graph
2. Add Edge
3. Display Graph
4. Save Graph to File
5. Load Graph from File
11. HasCycle
6. Depth First Search (DFS)
7. Breadth First Search (BFS)
8. Topological Sort
9. Floyd-Warshall Algorithm
12. Dijkstra
10. Exit

Enter choice:
```

**You can select different cases ! Here is an example on how you can use the application :**

- Add a graph:
```console
Input: 1
Enter graph name: my-graph
Output: Graph my-graph added.
```
- Add an edge:
```console
Input: 2
Enter graph name: my-graph
Enter source vertex: A
Enter destination vertex: B
Enter weight: 2
Output: Edge added.
```
- Display a graph:
```console
Input: 3
Enter graph name: my-graph
Output: Graph my-graph: Map(A -> Map(B -> 2))
```
- Save Graph to File:
```console
Input: 4
Enter graph name: my-graph
Enter file path: my-graph.json
```
- Load Graph from File:
```console
Input: 5
Enter graph name: my-graph2
Enter file path: my-graph.json
```
- Check for cycle:
```console
Input: 6
Enter graph name: my-graph
Output: HasCycle result: false
```
- Run Depth First Search (DFS):
```console
Input: 7
Enter graph name: my-graph
Enter start vertex: A
Output: DFS result: List(A, B)
```
- Run Breadth First Search (BFS):
```console
Input: 8
Enter graph name: my-graph
Enter start vertex: A
Output: BFS result: List(A, B)
```
- Run Topological Sort:
```console
Input: 9
Enter graph name: my-graph
Output: Topological Sort result: List(A, B)
```
- Run Floyd-Warshall Algorithm:
```console
Input: 10
Enter graph name: my-graph
Output: Floyd-Warshall result: Map(A -> Map(A -> 0, B -> 2), B -> Map(A -> 9223372036854775807, B -> 0))
```
- Run Dijkstra:
```console
Input: 11
Enter graph name: my-graph
Enter start vertex: A
Output: Dijkstra result: Map(A -> 0, B -> 2)
```
- Exit:
```console
Input: 12
```
*Of course you can run the application with your own graph.*

*You can create your own graph in json file, you just have to follow this pattern :*

```json
{"vertices":["A","B",...,"Y","Z"],"edges":[["A","B",2],["B","C",5],...,["A","Z",10], ["Y","Z",4]]}
```


## Testing
**How to run the tests :**

- Navigate to the root directory of the project.
- Run the following command to execute the tests in the core subproject:
```console
sbt core/test
```
**Overview of the test coverage**

The test coverage includes:

**GraphJsonSupportSpec.scala :**

The [`GraphJsonSupportSpec.scala`](/core/src/test/scala/GraphJsonSpec.scala) tests the serialization and deserialization of directed and undirected graphs. And it ensures that the JSON representation of the graphs is correct.

Here is the output if the tests are correct :
```console
[info] GraphVizSupportSpec:
[info] GraphVizSupport
[info] - should convert DirectedGraph to GraphViz format correctly
[info] - should convert UndirectedGraph to GraphViz format correctly
```

**GraphVizSupportSpec.scala :**

The [`GraphVizSupportSpec.scala`](/core/src/test/scala/GraphVizSpec.scala) tests the conversion of directed and undirected graphs to the GraphViz format. And it ensures that the generated GraphViz strings are accurate.

Here is the output if the tests are correct :
```console
[info] GraphJsonSupportSpec:
[info] GraphJsonSupport
[info] - should serialize and deserialize DirectedGraph to and from JSON correctly
[info] - should serialize and deserialize UndirectedGraph to and from JSON correctly
```