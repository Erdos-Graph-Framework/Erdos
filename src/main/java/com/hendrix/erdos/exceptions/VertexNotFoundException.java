package com.hendrix.erdos.exceptions;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.types.IVertex;

public class VertexNotFoundException extends GraphException {
    public VertexNotFoundException(IVertex v, IGraph graph) {
        super(String.format("%s (%s) not found in graph", v.getId(), v.toString()), graph);
    }
}
