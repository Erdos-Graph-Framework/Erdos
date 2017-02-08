package com.hendrix.erdos.algorithms;

import com.hendrix.collection.NaiveUnionFind;
import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.DirectedGraph;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * computes the Strongly Connected Components of input Graph G
 * @author Tomer Shalev
 */
public class SCC extends GraphAlgorithm<ArrayList<HashSet<IVertex>>, IDirectedGraph> {

    public SCC(IDirectedGraph graph_input) {
        super(graph_input, "Strongly Connected Components");
    }

    @Override
    public ArrayList<HashSet<IVertex>> applyAlgorithm() {
        if(_graph_input==null)
            throw new AlgorithmException("_graph_input==null", this);

        if(_graph_input.getGraphType()!= Edge.EDGE_DIRECTION.DIRECTED)
            throw new AlgorithmException("graph must be directed", this);

        scc();

        return _result_algorithm;
    }

    @SuppressWarnings("unchecked")
    protected void scc()
    {
        if(_graph_input.isEmpty())
            return;
        // fill the graph V

        //Collection<Vertex> vertices = _graph_input.vertices().values();

        // DFS(G)
        DFS.DepthFirstForest dff            = new DFS(_graph_input).applyAlgorithm();

        // get finishing times f[u] for each vertex u in descending order
        LinkedList<IVertex> f_sorted        = dff.get_F_SORTED();

        // compute transpose of G
        DirectedGraph G_Transpose                   = new Transpose(_graph_input).applyAlgorithm();

        // DFS(G_Transpose) but in the main loop of DFS, consider the vertices in order of decreasing f[u]
        // the output is a forset of trees, and each tree's vertices are a strongly connected component
        // of the original graph

        DFS.DepthFirstForest dff_gt         = new DFS(G_Transpose).setInputSubset(f_sorted).applyAlgorithm();

        // identify components - use CC here

        HashMap<IVertex, IVertex> pie       = dff_gt.getPIE();

        // output connected components

        NaiveUnionFind<IVertex> unionFind   = new NaiveUnionFind<>();

        for (IVertex vertex : pie.keySet()) {
            unionFind.MAKESET(vertex);
        }

        for (IVertex vertex : pie.keySet()) {
            if(pie.get(vertex) == null)
                continue;
            unionFind.UNION(vertex, pie.get(vertex));
        }

        _result_algorithm                   = unionFind.getDisjointSets();
    }

}
