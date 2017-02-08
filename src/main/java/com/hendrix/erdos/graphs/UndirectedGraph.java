package com.hendrix.erdos.graphs;

import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Edge;

/**
 * @author Tomer Shalev
 */
abstract public class UndirectedGraph extends AbstractGraph implements IUndirectedGraph {

    @Override
    public Edge.EDGE_DIRECTION getGraphType() {
        return Edge.EDGE_DIRECTION.UNDIRECTED;
    }



    /**
     * @param vertex the vertex in question
     * @return the out degree of  the vertex
     */
    @Override
    public int degreeOfVertex(IVertex vertex) {
        return getGraphEngine().inDegreeOfVertex(vertex);
    }
}
