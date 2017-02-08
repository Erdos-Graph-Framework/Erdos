package com.hendrix.erdos.exceptions;

import com.hendrix.erdos.graphs.IGraph;

/**
 * graph runtime exception
 *
 * @author Tomer Shalev
 */
public class GraphException extends RuntimeException {
    public GraphException(String message, IGraph graph) {
        super("Graph ID: " + graph.getId() + ", tag: " + graph.getTag() + " :: Error - " + message);
    }
}
