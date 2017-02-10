package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.graphs.DirectedGraph;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.utils.SMatrixUtils;
import com.hendrix.erdos.utils.SVertexUtils;

import java.util.HashMap;

/**
 * compute the transitive closure graph of an input graph.
 *
 * Given a directed graph {@code G = (V, E)} with vertex set {@code V = {1, 2,...,n}}, we may wish to find out
 * whether there is a path in {@code G} from {@code i} to {@code j} for all vertex pairs {@code i, j ∈ V}. The transitive closure
 * of G is defined as the graph {@code G* = (V, E*)}, <br/>
 * where {@code E*= {(i, j) : there is a path from vertex i to vertex j in G}}.
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class TransitiveClosure extends AbstractGraphAlgorithm<IDirectedGraph, IDirectedGraph> {

    public TransitiveClosure(IDirectedGraph graph_input) {
        super(graph_input, "Transitive Closure algorithm");
    }

    @Override
    public IDirectedGraph applyAlgorithm() {
        closure();

        return _result_algorithm;
    }


//    TRANSITIVE-CLOSURE(G)
//    1  n ← |V[G]|
//    2  for i ← 1 to n
//    3       do for j ← 1 to n
//    4              do if i = j or (i, j) ∈ E[G]
//    5                    then
//    6                    else
//    7  for k ← 1 to n
//    8       do for i ← 1 to n
//    9              do for j ← 1 to n
//    10                     do
//    11  return T(n)
    private void closure() {
        int n = _graph_input.numVertices();

        int[][] T_prev = new int[n][n];
        int[][] T = new int[n][n];

        HashMap<Integer, IVertex> indicesVertices = SVertexUtils.getIndicesVertices(_graph_input);

        // init
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || hasEdgeByVerticesIndices(i, j, indicesVertices))
                    T_prev[i][j] = 1;
                else
                    T_prev[i][j] = 0;
            }

        }

        // algorithm

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                   if(T_prev[i][j]==1 || (T_prev[i][k]==1 && T_prev[k][j]==1))
                       T[i][j] = 1;
                    else
                       T[i][j] = 0;
                }

            }

            SMatrixUtils.array2dCopy(T, T_prev);
        }

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

        _result_algorithm.addAll(_graph_input.vertices(), _graph_input.edges());

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(T[i][j] == 1)
                   _result_algorithm.addEdge(indicesVertices.get(i), indicesVertices.get(j));
            }
        }

        _result_algorithm.setTag("Transitive Closure result");

    }

    private boolean hasEdgeByVerticesIndices(int i, int j, HashMap<Integer, IVertex> indicesVertices) {
        IVertex u = indicesVertices.get(i);
        IVertex v = indicesVertices.get(j);

        return _graph_input.hasEdge(u, v);
    }

}
