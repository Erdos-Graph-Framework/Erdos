package com.hendrix.erdos.graphs;

import com.hendrix.erdos.types.IVertex;

/**
 * @author Tomer Shalev
 */
public interface IUndirectedGraph extends IGraph {
    /**
     * @param vertex the vertex in question
     *
     * @return the out degree of  the vertex
     */
    int degreeOfVertex(IVertex vertex);

}
