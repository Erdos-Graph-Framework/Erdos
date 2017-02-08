package com.hendrix.erdos.utils;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.types.Edge;

import java.util.HashMap;

/**
 * edges utilities
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class SEdgeUtils {

    /**
     * compute the index of a given edge in a graph. it is okay for occasional query.
     * for frequent queries use {@link #getEdgesIndices(IGraph)}
     *
     * @param edge  the edge whose index is wished to be found
     * @param graph the graph in which the edge is in
     *
     * @return the index of the edge, or -1 if none.
     */
    static public int indexOf(Edge edge, IGraph graph) {
        int index = 0;
        for (Edge e : graph.edges()) {
            if(edge.equals(e))
                return index;
            index++;
        }

        return -1;
    }

    /**
     * find the edge at a specific index. it is okay for occasional query.
     * for frequent queries use {@link #getIndicesEdges(IGraph)}
     *
     * @param index the index of the edge
     * @param graph a graph in which the edge is in
     *
     * @return the edge at the specified index/location, or {@code null} otherwise
     */
    static public Edge getEdgeAt(int index, IGraph graph) {
        int idx = 0;
        for (Edge e : graph.edges()) {
            if(idx == index)
                return e;
            idx++;
        }

        return null;
    }

    /**
     * get a complete mapping between edges and their corresponding indices in a graph
     *
     * @param graph the graph
     *
     * @return a correspondence mapping
     *
     * @see #getIndicesEdges(IGraph)
     */
    static public HashMap<Edge, Integer> getEdgesIndices(IGraph graph) {
        HashMap<Edge, Integer> map = new HashMap<>();
        int idx = 0;
        for (Edge e : graph.edges()) {
            map.put(e, idx++);
        }

        return map;
    }

    /**
     * get a complete mapping between indices and their corresponding edges in a graph
     *
     * @param graph the graph
     *
     * @return a correspondence mapping
     *
     * @see #getEdgesIndices(IGraph)
     */
    static public HashMap<Integer, Edge> getIndicesEdges(IGraph graph) {
        HashMap<Integer, Edge> map = new HashMap<>();
        int idx = 0;
        for (Edge e : graph.edges()) {
            map.put(idx++, e);
        }

        return map;
    }

}
