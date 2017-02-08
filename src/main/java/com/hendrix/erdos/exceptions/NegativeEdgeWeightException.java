package com.hendrix.erdos.exceptions;

import com.hendrix.erdos.algorithms.IGraphAlgorithm;

/**
 * @author Tomer Shalev
 */
public class NegativeEdgeWeightException extends AlgorithmException {
    public NegativeEdgeWeightException(IGraphAlgorithm algorithm) {
        super("algorithm does not support negative edge weights!! ", algorithm);
    }
}
