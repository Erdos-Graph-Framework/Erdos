package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Edge.EDGE_DIRECTION;

import java.util.LinkedList;

/**
 * By relaxing the edges of a weighted dag (directed acyclic graph) G = (V, E) according to a<br/>
 * topological sort of its vertices, we can compute shortest paths from a single source<br/>
 * in Θ(V + E) time. Shortest paths are always well defined in a dag, since even if there are<br/>
 * negative-weight edges, no negative-weight cycles can exist.
 *
 * <pre>
 * {@code complexity Θ(V + E)}
 * </pre>
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class DagShortestPaths extends AbstractShortestPathAlgorithm<IDirectedGraph> {

    public DagShortestPaths(IDirectedGraph graph_input) {
        super(graph_input, "Dag Shortest Paths algorithm");
    }

    @Override
    public ShortestPathsTree applyAlgorithm() {
        if(_graph_input==null)
            throw new AlgorithmException("_graph_input==null", this);

        if(_graph_input.getGraphType()== EDGE_DIRECTION.UNDIRECTED)
            throw new AlgorithmException("graph type must be directed!", this);

        if(_startVertex==null)
            throw new AlgorithmException("startVertex==null", this);

        shortest();

        return _result_algorithm;
    }

    /*
    DAG-SHORTEST-PATHS(G, w, s)
    1  topologically sort the vertices of G
    2  INITIALIZE-SINGLE-SOURCE(G, s)
    3  for each vertex u, taken in topologically sorted order
    4       do for each vertex v ∈ Adj[u]
    5              do RELAX(u, v, w)
    */
    protected void shortest()
    {
        IVertex s = _startVertex;

        LinkedList<IVertex> topologicalSort = new TopologicalSort(_graph_input).applyAlgorithm();

        INITIALIZE_SINGLE_SOURCE(s);

        for (IVertex u : topologicalSort) {
            for (IVertex v : _graph_input.getNeighborsOf(u)) {
                RELAX(u, v);
            }
        }
        
        // create the tree

        _result_algorithm = new ShortestPathsTree(_PIE, _startVertex, _DISTANCE);
        _result_algorithm.setTag(getTag() + " result");

    }

}
