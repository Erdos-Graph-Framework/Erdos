package com.hendrix.erdos.graphs;

import com.hendrix.erdos.graphs.engines.AdjIncidenceGraphEngine;
import com.hendrix.erdos.graphs.engines.IGraphEngine;

/**
 * Directed simple graph
 *
 * @author Tomer Shalev
 */
public class SimpleDirectedGraph extends DirectedGraph {

    public SimpleDirectedGraph() {
    }

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
