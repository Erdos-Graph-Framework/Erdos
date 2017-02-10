package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.IUndirectedGraph;
import com.hendrix.erdos.graphs.SimpleGraph;
import com.hendrix.erdos.graphs.UndirectedGraph;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * computes a minimum spanning tree
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class MSTPrim extends AbstractGraphAlgorithm<UndirectedGraph, IUndirectedGraph> {
    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    protected HashMap<IVertex, IVertex> _PIE              = null;
    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    protected HashMap<IVertex, Float> _KEY              = null;

    protected IVertex _startVertex;

    public MSTPrim(IUndirectedGraph graph_input) {
        super(graph_input, "MST_Prim");

        _PIE = new HashMap<>();
        _KEY = new HashMap<>();
    }

    @Override
    public UndirectedGraph applyAlgorithm() {
        if(_graph_input==null)
            throw new AlgorithmException("_graph_input==null", this);

        if(_graph_input.getGraphType()== Edge.EDGE_DIRECTION.DIRECTED)
            throw new AlgorithmException("graph type must be undirected!", this);

        mst();

        return _result_algorithm;
    }

    /**
     * set the start vertex of the algorithm
     *
     * @param vertex the starting vertex
     */
    public AbstractGraphAlgorithm<UndirectedGraph, IUndirectedGraph> setStartVertex(IVertex vertex) {
        _startVertex = vertex;

        return this;
    }

    /*
     MST-PRIM(G, w, r)
     1  for each u ∈ V [G]
     2       do key[u] ← ∞
     3          π[u] ← NIL
     4  key[r] ← 0
     5   Q ← V [G]
     6   while Q ≠ Ø
     7       do u ← EXTRACT-MIN(Q)
     8          for each v ∈ Adj[u]
     9              do if v ∈ Q and w(u, v) < key[v]
    10                    then π[v] ← u
    11                         key[v] ← w(u, v)

    */
    protected void mst()
    {
        _result_algorithm = new SimpleGraph();
        _result_algorithm.setTag(getTag() + " result");

        IVertex r = _startVertex;

        if(r==null)
            throw new AlgorithmException("startVertex==null", this);

        //HashMap<String, Float> mapEdgesWeight = new HashMap<>();

        //for (Edge edge : _graph_input.edges()) {
        //    mapEdgesWeight.put(edge.getId(), edge.getWeight());
        //}

        for (IVertex u : _graph_input.vertices()) {
            _KEY.put(u, Float.POSITIVE_INFINITY);
            _PIE.put(u, null);
        }

        _KEY.put(r, 0f);

        // a min-priority queue of keys
        PriorityQueue<IVertex> Q = new PriorityQueue<>(this.new keyComparator());
        Q.addAll(_graph_input.vertices());


        IVertex u;

        float edge_uv_weight;

        while(!Q.isEmpty()) {
            u = Q.poll();

            for (IVertex v : _graph_input.getNeighborsOf(u)) {
                //edge_uv_weight = mapEdgesWeight.get(Edge.getEdgeId(u, v, EDGE_DIRECTION.UNDIRECTED));
                edge_uv_weight = _graph_input.getEdge(u, v).getWeight();
                if(Q.contains(v) && (edge_uv_weight < _KEY.get(v))) {
                    _PIE.put(v, u);
                    _KEY.put(v, edge_uv_weight);
                    // update the queue with the updated key
                    Q.remove(v);
                    Q.add(v);
                }
            }
        }

        Set<IVertex> set_keys = _PIE.keySet();

        IVertex parent;

        for (IVertex key : set_keys) {
            parent       = _PIE.get(key);
            if(parent != null) {
                _result_algorithm.addVertex(key);
                _result_algorithm.addVertex(parent);
                _result_algorithm.addEdge(parent, key);
            }
        }

    }

    private class keyComparator implements Comparator<IVertex> {

        @Override
        public int compare(IVertex o1, IVertex o2) {
            return (int)Math.signum(_KEY.get(o1) - _KEY.get(o2));
        }
    }

}
