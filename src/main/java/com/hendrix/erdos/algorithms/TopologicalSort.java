package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.exceptions.NotDirectedAcyclicGraphException;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.graphs.IDirectedGraph;

import java.util.LinkedList;

/**
 * computes a Topological Sort of input Graph G
 * @author Tomer Shalev
 */
public class TopologicalSort extends GraphAlgorithm<LinkedList<IVertex>, IDirectedGraph> {

    public TopologicalSort(IDirectedGraph graph_input) {
        super(graph_input, "TopologicalSort");
    }


    /**
     *
     * @return a {@code LinkedList} that represents the {@code topological order}.
     *
     * @throws NotDirectedAcyclicGraphException if there are cycles
     */
    @Override
    public LinkedList<IVertex> applyAlgorithm() throws NotDirectedAcyclicGraphException{
        if(_graph_input==null)
            throw new AlgorithmException("_graph_input==null", this);

        sort();

        return _result_algorithm;
    }

    protected void sort()
    {
        DFS.DepthFirstForest dff = new DFS(_graph_input).applyAlgorithm();

        if(dff.isFlagContainsCycle())
            throw new NotDirectedAcyclicGraphException(this);

        _result_algorithm = dff.get_F_SORTED();
    }

}
