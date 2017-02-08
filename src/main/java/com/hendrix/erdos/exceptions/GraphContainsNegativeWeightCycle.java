package com.hendrix.erdos.exceptions;

import com.hendrix.erdos.algorithms.IGraphAlgorithm;

/**
 * @author Tomer Shalev
 */
public class GraphContainsNegativeWeightCycle extends AlgorithmException {

    public GraphContainsNegativeWeightCycle(IGraphAlgorithm algorithm) {
        super("Single Source Shortest Path on a graph with negative weight cycle!!", algorithm);
    }
}
