package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Edge.EDGE_DIRECTION;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Dijkstra's algorithm solves the single-source shortest-paths problem on a weighted, directed<br/>
 * graph G = (V, E) for the case in which all edge weights are non negative. In this section, therefore, we <br/>
 * assume that w(u, v) ≥ 0 for each edge (u, v) ∈ E. As we shall see, with a good implementation, the <br/>
 * running time of Dijkstra's algorithm is lower than that of the Bellman-Ford algorithm.<br/>
 *
 * <pre>
 * {@code complexity O(VE)}
 * </pre>
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class DijkstraShortestPath extends AbstractShortestPathAlgorithm<IDirectedGraph> {

    public DijkstraShortestPath(IDirectedGraph graph_input) {
        super(graph_input, "Dijkstra's algorithm");
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
    DIJKSTRA(G, w, s)
    1  INITIALIZE-SINGLE-SOURCE(G, s)
    2  S ← Ø
    3  Q ← V[G]
    4  while Q ≠ Ø
    5      do u ← EXTRACT-MIN(Q)
    6         S ← S ∪{u}
    7         for each vertex v ∈ Adj[u]
    8             do RELAX(u, v, w)
    */
    protected void shortest()
    {
        IVertex s = _startVertex;
        IVertex u;

        INITIALIZE_SINGLE_SOURCE(s);

        HashSet<IVertex> S = new HashSet<>();

        PriorityQueue<IVertex> Q = new PriorityQueue<>(_graph_input.vertices().size(), this.new keyComparator());
        Q.addAll(_graph_input.vertices());

        while (!Q.isEmpty()) {
            u = Q.poll();
            S.add(u);

            for (IVertex v : _graph_input.getNeighborsOf(u)) {
                RELAX(u, v);
            }
        }

        // create the tree

        _result_algorithm = new ShortestPathsTree(_PIE, _startVertex, _DISTANCE);
        _result_algorithm.setTag(getTag() + " result");
    }

    /**
     * minimum priority comparator based on vertices distance from startVertex
     */
    private class keyComparator implements Comparator<IVertex> {

        @Override
        public int compare(IVertex o1, IVertex o2) {
            return (int)Math.signum(_DISTANCE.get(o1) - _DISTANCE.get(o2));
        }
    }

}
