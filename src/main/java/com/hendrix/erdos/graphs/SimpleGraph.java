package com.hendrix.erdos.graphs;

import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.graphs.engines.AdjIncidenceGraphEngine;

/**
 * undirected simple graph
 *
 * @author Tomer Shalev
 */
public class SimpleGraph extends UndirectedGraph {

    @Override
    public boolean hasMultiEdges() {
        return false;
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
