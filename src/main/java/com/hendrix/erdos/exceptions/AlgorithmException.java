package com.hendrix.erdos.exceptions;

import com.hendrix.erdos.algorithms.IGraphAlgorithm;

/**
 * algorithm exception
 *
 * @author Tomer Shalev
 */
public class AlgorithmException extends RuntimeException {
    public AlgorithmException(String message, IGraphAlgorithm algorithm) {
        super("Algorithm " + algorithm.getTag() + ":: message - " + message);
    }
}
