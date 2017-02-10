package com.hendrix.erdos.graphs.engines;

import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;

import java.util.Collection;

/**
 * basic graph operations representation
 *
 * @author Tomer Shalev
 */
public interface IGraphRepresentation {

    /**
     * @return <code>GraphType.DIRECTED</code> or <code>GraphType.UNDIRECTED</code>
     */
    public Edge.EDGE_DIRECTION getGraphType();
    /**
     * @return get V of G=(V, E)
     */
    java.util.Collection<IVertex> vertices();

    /**
     * get the edges list
     *
     * @return the edge collection
     */
    Collection<Edge> edges();

    /**
     * get the accessible vertex neighbors of vertex v1
     *
     * @param vertex the vertex
     *
     * @return the neighbors
     */
    Collection<IVertex> getNeighborsOf(IVertex vertex);

    /**
     * get the list of incident edges that go out from vertex
     *
     * @param vertex the vertex
     * @return the out incidence list of vertex
     */
    Collection<Edge> getIncidenceOutListOf(IVertex vertex);

    /**
     * get the list of incident edges that go into the vertex
     *
     * @param vertex the vertex
     * @return the in incidence list of vertex
     */
    Collection<Edge> getIncidenceInListOf(IVertex vertex);

    /**
     * check whether <code>vertex</code> belongs to the graph's vertex list
     *
     * @param vertex the vertex in question
     * @return true if graph contains this vertex
     */
    boolean hasVertex(IVertex vertex);

    /**
     * add <code>vertex</code> into the graph
     *
     * @param vertex the vertex to add
     * @return true if success
     */
    boolean addVertex(IVertex vertex);

    /**
     * add all the vertices and edges of the collection into the graph.
     *
     * @param vertices a collection of vertices
     * @param edges    a collection of vertices
     */
    void addAll(Collection<IVertex> vertices, Collection<Edge> edges);

    /**
     * remove <code>vertex</code> from the graph
     *
     * @param vertex the vertex to remove
     * @return true if success
     */
    boolean removeVertex(IVertex vertex);

    /**
     * check whether there exists an edge (v1, v2) is in graph
     *
     * @param v1 vertex v1
     * @param v2 vertex v2
     * @return true if graph contains this edge
     */
    boolean hasEdge(IVertex v1, IVertex v2);

    /**
     * check whether edge instance is in graph
     *
     * @param edge the edge in question
     * @return true if graph contains this edge
     */
    boolean hasEdge(Edge edge);

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     * @return the edge so use can query the id
     */
    Edge addEdge(IVertex v1, IVertex v2);

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     * @param weight the weight of the edge
     * @return the edge so use can query the id
     */
    Edge addEdge(IVertex v1, IVertex v2, float weight);

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param edge the edge to add
     * @return the edge so use can query the id
     */
    Edge addEdge(Edge edge);

    /**
     * remove edge (v1, v2) from the graph
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     * @return the edge if success, or null if failed
     */
    Edge removeEdge(IVertex v1, IVertex v2);

    /**
     * get the edge that connects (v1, v2) from the graph
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     * @return the edge if success, or null if failed
     */
    Edge getEdge(IVertex v1, IVertex v2);

    /**
     * remove edge (v1, v2) from the graph
     *
     * @param edge an edge that already belong to the graph
     * @return the edge if success, or null if failed
     */
    Edge removeEdge(Edge edge);

    //IGraph clone() throws CloneNotSupportedException;

    /**
     * @return the number of vertices in the Graph
     */
    int numVertices();

    /**
     * @return the number of edges in the fraph
     */
    int numEdges();

    /**
     * @return true if graph is empty, False - otherwise
     */
    boolean isEmpty();

    /**
     * clear the graph into an empty graph
     */
    void clear();

    String toString();

    public void print();

}
