package com.hendrix.erdos.types;

/**
 * @author Tomer Shalev
 */
public class DirectedEdge extends Edge {
    public DirectedEdge(IVertex v1, IVertex v2) {
        super(v1, v2, EDGE_DIRECTION.DIRECTED);
    }

    public DirectedEdge(IVertex v1, IVertex v2, float weight) {
        super(v1, v2, EDGE_DIRECTION.DIRECTED, weight);
    }
}
