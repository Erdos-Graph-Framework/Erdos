package com.hendrix.erdos;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.graphs.AbstractGraph;
import com.hendrix.erdos.graphs.DirectedGraph;
import com.hendrix.erdos.graphs.UndirectedGraph;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.Edge.*;

/**
 * Graph instances factory
 *
 * @author Tomer Shalev
 *
 * @see IGraph
 * @see AbstractGraph
 * @see DirectedGraph
 * @see UndirectedGraph
 */
public class Erdos {
    private Erdos() {}

    /**
     * clone empty graph with the same characteristics (graph engine, multi edge support, self loops supprt)
     *
     * @param graph the graph to clone
     *
     * @return an {@link AbstractGraph} implementation
     */
    static public AbstractGraph cloneEmptyGraphOf(IGraph graph) {
        return newGraphWithEngine(graph.graphEngineFactory(), graph.getGraphType(), graph.hasSelfLoops(), graph.hasMultiEdges());
    }

    /**
     * clone a graph. the vertices and edges are referenced and not cloned from the original graph.
     *
     * @param graph the graph to copy
     *
     * @return the copied graph
     */
    static public AbstractGraph cloneGraphOf(IGraph graph) {
        AbstractGraph graph_res = newGraphWithEngine(graph.graphEngineFactory(), graph.getGraphType(), graph.hasSelfLoops(), graph.hasMultiEdges());
        graph_res.addAll(graph.vertices(), graph.edges());

        return graph_res;
    }

    /**
     * instantiate a new {@link AbstractGraph} implementation. note:
     * <ul>
     *     <li> {@code EDGE_DIRECTION.DIRECTED} will return {@link DirectedGraph} implementation.
     *     <li> {@code EDGE_DIRECTION.UNDIRECTED} will return {@link UndirectedGraph} implementation.
     * </ul>
     *
     * @param graphEngine   a graph engine
     * @param direction     graph edge direction, as specified in {@link Edge.EDGE_DIRECTION}.
     * @param selfLoops     support for self loops
     * @param multiEdges    support for multi edges
     * @param <T>           a {@link IGraphEngine} instance.
     *
     * @return a new graph
     */
    static public <T extends IGraphEngine, R extends AbstractGraph> R newGraphWithEngine(final T graphEngine, final EDGE_DIRECTION direction,
                                                                            final boolean selfLoops, final boolean multiEdges) {
        switch (direction) {
            case DIRECTED:
                return (R) newDirectedGraphWithEngine(graphEngine, selfLoops, multiEdges);
            case UNDIRECTED:
                return (R) newUndirectedGraphWithEngine(graphEngine, selfLoops, multiEdges);
        }

        return null;
    }

    /**
     * instantiate a new {@link DirectedGraph} implementation. note:
     *
     * @param graphEngine   a graph engine
     * @param selfLoops     support for self loops
     * @param multiEdges    support for multi edges
     * @param <T>           a {@link IGraphEngine} instance.
     *
     * @return a new graph
     */
    static public  <T extends IGraphEngine> DirectedGraph newDirectedGraphWithEngine(final T graphEngine, final boolean selfLoops,
                                                                                     final boolean multiEdges) {
        return new DirectedGraph() {
            @Override
            public boolean hasMultiEdges() {
                return multiEdges;
            }

            @Override
            public boolean hasSelfLoops() {
                return selfLoops;
            }

            @Override
            public IGraphEngine graphEngineFactory() {
                return graphEngine;
            }
        };
    }

    /**
     * instantiate a new {@link UndirectedGraph} implementation. note:
     *
     * @param graphEngine   a graph engine
     * @param selfLoops     support for self loops
     * @param multiEdges    support for multi edges
     * @param <T>           a {@link IGraphEngine} instance.
     *
     * @return a new graph
     */
    static public <T extends IGraphEngine> UndirectedGraph newUndirectedGraphWithEngine(final T graphEngine, final boolean selfLoops,
                                                                                         final boolean multiEdges) {
        return new UndirectedGraph() {
            @Override
            public boolean hasMultiEdges() {
                return multiEdges;
            }

            @Override
            public boolean hasSelfLoops() {
                return selfLoops;
            }

            @Override
            public IGraphEngine graphEngineFactory() {
                return graphEngine;
            }
        };
    }

}
