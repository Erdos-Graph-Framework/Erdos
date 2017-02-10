package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.IGraph;

/**
 * abstract graph algorithm
 * @param <T> the return type of the algorithm
 * @param <E> the input type of the algorithm
 */
abstract public class AbstractGraphAlgorithm<T, E extends IGraph> implements IGraphAlgorithm<T, E>
{
    protected E         _graph_input       = null;
    protected T         _result_algorithm  = null;
    protected String    _tag               = "empty";

    public AbstractGraphAlgorithm(E graph_input, String tag) {
        _graph_input  = graph_input;
        _tag          = tag;
    }

    public AbstractGraphAlgorithm(E graph_input) {
        this(graph_input, null);
    }

    public AbstractGraphAlgorithm(String tag) {
        this(null, tag);
    }

    public AbstractGraphAlgorithm() {}

        @Override
    public void setInputGraph(E graph) {
        if(_graph_input != null)
            throw new AlgorithmException("InputGraph can only be set once!!", this);

        _graph_input = graph;
    }

    @Override
    public E getInputGraph() {
        return _graph_input;
    }

    @Override
    abstract public T applyAlgorithm();

    @Override
    public T getResult() {
        return _result_algorithm;
    }

    @Override
    public void dispose() {
        _graph_input      = null;
        _result_algorithm = null;
    }

    @Override
    public void setTag(String tag) {
        _tag = tag;
    }

    @Override
    public String getTag() {
        return _tag;
    }

}
