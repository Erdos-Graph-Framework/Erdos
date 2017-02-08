package com.hendrix.erdos.graphs;

import com.hendrix.erdos.graphs.engines.AbstractGraphEngine;
import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.graphs.engines.IGraphRepresentation;
import com.hendrix.erdos.interfaces.IDisposable;
import com.hendrix.erdos.interfaces.IId;
import com.hendrix.erdos.interfaces.ITag;
import com.hendrix.erdos.types.IVertex;

/**
 * Adjacency-list is embedded inside the vertex for optimality
 * @author Tomer Shalev
 */
public interface IGraph extends IGraphRepresentation, IId, ITag, IDisposable, Iterable<IVertex>
{
    /**
     * get the graph engine
     *
     * @return IGraphEngine
     * @see IGraphEngine
     */
    IGraphEngine getGraphEngine();

    /**
     * the graph engine instantiation factory
     *
     * @return a graph engine
     *
     * @see IGraphEngine
     * @see AbstractGraphEngine
     */
    IGraphEngine graphEngineFactory();

    /**
     * does this graph support multi edges
     *
     * @return {@code true, false}
     */
    boolean hasMultiEdges();

    /**
     * does this graph support self loops
     *
     * @return {@code true, false}
     */
    boolean hasSelfLoops();

    void print();
}
