package com.hendrix.erdos.types;

/**
 * @author Tomer Shalev
 */
public class UndirectedEdge extends Edge {
    public UndirectedEdge(IVertex v1, IVertex v2) {
        super(v1, v2, EDGE_DIRECTION.UNDIRECTED);
    }

    public UndirectedEdge(IVertex v1, IVertex v2, float weight) {
        super(v1, v2, EDGE_DIRECTION.UNDIRECTED, weight);
    }
}
