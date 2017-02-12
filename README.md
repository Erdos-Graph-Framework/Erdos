<div align="center"><img src="https://vectr.com/hendrixstring/buN6JaMtL.svg?width=800&height=225&select=buN6JaMtLpage0"/></div>

<p/>*Erdos* is a very light, modular and super easy to use modern Graph theoretic algorithms framework for `Java`. It
contains graph algorithms that you can apply swiftly iwth one line of code and was primarily developed to back a worker
manager tasks for various *Java* projects including one in *Android*.
<p/>[![Build status](https://travis-ci.org/Erdos-Graph-Framework/Erdos.svg?branch=master)](https://travis-ci.org/Erdos-Graph-Framework/Erdos) [![Release](https://jitpack.io/v/Erdos-Graph-Framework/Erdos.svg)](https://jitpack.io/#Erdos-Graph-Framework/Erdos)

## How to use

Option 1: Jitpack

Add Jitpack in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add to your dependencies:

```groovy
dependencies {
    compile 'com.github.Erdos-Graph-Framework:Erdos:v1.0'
    // or the following for the current master snapshot
    // compile 'com.github.Erdos-Graph-Framework:Erdos:master-SNAPSHOT'
}
```

Option 2: Simply fork or download the project, you can also download and create `.jar` file yourself, with

```bash
git clone https://github.com/Erdos-Graph-Framework/Erdos.git erdos
cd erdos
./gradlew build
ls -l build/libs
```

Option 3: grab the latest released jar

[https://github.com/Erdos-Graph-Framework/Erdos/releases](https://github.com/Erdos-Graph-Framework/Erdos/releases)

## Notable technical features
* compatible with `Java 7`
* compose your graph by features and engine. modular design.
* production proved code. Used in a commercial project.

## Supported graphs
* simple graph, directed and undirected
* multi edge graph, directed and undirected
* pseudo graph
* custom graph, configure by self-loops, multi-edges and a graph engine.

## builtin algorithms
Erdos is a framework to easily help you design graph algorithms
with the correct abstractions and utilities. The builtin algorithms are:
* search algorithms
    * [BFS](https://en.wikipedia.org/wiki/Breadth-first_search) - Breadth First Search
    * [DFS](https://en.wikipedia.org/wiki/Depth-first_search) - Depth First Search
* sorting
    * [Topological sorting](https://en.wikipedia.org/wiki/Topological_sorting)
* structure
    * [Strongly Connected Components](https://en.wikipedia.org/wiki/Strongly_connected_component)
* minimum spanning tree
    * [Prim's algorithm](https://en.wikipedia.org/wiki/Prim's_algorithm)
    * [Kruskal's algorithm](https://en.wikipedia.org/wiki/Kruskal's_algorithm)
* single source shortest path
    * [Bellman–Ford algorithm](https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm)
    * [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra's_algorithm)
    * Directed acyclic graph algorithm
* all pairs shortest path
    * [Floyd–Warshall algorithm](https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm)
    * [Johnson's algorithm](https://en.wikipedia.org/wiki/Johnson's_algorithm)
* minimum spanning tree
    * [Prim's algorithm](https://en.wikipedia.org/wiki/Prim's_algorithm)
    * [Kruskal's algorithm](https://en.wikipedia.org/wiki/Kruskal's_algorithm)
* transformations
    * Square of a graph
    * transitive closure of a graph
    * transpose of a graph

## builtin graph engines
* **Adjacency** and **Incidence** list based graph engine <br/>designed for optimal complexity for algorithms that require more than a moderate edge queries.

### Instructions, code by examples
#### 1. creating a very simple graph

```java
SimpleDirectedGraph graph_triangle = new SimpleDirectedGraph();

Vertex v0 = new Vertex();
Vertex v1 = new Vertex();
Vertex v2 = new Vertex("tag_v2");

graph_triangle.addVertex(v0);
graph_triangle.addVertex(v1);
graph_triangle.addVertex(v2);

Edge e_0 = graph_triangle.addEdge(v0, v1);
graph_triangle.addEdge(v1, v2);
graph_triangle.addEdge(v2, v3);

graph_triangle.print();

// iterate the graph vertices directly
for (IVertex vertex : graph_triangle) {
    System.out.println(vertex.toString());
}

// iterate the edges of the graph
for (Edge edge : graph_triangle.edges()) {
    System.out.println(edge.toString());
}

// removing a vertex in any of the following ways will remove it's connected edges as well,
// also removing any edge in similar fashion will update the graph :)
graph_triangle.removeVertex(v0);
graph_triangle.vertices().remove(v1);
graph_triangle.vertices().iterator().remove();

```

#### 2. use a factory for custom graph
you can define your graph in terms of self loops, multi edges (per vertex) and
a custom implementation of a graph engine.
```java
boolean allow_self_loops = true;
boolean allow_multi_edges = true;

UndirectedGraph graph_undirected = Erdos.newUndirectedGraphWithEngine(new AdjIncidenceGraphEngine(), 
                                                                      allow_self_loops, allow_multi_edges);

DirectedGraph graph = Erdos.newGraphWithEngine(new AdjIncidenceGraphEngine(), 
                                               Edge.EDGE_DIRECTION.DIRECTED,
                                               allow_self_loops, allow_multi_edges);

```

#### 3. algorithms introduction
every algorithm extends `AbstractGraphAlgorithm<T, E extends IGraph>`, which is generically
typed `E` for input graph and `T` for output and must implement
* `T applyAlgorithm()` method

for example, the **`Bellman-Ford`** algorithm for single source shortest path,
followed by the **`Floyd-Warshall`** algorithm for all pairs shortest paths.

```java
private void BellmanFord()
{
    SimpleDirectedGraph graph = new SimpleDirectedGraph();

    Vertex s = new Vertex("s");
    Vertex t = new Vertex("t");
    Vertex x = new Vertex("x");
    Vertex y = new Vertex("y");
    Vertex z = new Vertex("z");

    graph.addVertex(s);
    graph.addVertex(t);
    graph.addVertex(x);
    graph.addVertex(y);
    graph.addVertex(z);

    graph.addEdge(s, t, 6);
    graph.addEdge(t, x, 5);
    graph.addEdge(x, t, -2);
    graph.addEdge(s, y, 7);
    graph.addEdge(y, z, 9);
    graph.addEdge(t, y, 8);
    graph.addEdge(z, x, 7);
    graph.addEdge(t, z, -4);
    graph.addEdge(y, x, -3);
    graph.addEdge(z, s, 2);

    graph.setTag("graph");
    graph.print();

    // apply the Bellman-Ford algorithm
    ShortestPathsTree res = new BellmanFordShortestPath(graph).setStartVertex(s).applyAlgorithm();
    // print it
    res.print();
    // apply the Floyd-Warshall algorithm
    AllPairsShortPathResult floyd_result = new FloydWarshall(graph).applyAlgorithm();
    // print the shortest paths tree of the vertex
    floyd_result.shortestPathsTreeOf(s).print();
    // print the shortest path between two nodes
    System.out.println(floyd_result.shortestPathBetween(s, z).toString());
}

```

#### 4. algorithms, more examples
this example shows the simplicity of the framework (hopefully ;)) where we apply 5
different algorithms sequentally
```java
// perform a breadth first search
BFS.BreadthFirstTree breadthFirstTree = new BFS(graph, s).applyAlgorithm();
// perform a depth first search
DFS.DepthFirstForest depthFirstForest = new DFS(graph).applyAlgorithm();
// extract the strongly connected components of the graph
ArrayList<HashSet<IVertex>> hashSets = new SCC(graph).applyAlgorithm();
// perform a topological sort on the graph
LinkedList<IVertex> res_sort = new TopologicalSort(graph).applyAlgorithm();
// compute all pairs shortest paths using the Floyd-Warshall algorithm
AllPairsShortPathResult floyd_result = new FloydWarshall(graph).applyAlgorithm();

```

#### 5. algorithms factories
for major algorithms types, you can comfortably use the following algorithms factories
* `MinSpanTreeFactory` - for Minimum Spanning Tree/Forest, for example:
```java
AbstractGraphAlgorithm<UndirectedGraph, IUndirectedGraph> alg = MinSpanTreeFactory.newMST(graph, MstAlgorithm.KRUSKAL, start_vertex);
AbstractGraphAlgorithm<UndirectedGraph, IUndirectedGraph> alg2 = MinSpanTreeFactory.newMST(graph, MstAlgorithm.PRIM, start_vertex);
```
* `SingleSourceShortPathFactory` - for single source shortest path, for example:
```java
AbstractShortestPathAlgorithm alg = SingleSourceShortPathFactory.newSingleSourceShortPath(graph, SSSPAlgorithm.DIJKSTRA, start_vertex, end_vertex);
```
* `AllPairsShortPathFactory` - for shortest paths between all pairs, for example:
```java
AbstractGraphAlgorithm<AllPairsShortPathResult, IDirectedGraph> alg2 = AllPairsShortPathFactory.newAllPairsShortPath(graph, APSPAlgorithm.Johnson);```
```
#### 6. utilities
a bunch of helper utilities can be found in the package **`com.hendrix.erdos.utils`**
* **`SVertexUtils.java`** - query vertex order information inside a graph
* **`SEdgeUtils.java`** - query edge order information inside a graph
* **`SMatrixUtils.java`** - compute the adjacency and incidence matrix of a graph
* **`SGraphUtils.java`** - get a sorted list of the weighted edges in a graph

### used in
* [`Android-Zorn`](https://github.com/HendrixString/Android-Zorn) - for constructing a worker manager based on topological sorting in a graph.

### Contributions
contributions are most welcomed, please consult [`CONTRIBUTING.md`](CONTRIBUTING.md)

### License
If you like it -> star or share it with others

```
Copyright (C) 2016 Tomer Shalev (https://github.com/HendrixString)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
```

### Contact Author
* [tomer.shalev@gmail.com](tomer.shalev@gmail.com)
* [Google+ TomershalevMan](https://plus.google.com/+TomershalevMan/about)
* [Facebook - HendrixString](https://www.facebook.com/HendrixString)
