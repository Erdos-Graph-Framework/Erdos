package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.Erdos;
import com.hendrix.erdos.algorithms.factories.SingleSourceShortPathFactory;
import com.hendrix.erdos.exceptions.GraphContainsNegativeWeightCycle;
import com.hendrix.erdos.exceptions.NegativeEdgeWeightException;
import com.hendrix.erdos.graphs.DirectedGraph;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.types.DirectedEdge;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Vertex;
import com.hendrix.erdos.utils.SVertexUtils;
import com.hendrix.erdos.utils.EdgeFunction;

import java.util.HashMap;

/**
 * Johnson's algorithm finds shortest paths between all pairs in {@code O(V2 lg V + V E)} time. For sparse graphs,
 * it is asymptotically better than either repeated squaring of matrices or the Floyd-Warshall algorithm.
 *
 * @see FloydWarshall
 * @see AllPairsShortPathResult
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class Johnson extends AbstractGraphAlgorithm<AllPairsShortPathResult, IDirectedGraph> {
    private HashMap<IVertex, Float> H = null;
    private EdgeFunction W_cap = null;

    public Johnson(IDirectedGraph graph_input) {
        super(graph_input, "All Pairs shortest paths Johnson Algorithm");

        H = new HashMap<>();
        W_cap = new EdgeFunction();
    }

    /**
     * compute Johnson algorithm
     *
     * @return a {@link AllPairsShortPathResult}
     *
     * @throws GraphContainsNegativeWeightCycle if graph contains negative weight cycle
     * @throws NegativeEdgeWeightException if negative weight for edges are used
     */
    @Override
    public AllPairsShortPathResult applyAlgorithm() {

        johnson();

        return _result_algorithm;
    }

    //JOHNSON(G)
    //1  compute G′, where V[G′] = V[G] ∪ {s},
    //E[G′] = E[G] ∪ {(s, v) : v ∈ V[G]}, and
    //                                w(s, v) = 0 for all v ∈ V[G]
    //2  if BELLMAN-FORD(G′, w, s) = FALSE
    //3     then print "the input graph contains a negative-weight cycle"
    //4     else for each vertex v ∈ V[G′]
    //5              do set h(v) to the value of δ(s, v)
    //computed by the Bellman-Ford algorithm
    //6          for each edge (u, v) ∈ E[G′]
    //7              do
    //8          for each vertex u ∈ V[G]
    //9              do run DIJKSTRA(G, , u) to compute  for all v ∈ V[G]
    //10                 for each vertex v ∈ V[G]
    //11                     do
    //12          return D
    private void johnson() {
        int n                       = _graph_input.numVertices();
        int n_2                     = n * n;

        float[][] D                 = new float[n][n];

        IGraph G                    = _graph_input;

        HashMap<IVertex, Integer> verticesIndices = SVertexUtils.getVerticesIndices(G);

        // compute G'

        Vertex s                    = new Vertex();
        s.setTag("s");

        DirectedGraph G_cap                = (DirectedGraph) Erdos.cloneGraphOf(_graph_input);

        G_cap.addVertex(s);

        for (IVertex v : G_cap.vertices()) {
            G_cap.addEdge(new DirectedEdge(s, v, 0f));
        }

        //

        ShortestPathsTree bellmanFord, dijkstraShortestPath;

        bellmanFord                 = SingleSourceShortPathFactory.newSingleSourceShortPath(G_cap, SingleSourceShortPathFactory.SSSPAlgorithm.BELLMAN_FORD, s, null).applyAlgorithm();

        for (IVertex v : G_cap.vertices()) {
            H.put(v, bellmanFord.distanceOf(v));
        }

        float uv_weight;

        for (Edge uv : G_cap.edges()) {
            uv_weight               = uv.getWeight();

            if(uv_weight < 0f)
                throw new NegativeEdgeWeightException(this);

            W_cap.addValue(uv, uv_weight + h(uv.getV1()) - h(uv.getV2()));
        }

        int u_index, v_index;

        for (IVertex u : G.vertices()) {
            dijkstraShortestPath    = SingleSourceShortPathFactory.newSingleSourceShortPath(_graph_input, SingleSourceShortPathFactory.SSSPAlgorithm.DIJKSTRA, u, W_cap).applyAlgorithm();
            u_index                 = verticesIndices.get(u);

            for (IVertex v : G.vertices()) {
                v_index = verticesIndices.get(v);

                D[u_index][v_index] = dijkstraShortestPath.distanceOf(v) + h(v) - h(u);
            }

        }

        _result_algorithm           = new AllPairsShortPathResult(D, null, _graph_input);
        _result_algorithm.setTag(getTag() + " result");
    }

    private float h(IVertex u) {
        return H.get(u);
    }

}
