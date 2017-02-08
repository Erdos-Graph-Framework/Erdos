package com.hendrix.erdos.utils;

import com.hendrix.erdos.interfaces.IDisposable;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * external edge function helper: for weights, flows, or anything that would be mapped for edges.
 * take note, does not support multi edges.
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class EdgeFunction implements IDisposable {
    protected LinkedHashMap<String, Float> _mapEdges = null;

    public EdgeFunction() {
        _mapEdges = new LinkedHashMap<>();
    }

    /**
     * init with a {@link java.util.Collection} of edges. add all of the edges to the function
     * and use their {@link Edge#getWeight()} as the function value
     *
     * @param edges a {@link java.util.Collection} of edges
     */
    public EdgeFunction(Collection<Edge> edges) {
        this();

        addAll(edges);
    }

    /**
     * add all of the edges to the function and use their {@link Edge#getWeight()}
     * as the value
     *
     * @param edges a {@link java.util.Collection} of edges
     */
    public void addAll(Collection<Edge> edges){
        for (Edge edge : edges) {
            addValue(edge, edge.getWeight());
        }
    }

    /**
     * add a new value for edge {@code (u, v)}
     *
     * @param u     first vertex
     * @param v     second vertex
     * @param value the value
     */
    public void addValue(IVertex u, IVertex v, float value){
        String edgeDesc = Edge.getEdgeDesc(u, v, Edge.EDGE_DIRECTION.DIRECTED);

        _mapEdges.put(edgeDesc, value);
    }

    /**
     * add a new value for edge {@code e}
     *
     * @param e     the edge
     * @param value the value
     */
    public void addValue(Edge e, float value){
        addValue(e.getV1(), e.getV2(), value);
    }

    /**
     * get the function value of edge {@code (u, v)}
     *
     * @param u     first vertex
     * @param v     second vertex
     *
     * @return the function value of the edge
     *
     * @throws EdgeFunction.NoFunctionValueException if there is no value
     */
    public float valueOf(IVertex u, IVertex v){
        String edgeDesc = Edge.getEdgeDesc(u, v, Edge.EDGE_DIRECTION.DIRECTED);

        Float val = _mapEdges.get(edgeDesc);

        if(val == null)
            throw new NoFunctionValueException(u, v);

        return val;
    }

    /**
     * get the function value of edge {@code e}
     *
     * @param e     the edge
     *
     * @return the function value of the edge
     *
     * @throws EdgeFunction.NoFunctionValueException if there is no value
     */
    public float valueOf(Edge e, float value){
        return valueOf(e.getV1(), e.getV2());
    }

    /**
     * dispose the function
     */
    public void dispose() {
        _mapEdges.clear();
        _mapEdges = null;
    }

    /**
     * general {@link java.lang.RuntimeException} for when trying to query
     * a missing function value
     */
    protected class NoFunctionValueException extends RuntimeException {

        public NoFunctionValueException(Edge e) {
            super("No value for edge: " + e.getDesc());
        }

        public NoFunctionValueException(IVertex u, IVertex v) {
            super("No value for edge: " + Edge.getEdgeDesc(u, v, Edge.EDGE_DIRECTION.DIRECTED));
        }
    }
}
