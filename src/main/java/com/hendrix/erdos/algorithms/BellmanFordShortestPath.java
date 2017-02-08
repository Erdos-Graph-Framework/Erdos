package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.exceptions.GraphContainsNegativeWeightCycle;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.types.IVertex;

/**
 * The Bellman-Ford algorithm solves the single-source shortest-paths<br/>
 * problem in the general case in which edge weights may be negative.
 *
 * <pre>
 * {@code complexity O(VE)}
 * </pre>
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class BellmanFordShortestPath extends AbstractShortestPathAlgorithm<IDirectedGraph> {

    public BellmanFordShortestPath(IDirectedGraph graph_input) {
        super(graph_input, "Bellman Ford algorithm");
    }

    /**
     *
     * @return
     *
     * @throws GraphContainsNegativeWeightCycle
     */
    @Override
    public ShortestPathsTree applyAlgorithm() {
        if(_graph_input==null)
            throw new AlgorithmException("_graph_input==null", this);

        if(_graph_input.getGraphType()== Edge.EDGE_DIRECTION.UNDIRECTED)
            throw new AlgorithmException("graph type must be directed!", this);

        if(_startVertex==null)
            throw new AlgorithmException("startVertex==null", this);

        shortest();

        return _result_algorithm;
    }

    /*
    BELLMAN-FORD(G, w, s)
    1  INITIALIZE-SINGLE-SOURCE(G, s)
    2  for i ← 1 to |V[G]| - 1
    3       do for each edge (u, v) ∈ E[G]
    4              do RELAX(u, v, w)
    5  for each edge (u, v) ∈ E[G]
    6       do if d[v] > d[u] + w(u, v)
    7             then return FALSE
    8  return TRUE
    */

    /**
     * @throws GraphContainsNegativeWeightCycle
     */
    protected void shortest()
    {
        IVertex s = _startVertex;
        IVertex u, v;

        INITIALIZE_SINGLE_SOURCE(s);

        for (IVertex vertex : _graph_input) {
            for (Edge edge_uv : _graph_input.edges()) {
                RELAX(edge_uv);
            }
        }

        for (Edge edge_uv : _graph_input.edges()) {
            u = edge_uv.getV1();
            v = edge_uv.getV2();

            if(_DISTANCE.get(v) > _DISTANCE.get(u) + weightOf(u, v))
                throw new GraphContainsNegativeWeightCycle(this);
        }

        // create the tree

        _result_algorithm = new ShortestPathsTree(_PIE, _startVertex, _DISTANCE);
        _result_algorithm.setTag(getTag() + " result");
    }

}
