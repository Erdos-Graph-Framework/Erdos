package com.hendrix.erdos.utils;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;

import java.util.Collection;
import java.util.HashMap;

/**
 * Graph matrix utilities
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class SMatrixUtils {

    /**
     * compute the adjacency matrix of a graph
     *
     * @param graph         the graph
     * @param fillWeights   if {@code true} then values will be replaces with weights.
     *
     * @return the adjacency matrix of the graph
     */
    static public float[][] adjacencyMatrixOf(IGraph graph, boolean fillWeights) {
        return adjacencyMatrixOf(graph, 0f, fillWeights, 0f);
    }

    /**
     * compute the adjacency matrix of a graph with custom conditions
     *
     * @param graph             the graph
     * @param condition1_value  the value to fill if {@code e{i, j}} and {@code i==j}
     * @param fillWeights       if {@code true} then values will be replaced with weights, other wise 1
     * @param condition2_value  the value to fill if {@code e{i, j}} does not belong to E and {@code i!=j}
     *
     * @return the custom adjacency matrix of the graph
     */
    static public float[][] adjacencyMatrixOf(IGraph graph, float condition1_value, boolean fillWeights, float condition2_value) {
        int numVertices                     = graph.numVertices();
        Edge.EDGE_DIRECTION graphType       = graph.getGraphType();

        float[][] matrix                    = new float[numVertices][numVertices];

        // init

        // condition 2 => the value to fill if {@code e{i, j}} does not belong to E and {@code i!=j}

        for (int ma = 0; ma < matrix.length; ma++) {
            for (int mb = 0; mb < matrix[ma].length; mb++) {
                matrix[ma][mb]              = condition2_value;
            }
        }

        // condition 1 => the value to fill if {@code e{i, j}} and {@code i==j}

        for (int ma = 0; ma < matrix.length; ma++) {
            matrix[ma][ma]                  = condition1_value;
        }

        // fill weights or presence

        HashMap<IVertex, Integer> verticesIndices = SVertexUtils.getVerticesIndices(graph);

        IVertex v1, v2;
        int v1_index, v2_index;

        for (Edge edge : graph.edges()) {
            v1                              = edge.getV1();
            v2                              = edge.getV2();

            v1_index                        = verticesIndices.get(v1);
            v2_index                        = verticesIndices.get(v2);

            matrix[v1_index][v2_index]      = fillWeights ? edge.getWeight() : 1;

            if(graphType == Edge.EDGE_DIRECTION.UNDIRECTED)
                matrix[v2_index][v1_index]  = matrix[v1_index][v2_index];
        }

        return matrix;
    }

    /**
     * return the incidence matrix of a graph {@code G=<V, E>}.<br/>
     * i.e, a {@code |V|x|E|} matrix {@code M} where, {@code M(i,j)=1} if vertex with index {@code i} is incident to an edge with <br/>
     * index {@code j}, otherwise {@code M(i,j)=0}.
     *
     * @param graph the graph whose incidence matrix is to be computed
     *
     * @return the incidence matrix
     *
     * @see SEdgeUtils#indexOf(Edge, IGraph)
     * @see SEdgeUtils#getEdgesIndices(IGraph)
     * @see SEdgeUtils#getIndicesEdges(IGraph)
     * @see SEdgeUtils#getEdgeAt(int, IGraph)
     * @see SVertexUtils#getIndicesVertices(IGraph)
     * @see SVertexUtils#getVertexAt(int, IGraph)
     * @see SVertexUtils#getIndicesVertices(IGraph)
     * @see SVertexUtils#getVerticesIndices(IGraph)
     *
     */
    static public float[][] incidenceMatrixOf(IGraph graph) {
        int numVertices                             = graph.numVertices();
        int numEdges                                = graph.numEdges();

        float[][] matrix                            = new float[numVertices][numEdges];

        HashMap<Integer, IVertex> indicesVertices   = SVertexUtils.getIndicesVertices(graph);
        HashMap<Integer, Edge> indicesEdges         = SEdgeUtils.getIndicesEdges(graph);

        Collection<Edge> incidenceInListOf, incidenceOutListOf;

        Edge edge;

        for (int v_index = 0; v_index < matrix.length; v_index++) {
            for (int e_index = 0; e_index < matrix[v_index].length; e_index++) {
                incidenceInListOf                   = graph.getIncidenceInListOf(indicesVertices.get(v_index));
                incidenceOutListOf                  = graph.getIncidenceOutListOf(indicesVertices.get(v_index));
                edge                                = indicesEdges.get(e_index);

                if(incidenceInListOf.contains(edge) || incidenceOutListOf.contains(edge))
                    matrix[v_index][e_index]        = 1;
            }
        }

        return matrix;
    }

    /**
     * deep copy a 2D array
     *
     * @param src       the source array
     * @param dest      the destination array
     */
    static public void array2dCopy(Object[][] src,
                                   Object[][] dest) {
        int n = src.length;

        for (int i = 0; i < n; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, n);
        }

    }

    /**
     * deep copy a 2D array
     *
     * @param src       the source array
     * @param dest      the destination array
     */
    static public void array2dCopy(float[][] src,
                                   float[][] dest) {
        int n = src.length;

        for (int i = 0; i < n; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, n);
        }

    }

    /**
     * deep copy a 2D array
     *
     * @param src       the source array
     * @param dest      the destination array
     */
    static public void array2dCopy(int[][] src,
                                   int[][] dest) {
        int n = src.length;

        for (int i = 0; i < n; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, n);
        }

    }

}
