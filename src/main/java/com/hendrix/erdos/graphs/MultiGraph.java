package com.hendrix.erdos.graphs;

import com.hendrix.erdos.graphs.engines.AdjIncidenceGraphEngine;
import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;

import java.util.Collection;

/**
 * undirected multi graph
 * @author Tomer Shalev
 */
public class MultiGraph extends UndirectedGraph {

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
    public Collection<Edge> getMultiEdges(IVertex u, IVertex v) {
        return getGraphEngine().getMultiEdges(u, v);
    }

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
    public Collection<Edge> removeMultiEdges(IVertex u, IVertex v)
    {
        return getGraphEngine().removeMultiEdges(u, v);
    }

    @Override
    public boolean hasMultiEdges() {
        return true;
    }

    @Override
    public boolean hasSelfLoops() {
        return false;
    }

    public AdjIncidenceGraphEngine getTypedGraphEngine() {return (AdjIncidenceGraphEngine)getGraphEngine();}

    @Override
    public IGraphEngine graphEngineFactory() {
        return new AdjIncidenceGraphEngine();
    }
}
