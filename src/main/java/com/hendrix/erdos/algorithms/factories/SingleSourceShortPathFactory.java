package com.hendrix.erdos.algorithms.factories;

import com.hendrix.erdos.algorithms.AbstractShortestPathAlgorithm;
import com.hendrix.erdos.algorithms.BellmanFordShortestPath;
import com.hendrix.erdos.algorithms.DagShortestPaths;
import com.hendrix.erdos.algorithms.DijkstraShortestPath;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.utils.EdgeFunction;

/**
 * a helper factory for single source shortest path according to<br/>
 * implementations: {@code SSSPAlgorithm.BELLMAN_FORD, SSSPAlgorithm.DAG_SHORTEST_PATH, SSSPAlgorithm.DIJKSTRA}
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class SingleSourceShortPathFactory {
    public enum SSSPAlgorithm {
        BELLMAN_FORD, DAG_SHORTEST_PATH, DIJKSTRA
    }

    /**
     * factory for single source shortest path algorithms
     *
     * @param graph             the input graph
     * @param algorithm         the algorithm of choice: {@code SSSPAlgorithm.BELLMAN_FORD, SSSPAlgorithm.DAG_SHORTEST_PATH, SSSPAlgorithm.DIJKSTRA}
     * @param startVertex       source vertex of the algorithm
     * @param weightFunction    (Optional) a weight function, this is optional, if {@code null} is provided then {@link Edge#getWeight()} is used.
     *
     * @return the algorithm
     */
    @SuppressWarnings("UnusedDeclaration")
    static public AbstractShortestPathAlgorithm newSingleSourceShortPath(IDirectedGraph graph, SSSPAlgorithm algorithm, IVertex startVertex, EdgeFunction weightFunction) {
        switch (algorithm) {
            case BELLMAN_FORD:
                return new BellmanFordShortestPath(graph).setStartVertex(startVertex).setWeightFunction(weightFunction);
            case DAG_SHORTEST_PATH:
                return new DagShortestPaths(graph).setStartVertex(startVertex).setWeightFunction(weightFunction);
            case DIJKSTRA:
                return new DijkstraShortestPath(graph).setStartVertex(startVertex).setWeightFunction(weightFunction);
        }

        return null;
    }
    
}
