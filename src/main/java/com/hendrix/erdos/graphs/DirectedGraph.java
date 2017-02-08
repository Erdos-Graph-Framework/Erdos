package com.hendrix.erdos.graphs;

import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Edge;

/**
 * an abstract {@link IDirectedGraph}
 *
 * @author Tomer Shalev
 */
abstract public class DirectedGraph extends AbstractGraph implements IDirectedGraph {

    public DirectedGraph() {
    }

    /**
     * @param vertex the vertex in question
     *
     * @return the out degree of  the vertex
     *
     */
    public int outDegreeOfVertex(IVertex vertex) {
        return getGraphEngine().outDegreeOfVertex(vertex);
    }

    /**
     * @param vertex the vertex in question
     *
     * @return the in degree of  the vertex
     *
     */
    @SuppressWarnings("UnusedDeclaration")
    public int inDegreeOfVertex(IVertex vertex) {
        return getGraphEngine().inDegreeOfVertex(vertex);
    }

    @Override
    public Edge.EDGE_DIRECTION getGraphType() {
        return Edge.EDGE_DIRECTION.DIRECTED;
    }
}
