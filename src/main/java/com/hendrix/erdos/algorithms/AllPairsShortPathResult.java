package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.interfaces.ITag;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.utils.SVertexUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * the result of any All Pairs Shortest Paths algorithm
 *
 * @see FloydWarshall
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class AllPairsShortPathResult implements ITag{
    /**
     * the Distance Matrix
     */
    private float[][] _D = null;
    /**
     * the Predecessor Matrix of paths
     */
    private int[][] _PIE = null;
    /**
     * the mapping between indices and vertices
     */
    private HashMap<Integer, IVertex> _indicesVertices = null;
    /**
     * the mapping between vertices and indices
     */
    private HashMap<IVertex, Integer> _verticesIndices = null;

    private String _tag = null;

    /**
     * instantiate a new result
     *
     * @param D                 the Distance Matrix
     * @param PIE               the Predecessor Matrix of paths
     * @param graph             the graph
     *
     * @see SVertexUtils#getIndicesVertices(IGraph)
     */
    public AllPairsShortPathResult(float[][] D, int[][] PIE, IGraph graph) {
        _D                  = D;
        _PIE                = PIE;

        _indicesVertices    = SVertexUtils.getIndicesVertices(graph);
        _verticesIndices    = SVertexUtils.getVerticesIndices(graph);
    }

    /**
     * get the shortest path distance between two vertices
     *
     * @param u the first vertex
     * @param v the second vertex
     *
     * @return the shortest path distance, or {@code Float.POSITIVE_INFINITY} if no path exists
     */
    public float shortDistanceBetween(IVertex u, IVertex v) {
        int u_index = indexOf(u);
        int v_index = indexOf(v);

        return _D[u_index][v_index];
    }

    /**
     * get a shortest path tree of a vertex
     *
     * @param u the source vertex
     *
     * @return a shortest path tree graph
     *
     * @throws java.lang.UnsupportedOperationException if the algorithm did not supply predecessor info
     */
    public ShortestPathsTree shortestPathsTreeOf(IVertex u) {
        if(_PIE == null)
            throw new UnsupportedOperationException(getTag() + " does not support this operation");

        HashMap<IVertex, Float> Distance    = new HashMap<>();
        LinkedHashMap<IVertex, IVertex> PIE = new LinkedHashMap<>();

        int u_index                         = indexOf(u);

        float[] D_u_matrix                  = _D[u_index];
        int[] PIE_u_matrix                  = _PIE[u_index];

        for (int ix = 0; ix < D_u_matrix.length; ix++) {
            Distance.put(_indicesVertices.get(ix), D_u_matrix[ix]);
        }

        for (int jx = 0; jx < PIE_u_matrix.length; jx++) {
            if(PIE_u_matrix[jx] != -1)
                PIE.put(vertexAt(jx), vertexAt(PIE_u_matrix[jx]));
        }

        return new ShortestPathsTree(PIE, u, Distance);
    }

    /**
     * get the shortest path between two vertices
     *
     * @param u the first vertex
     * @param v the second vertex
     *
     * @return an ordered list representing the shortest path between two vertices. If no path exists
     *         return {@code null}
     *
     * @throws java.lang.UnsupportedOperationException if the algorithm did not supply predecessor info
     */
    public ArrayList<IVertex> shortestPathBetween(IVertex u, IVertex v) {
        if(_PIE == null)
            throw new UnsupportedOperationException(getTag() + " does not support this operation");

        ArrayList<IVertex> path = new ArrayList<>();

        int u_index             = _verticesIndices.get(u);
        int v_index             = _verticesIndices.get(v);

        if(_D[u_index][v_index] == Float.POSITIVE_INFINITY)
            return null;

        IVertex vertex_current  = null;
        int index_next          = v_index;

        do {
            vertex_current      = vertexAt(index_next);

            path.add(vertex_current);

            index_next          = _PIE[u_index][index_next];

        } while (vertex_current!=u);

        Collections.reverse(path);

        return path;
    }

    /**
     * get vertex at specified index, uses a cached {@link SVertexUtils#getIndicesVertices(IGraph)}
     *
     * @param index the index of the vertex
     *
     * @return the corresponding vertex
     */
    protected IVertex vertexAt(int index) {
        return _indicesVertices.get(index);
    }

    /**
     * get the index of a vertex, uses a cached {@link SVertexUtils#getVerticesIndices(IGraph)}
     *
     * @param vertex the vertex
     *
     * @return the corresponding index
     */
    protected int indexOf(IVertex vertex) {
        return _verticesIndices.get(vertex);
    }

    /**
     * set the tag/description of an object
     *
     * @param tag the tag
     */
    @Override
    public void setTag(String tag) {
        _tag = tag;
    }

    /**
     * @return the tag of the object
     */
    @Override
    public String getTag() {
        return _tag;
    }

}
