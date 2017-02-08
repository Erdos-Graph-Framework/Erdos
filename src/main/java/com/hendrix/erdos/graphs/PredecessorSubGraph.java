package com.hendrix.erdos.graphs;

import com.hendrix.erdos.types.IVertex;

import java.util.HashMap;
import java.util.Set;

/**
 * the graph induced by a predecessor list mapping
 *
 * @author Tomer Shalev
 */
public class PredecessorSubGraph extends SimpleDirectedGraph {
    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    protected HashMap<IVertex, IVertex> _PIE              = null;

    /**
     * start vertex, in case of a tree (Optional)
     */
    protected IVertex _startVertex;

    /**
     *
     * @param PIE a mapping of vertex to it's parent
     * @param startVertex start vertex, in case of a tree (Optional)
     */
    public PredecessorSubGraph(HashMap<IVertex, IVertex> PIE, IVertex startVertex) {
        this();

        commitPredecessorList(PIE, startVertex);
    }

    public PredecessorSubGraph() {
        super();
    }

    /**
     * setup the graph according to the predecessor list
     *
     * @param PIE a mapping of vertex to it's parent
     * @param startVertex start vertex, in case of a tree (Optional)
     */
    public void commitPredecessorList(HashMap<IVertex, IVertex> PIE, IVertex startVertex) {
        _PIE = PIE;
        _startVertex = startVertex;

        Set<IVertex> set_keys = _PIE.keySet();

        IVertex parent;

        for (IVertex key : set_keys) {
            parent       = _PIE.get(key);
            if(parent != null) {
                addVertex(parent);
                addVertex(key);
                addEdge(parent, key);
            }
        }

        addVertex(_startVertex);
    }

    /**
     *
     * @return the start vertex
     */
    public IVertex getStartVertex() {
        return _startVertex;
    }

}
