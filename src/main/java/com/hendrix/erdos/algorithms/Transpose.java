package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.DirectedGraph;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.types.IVertex;

import java.util.Collection;

/**
 * computes a new Transpose graph of input Graph G
 * @author Tomer Shalev
 */
public class Transpose extends GraphAlgorithm<DirectedGraph, IDirectedGraph> {

    public Transpose(IDirectedGraph graph_input) {
        super(graph_input, "Transpose");
    }

    @Override
    public DirectedGraph applyAlgorithm() {
        if(_graph_input==null)
            throw new AlgorithmException("_graph_input==null", this);

        transpose();

        return _result_algorithm;
    }

    protected void transpose()
    {
        _result_algorithm = new DirectedGraph() {
            @Override
            public boolean hasMultiEdges() {
                return _graph_input.hasMultiEdges();
            }

            @Override
            public boolean hasSelfLoops() {
                return _graph_input.hasSelfLoops();
            }

            @Override
            public IGraphEngine graphEngineFactory() {
                return _graph_input.graphEngineFactory();
            }
        };

        // fill the graph V

        Collection<IVertex> vertices = _graph_input.vertices();

        for (IVertex vertex : vertices) {
            _result_algorithm.addVertex(vertex);
        }

        // square

        Collection<IVertex> adjList_v;

        for (IVertex v : vertices) {
            adjList_v = _graph_input.getNeighborsOf(v);

            for (IVertex u : adjList_v) {
                _result_algorithm.addEdge(u, v);
            }

        }

    }

}
