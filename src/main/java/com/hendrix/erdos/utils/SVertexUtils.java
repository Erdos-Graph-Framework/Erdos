package com.hendrix.erdos.utils;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.types.IVertex;

import java.util.HashMap;

/**
 * vertices utilities
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class SVertexUtils {

    /**
     * compute the index of a given vertex in a graph. it is okay for occasional query.
     * for frequent queries use {@link #getVerticesIndices(IGraph)}
     *
     * @param vertex the vertex whose index is wished to be found
     * @param graph the graph in which the vertex is in
     *
     * @return the index of the vertex, or -1 if none.
     */
    static public int indexOf(IVertex vertex, IGraph graph) {
        int index = 0;
        for (IVertex v : graph.vertices()) {
            if(vertex.equals(v))
                return index;
            index++;
        }

        return -1;
    }

    /**
     * find the vertex at a specific index. it is okay for occasional query.
     * for frequent queries use {@link #getIndicesVertices(IGraph)}
     *
     * @param index the index of the vertex
     * @param graph a graph in which the vertex is in
     *
     * @return the vertex at the specified index/location, or {@code null} otherwise
     */
    static public IVertex getVertexAt(int index, IGraph graph) {
        int idx = 0;
        for (IVertex v : graph.vertices()) {
            if(idx == index)
                return v;
            idx++;
        }

        return null;
    }

    /**
     * get a complete mapping between vertices and their corresponding indices in a graph
     *
     * @param graph the graph
     *
     * @return a correspondence mapping
     *
     * @see #getIndicesVertices(IGraph)
     */
    static public HashMap<IVertex, Integer> getVerticesIndices(IGraph graph) {
        HashMap<IVertex, Integer> map = new HashMap<>();
        int idx = 0;
        for (IVertex v : graph.vertices()) {
            map.put(v, idx++);
        }

        return map;
    }

    /**
     * get a complete mapping between indices and their corresponding vertices in a graph
     *
     * @param graph the graph
     *
     * @return a correspondence mapping
     *
     * @see #getVerticesIndices(IGraph)
     */
    static public HashMap<Integer, IVertex> getIndicesVertices(IGraph graph) {
        HashMap<Integer, IVertex> map = new HashMap<>();
        int idx = 0;
        for (IVertex v : graph.vertices()) {
            map.put(idx++, v);
        }

        return map;
    }

}
