package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.utils.SMatrixUtils;
import com.hendrix.erdos.graphs.IDirectedGraph;

/**
 * Floyd Warshall algorithm for all pairs shortest paths
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class FloydWarshall extends AbstractGraphAlgorithm<AllPairsShortPathResult, IDirectedGraph> {

    public FloydWarshall(IDirectedGraph graph_input) {
        super(graph_input, "result Floyd Warshall Algorithm");
    }

    @Override
    public AllPairsShortPathResult applyAlgorithm() {

        floydWarshall();

        return _result_algorithm;
    }

    //FLOYD-WARSHALL(W)
    //1  n ← rows[W]
    //2  D(0) ← W
    //3  for k ← 1 to n
    //4       do for i ← 1 to n
    //5              do for j ← 1 to n
    //6                     do
    //7  return D(n)
    private void floydWarshall() {
        int n = _graph_input.numVertices();
        int n_2 = n * n;

        float[][] W = SMatrixUtils.adjacencyMatrixOf(_graph_input, 0f, true, Float.POSITIVE_INFINITY);
        float[][] D_prev = W;
        float[][] D = new float[n][n];

        int[][] PIE_prev = new int[n][n];
        int[][] PIE = new int[n][n];

        //HashMap<Integer, IVertex> indicesVertices = SVertexUtils.getIndicesVertices(_graph_input);

        // init PIE

        for (int ix = 0; ix < PIE_prev.length; ix++) {
            for (int jx = 0; jx < PIE_prev[ix].length; jx++) {
                if(ix == jx || W[ix][jx]==Float.POSITIVE_INFINITY)
                    PIE_prev[ix][jx] = -1;
                else
                    PIE_prev[ix][jx] = ix;//indicesVertices.get(ix);
            }
        }

        //

        //

        int k, i, j;

        for (k = 0; k < n; k++) {
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    D[i][j] = Math.min(D_prev[i][j], D_prev[i][k] + D_prev[k][j]);

                    //System.out.print(D[i][j] + ",");

                    if(D_prev[i][j] <= D_prev[i][k] + D_prev[k][j])
                        PIE[i][j] = PIE_prev[i][j];
                    else
                        PIE[i][j] = PIE_prev[k][j];
                }
                System.out.println();

            }
            SMatrixUtils.array2dCopy(D, D_prev);
            SMatrixUtils.array2dCopy(PIE, PIE_prev);
        }

        _result_algorithm = new AllPairsShortPathResult(D, PIE, _graph_input);
    }

}
