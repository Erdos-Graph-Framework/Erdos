package com.hendrix.erdos.graphs.engines;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Vertex;

import java.util.Collection;

/**
 * Abstract Graph engine.
 *
 * @author Tomer Shalev
 */
abstract public class AbstractGraphEngine implements IGraphEngine {
    /**
     * support for multi edges
     */
    //protected boolean _flagMultiEdges = false;
    /**
     * support for self loops
     */
    //protected boolean _flagSelfLoops = false;

    /**
     * the graph that this engine works for
     */
    protected IGraph _graph = null;

    /**
     *
     * @param flagMultiEdges support for multi edges
     * @param flagSelfLoops support for self loops
     */
   // protected AbstractGraphEngine(boolean flagMultiEdges, boolean flagSelfLoops) {
   //     _flagMultiEdges = flagMultiEdges;
   //     _flagSelfLoops = flagSelfLoops;
    //}

    /**
     * get the graph that this engine works for
     *
     * @return the graph
     */
    @Override
    public IGraph getGraph() {
        return _graph;
    }

    /**
     * set the graph that this engine works for.
     *
     * @param graph the graph
     */
    @Override
    public void setGraph(IGraph graph) {
        if(_graph != null)
            throw new Error("graph can only be setup once");

        _graph = graph;
    }

    /**
     * does this support multi edges
     *
     * @return true or false
     */
    public boolean isMultiEdgesSupported() {
        return getGraph().hasMultiEdges();
    }

    /**
     * does this support self loops
     *
     * @return true or false
     */
    public boolean isSelfLoopsSupported() {
        return getGraph().hasSelfLoops();
    }

    /**
     * @return <code>GraphType.DIRECTED</code> or <code>GraphType.UNDIRECTED</code>
     */
    @Override
    public Edge.EDGE_DIRECTION getGraphType() {
        return getGraph().getGraphType();
    }

    /**
     * add all the vertices and edges of the collection into the graph.
     *
     * @param vertices a collection of vertices
     * @param edges    a collection of vertices
     */
    @Override
    public void addAll(Collection<IVertex> vertices, Collection<Edge> edges) {
        if(vertices != null) {
            for (IVertex vertex : vertices) {
                addVertex(vertex);
            }
        }

        if(edges != null) {
            for (Edge edge : edges) {
                addEdge(edge);
            }
        }
    }

    /**
     * @return true if graph is empty, False - otherwise
     */
    @Override
    public boolean isEmpty() {
        return (vertices().size()==0 && edges().size()==0);
    }

    /**
     * print the graph:: vertices and edges
     */
    public void print()
    {
        System.out.println(V());
        System.out.println(E());
    }

    /**
     * @return vertex set string V={,,,}
     */
    public String V()
    {
        String res = "V = {";

        for (IVertex v : vertices()) {
            res += Vertex.toString(v) + ", ";
        }

        res += "}";

        return res;
    }

    /**
     * @return edge set string E={,,,}
     */
    public String E()
    {
        String res = "E = {";

        for (Edge e : edges()) {
            res += e.toString() + ", ";
        }

        res += "}";

        return res;
    }

    @Override
    public String toString() {
        return V() + " " + E();
    }

}
