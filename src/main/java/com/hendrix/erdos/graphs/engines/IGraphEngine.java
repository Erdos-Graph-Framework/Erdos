package com.hendrix.erdos.graphs.engines;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.interfaces.IDisposable;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Edge;

import java.util.Collection;

/**
 * the back bone engine of a graph vertices and edges.
 * implement this if ou would like to try different engine, representations, data structures etc
 * for a graph. by default, the interface implies that all types of graphs may be supported.
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public interface IGraphEngine extends IGraphRepresentation, Iterable<IVertex>, IDisposable {
    /**
     * @param vertex the vertex in question
     *
     * @return the out degree of  the vertex
     */
    int outDegreeOfVertex(IVertex vertex);
    /**
     * @param vertex the vertex in question
     *
     * @return the in degree of  the vertex
     */
    @SuppressWarnings("UnusedDeclaration")
    int inDegreeOfVertex(IVertex vertex);

    /**
     * remove all the edges that connect the two vertices
     *
     * @param u first vertex
     * @param v second vertex
     *
     * @return a collection of edges that connect the two vertices that were removed if any
     *
     * @throws java.lang.UnsupportedOperationException if the engine does not support multi edges
     */
    Collection<Edge> removeMultiEdges(IVertex u, IVertex v);

    /**
     * get all the edges that connect the two vertices
     *
     * @param u first vertex
     * @param v second vertex
     *
     * @return a collection of edges that connect the two vertices if any
     *
     * @throws java.lang.UnsupportedOperationException if the engine does not support multi edges
     */
    Collection<Edge> getMultiEdges(IVertex u, IVertex v);
    /**
     * get the graph that this engine works for
     *
     * @return the graph
     */
    public IGraph getGraph();
    /**
     * set the graph that this engine works for.
     *
     */
    public void setGraph(IGraph graph);

}
