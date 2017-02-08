package com.hendrix.erdos.graphs;

import com.hendrix.erdos.graphs.engines.AdjIncidenceGraphEngine;
import com.hendrix.erdos.graphs.engines.IGraphEngine;

/**
 * Pseudo graph
 * @author Tomer Shalev
 */
public class PseudoGraph extends UndirectedGraph {

    @Override
    public boolean hasMultiEdges() {
        return true;
    }

    @Override
    public boolean hasSelfLoops() {
        return true;
    }

    public AdjIncidenceGraphEngine getTypedGraphEngine() {return (AdjIncidenceGraphEngine)getGraphEngine();}

    @Override
    public IGraphEngine graphEngineFactory() {
        return new AdjIncidenceGraphEngine();
    }
}
