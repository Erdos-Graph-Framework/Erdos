package com.hendrix.erdos.exceptions;

import com.hendrix.erdos.algorithms.IGraphAlgorithm;

/**
 * @author Tomer Shalev
 */
public class NotDirectedAcyclicGraphException extends AlgorithmException {

    public NotDirectedAcyclicGraphException(IGraphAlgorithm algorithm) {
        super("Topological sorting on a non DAG", algorithm);
    }
}
