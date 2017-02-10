package com.hendrix.erdos.graphs;

import com.hendrix.erdos.graphs.engines.AbstractGraphEngine;
import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;

import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract Graph
 *
 * @see IGraph
 * @author Tomer Shalev
 */
abstract public class AbstractGraph implements IGraph {
    /**
     * the type of this graph direction wise
     * <p/>
     * <code>EDGE_DIRECTION.DIRECTED, EDGE_DIRECTION.UNDIRECTED</code>
     */
    //protected Edge.EDGE_DIRECTION _graphType  = Edge.EDGE_DIRECTION.DIRECTED;
    /**
     * the id of this graph
     */
    private String _id;
    /**
     * the tag of this graph
     */
    private String _tag;

    /**
     * the graph engine behind the graph
     */
    private IGraphEngine _graphEngine;

    protected AbstractGraph() {
        _graphEngine = graphEngineFactory();
        _graphEngine.setGraph(this);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<IVertex> iterator() {
        return getGraphEngine().iterator();
    }

    @Override
    abstract public Edge.EDGE_DIRECTION getGraphType();

    /**
     * @return get V of G=(V, E)
     */
    @Override
    public Collection<IVertex> vertices() {
        return getGraphEngine().vertices();
    }

    /**
     * get the edges list
     *
     * @return the edge collection
     */
    @Override
    public Collection<Edge> edges() {
        return getGraphEngine().edges();
    }

    /**
     * get the accessible vertex neighbors of vertex v1
     *
     * @param vertex the vertex
     *
     * @return the neighbors
     */
    @Override
    public Collection<IVertex> getNeighborsOf(IVertex vertex) {
        return getGraphEngine().getNeighborsOf(vertex);
    }

    /**
     * get the list of incident edges that go out from vertex
     *
     * @param vertex the vertex
     *
     * @return the out incidence list of vertex
     */
    @Override
    public Collection<Edge> getIncidenceOutListOf(IVertex vertex) {
        return getGraphEngine().getIncidenceOutListOf(vertex);
    }

    /**
     * get the list of incident edges that go into the vertex
     *
     * @param vertex the vertex
     *
     * @return the in incidence list of vertex
     */
    @Override
    public Collection<Edge> getIncidenceInListOf(IVertex vertex) {
        return getGraphEngine().getIncidenceInListOf(vertex);
    }

    /**
     * check whether <code>vertex</code> belongs to the graph's vertex list
     *
     * @param vertex the vertex in question
     *
     * @return true if graph contains this vertex
     */
    @Override
    public boolean hasVertex(IVertex vertex) {
        return getGraphEngine().hasVertex(vertex);
    }

    /**
     * add <code>vertex</code> into the graph
     *
     * @param vertex the vertex to add
     *
     * @return true if success
     */
    @Override
    public boolean addVertex(IVertex vertex) {
        return getGraphEngine().addVertex(vertex);
    }

    /**
     * add all the vertices and edges of the collection into the graph.
     *
     * @param vertices a collection of vertices
     * @param edges    a collection of vertices
     */
    @Override
    public void addAll(Collection<IVertex> vertices, Collection<Edge> edges) {
        getGraphEngine().addAll(vertices, edges);
    }

    /**
     * remove <code>vertex</code> from the graph
     *
     * @param vertex the vertex to remove
     *
     * @return true if success
     */
    @Override
    public boolean removeVertex(IVertex vertex) {
        return getGraphEngine().removeVertex(vertex);
    }

    /**
     * check whether there exists an edge (v1, v2) is in graph
     *
     * @param v1 vertex v1
     * @param v2 vertex v2
     *
     * @return true if graph contains this edge
     */
    @Override
    public boolean hasEdge(IVertex v1, IVertex v2) {
        return getGraphEngine().hasEdge(v1, v2);
    }

    /**
     * check whether edge instance is in graph
     *
     * @param edge the edge in question
     *
     * @return true if graph contains this edge
     */
    @Override
    public boolean hasEdge(Edge edge) {
        return getGraphEngine().hasEdge(edge);
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     *
     * @return the edge so use can query the id
     */
    @Override
    public Edge addEdge(IVertex v1, IVertex v2) {
        return getGraphEngine().addEdge(v1, v2);
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     * @param weight the weight of the edge
     *
     * @return the edge so use can query the id
     */
    @Override
    public Edge addEdge(IVertex v1, IVertex v2, float weight) {
        return getGraphEngine().addEdge(v1, v2, weight);
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param edge the edge to add
     *
     * @return the edge so use can query the id
     */
    @Override
    public Edge addEdge(Edge edge) {
        return getGraphEngine().addEdge(edge);
    }

    /**
     * remove edge (v1, v2) from the graph
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     *
     * @return the edge if success, or null if failed
     */
    @Override
    public Edge removeEdge(IVertex v1, IVertex v2) {
        return getGraphEngine().removeEdge(v1, v2);
    }

    /**
     * get the edge that connects (v1, v2) from the graph
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     *
     * @return the edge if success, or null if failed
     */
    @Override
    public Edge getEdge(IVertex v1, IVertex v2) {
        return getGraphEngine().getEdge(v1, v2);
    }

    /**
     * remove edge (v1, v2) from the graph
     *
     * @param edge an edge that already belong to the graph
     *
     * @return the edge if success, or null if failed
     */
    @Override
    public Edge removeEdge(Edge edge) {
        return getGraphEngine().removeEdge(edge);
    }

    /**
     * @return the number of vertices in the Graph
     */
    @Override
    public int numVertices() {
        return getGraphEngine().numVertices();
    }

    /**
     * @return the number of edges in the fraph
     */
    @Override
    public int numEdges() {
        return getGraphEngine().numEdges();
    }

    /**
     * @return true if graph is empty, False - otherwise
     */
    @Override
    public boolean isEmpty() {
        return getGraphEngine().isEmpty();
    }

    /**
     * clear the graph into an empty graph
     */
    @Override
    public void clear() {
        getGraphEngine().clear();
    }

    /**
     * dispose the item
     */
    @Override
    public void dispose() {
        getGraphEngine().dispose();
    }

    @Override
    public void setId(String id) {
        _id =   id;
    }

    @Override
    public String getId() {
        return _id;
    }

    @Override
    public boolean hasId() {
        return _id!=null;
    }

    /**
     * set the tag/description of an object
     *
     * @param tag the tag
     */
    @Override
    public void setTag(String tag) {
        _tag = tag;
    }

    /**
     * @return the tag of the object
     */
    @Override
    public String getTag() {
        return _tag;
    }

    /**
     * get the graph engine. if the graph engine is null
     * then {@link #graphEngineFactory()}  will create a new graph engine
     * and will set it's graph to this one.
     *
     * @return IGraphEngine
     * @see IGraphEngine
     */
    @Override
    public IGraphEngine getGraphEngine() {
        return _graphEngine;
    }

    /**
     * does this graph support multi edges
     *
     * @return {@code true, false}
     */
    abstract public boolean hasMultiEdges();

    /**
     * does this graph support self loops
     *
     * @return {@code true, false}
     */
    abstract public boolean hasSelfLoops();

    /**
     * the graph engine instantiation factory
     *
     * @return a graph engine
     *
     * @see IGraphEngine
     * @see AbstractGraphEngine
     */
    abstract public IGraphEngine graphEngineFactory();

    @Override
    public void print() {
        System.out.println(toString());
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "id=" + getId() + ", tag=" + getTag() + "::" + getGraphEngine().toString();
    }
}
