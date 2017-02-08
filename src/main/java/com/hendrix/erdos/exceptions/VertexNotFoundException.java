package com.hendrix.erdos.exceptions;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.types.IVertex;

public class VertexNotFoundException extends GraphException {
    public VertexNotFoundException(IVertex v, IGraph graph) {
        super(v.getId() + " not found in graph", graph);
    }
}
