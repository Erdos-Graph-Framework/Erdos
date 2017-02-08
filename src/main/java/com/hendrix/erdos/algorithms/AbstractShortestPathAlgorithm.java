package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.utils.EdgeFunction;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * abstract single source shortest path algorithm<br/>
 * <ul>
 *     <li/>supplies Shortest distance data structure map
 *     <li/>INITIALIZE_SINGLE_SOURCE method
 *     <li/>RELAX method
 * </ul>
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
abstract public class AbstractShortestPathAlgorithm<E extends IGraph> extends GraphAlgorithm<ShortestPathsTree, E> {
    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    protected LinkedHashMap<IVertex, IVertex> _PIE              = null;
    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    protected HashMap<IVertex, Float> _DISTANCE           = null;
    /**
     * the start vertex for the single source shortest path
     */
    protected IVertex _startVertex;
    /**
     * an optional weight function. if not supplied, then {@link Edge#getWeight()} will be used
     */
    protected EdgeFunction _weightFunction = null;

    public AbstractShortestPathAlgorithm(E graph_input, String tag) {
        super(graph_input, "Single Source Shortest Path:: " + tag);

        _PIE        = new LinkedHashMap<>();
        _DISTANCE   = new HashMap<>();
    }

    /**
     * set the start vertex of the algorithm
     *
     * @param vertex the starting vertex
     */
    public AbstractShortestPathAlgorithm<E> setStartVertex(IVertex vertex) {
        _startVertex = vertex;

        return this;
    }

    /**
     * an optional weight function. if not supplied, then {@link Edge#getWeight()} will be used
     *
     * @param weightFunction the optional weight function
     */
    public AbstractShortestPathAlgorithm<E> setWeightFunction(EdgeFunction weightFunction) {
        _weightFunction = weightFunction;

        return this;
    }

    abstract public ShortestPathsTree applyAlgorithm();

    /**
     * <pre>
     * {@code
     *
     * 0 INITIALIZE-SINGLE-SOURCE(G, s)
     * 1  for each vertex v ∈ V[G]
     * 2       do d[v] ← ∞
     * 3          π[v] ← NIL
     * 4  d[s] = 0
     * }
     * </pre>
     * @param s the source vertex
     */
    protected void INITIALIZE_SINGLE_SOURCE(IVertex s)
    {
        for (IVertex v : _graph_input) {
            _DISTANCE.put(v, Float.POSITIVE_INFINITY);
            _PIE.put(v, null);
        }

        _DISTANCE.put(s, 0f);
    }

    /**
     * perform edge relaxation
     *
     * <pre>
     * {@code
     *
     * 0 RELAX(u, v, w)
     * 1  if d[v] > d[u] + w(u, v)
     * 2     then d[v] ← d[u] + w(u, v)
     * 3          π[v] ← u
     * }
     * </pre>
     *
     * @param u the source vertex
     * @param v the source vertex
     */
    protected void RELAX(IVertex u, IVertex v)
    {
        if(_DISTANCE.get(v) > _DISTANCE.get(u) + weightOf(u, v)) {
            _DISTANCE.put(v, _DISTANCE.get(u) + weightOf(u, v));
            _PIE.put(v, u);
        }
    }

    /**
     * perform edge relaxation
     *
     * <pre>
     * {@code
     *
     * 0 RELAX(u, v, w)
     * 1  if d[v] > d[u] + w(u, v)
     * 2     then d[v] ← d[u] + w(u, v)
     * 3          π[v] ← u
     * }
     * </pre>
     *
     * @param uv the edge (u, v)
     */
    protected void RELAX(Edge uv)
    {
        RELAX(uv.getV1(), uv.getV2());
    }

    /**
     * a helper function for extracting weight. if a weight function was supplied with {@link #setWeightFunction(EdgeFunction)}
     * then it will be used. otherwise {@link Edge#getWeight()} will be used.
     *
     * @param u the first vertex in the edge {@code (u, v)}
     * @param u the second vertex in the edge {@code (u, v)}
     *
     * @return the weight
     *
     * @throws EdgeFunction.NoFunctionValueException if there is no value and weight function was used
     */
    protected float weightOf(IVertex u, IVertex v) {
        if(_weightFunction != null)
            return _weightFunction.valueOf(u, v);

        return _graph_input.getEdge(u, v).getWeight();
    }

}
