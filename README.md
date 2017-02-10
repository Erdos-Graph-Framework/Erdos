# Erdos
a modular and modern Graph theoretic algorithms framework
### Dependencies
* none

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
    compile 'tbd'
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

```bash

```

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

    // removing a vertex in any of the floowing ways will remove it's connected edges as well,
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

#### 2. Implement a View renderer
```java

```

#### 2. Implement a View renderer
```java

```

#### 2. Implement a View renderer
```java

```

### Contributions
contributions are most welcomed, please consult [`CONTRIBUTING.MD`](CONTRIBUTING.MD)

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
