package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.graphs.PredecessorSubGraph;
import com.hendrix.erdos.types.IVertex;

import java.util.HashMap;

/**
 * single source shortest path tree.
 * <ul>
 *     <li/>use {@code this.distanceOf(v)} to get the shortest path
 * </ul>
 *
 * @author Tomer Shalev
 */
public class ShortestPathsTree extends PredecessorSubGraph {
    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    protected HashMap<IVertex, Float> _DISTANCE              = null;

    /**
     * @param PIE         Predecessor list of (vertex, parent-of-vertex)
     * @param startVertex the start vertex (Optional)
     * @param DISTANCE    the Distance mapping of vertices from the start vertex ==> d(s, v)
     */
    public ShortestPathsTree(HashMap<IVertex, IVertex> PIE, IVertex startVertex, HashMap<IVertex, Float> DISTANCE) {
        super(PIE, startVertex);

        _DISTANCE = DISTANCE;
    }

    public ShortestPathsTree() {
        super();
    }

    /**
     * returns the shortest distance from startVertex --> vertex
     *
     * @param vertex the vertex in question
     *
     * @return the shortest distance
     */
    public float distanceOf(IVertex vertex) {
        return _DISTANCE.get(vertex);
    }

}
