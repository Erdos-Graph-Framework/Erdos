package com.hendrix.test;

import com.hendrix.erdos.algorithms.FloydWarshall;
import com.hendrix.erdos.algorithms.factories.AllPairsShortPathFactory;
import com.hendrix.erdos.exceptions.VertexNotFoundException;
import com.hendrix.erdos.graphs.SimpleDirectedGraph;
import com.hendrix.erdos.types.DirectedEdge;
import com.hendrix.erdos.types.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleDirectedGraphTest {

    @Test
    public void validateVertexBelongsToGraph() {
        var graph = new SimpleDirectedGraph();
        var first = new Vertex<String>("first");
        var second = new Vertex<String>("second");
        try {
            graph.addEdge(first, second);
            fail("Should receive a vertex not found exception");
        } catch (VertexNotFoundException e) {
            assertTrue(e.getMessage().contains("(first) not found in graph"), e.getMessage());
        }
        graph.addVertex(first);
        try {
            graph.addEdge(first, second);
            fail("Should receive a vertex not found exception");
        } catch (VertexNotFoundException e) {
            assertTrue(e.getMessage().contains("(second) not found in graph"), e.getMessage());
        }

    }


    @Test
    public void testSimpleDirectedGraph() {
        // TODO this might be a good thing to load from JSON or XML
        SimpleDirectedGraph graph = new SimpleDirectedGraph();

        var v1 = new Vertex<String>();
        v1.setTag("1");
        var v2 = new Vertex<String>();
        v2.setTag("2");
        var v3 = new Vertex<String>();
        v3.setTag("3");
        var v4 = new Vertex<String>();
        v4.setTag("4");
        var v5 = new Vertex<String>();
        v5.setTag("5");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);

        var e1_2 = new DirectedEdge(v1, v2, 3);
        var e1_3 = new DirectedEdge(v1, v3, 8);
        var e1_5 = new DirectedEdge(v1, v5, 4);

        var e2_4 = new DirectedEdge(v2, v4, 1);
        var e2_5 = new DirectedEdge(v2, v5, 7);

        var e3_2 = new DirectedEdge(v3, v2, 4);

        var e4_1 = new DirectedEdge(v4, v1, 2);
        var e4_3 = new DirectedEdge(v4, v3, 5);

        var e5_4 = new DirectedEdge(v5, v4, 6);

        graph.addEdge(e1_2);
        graph.addEdge(e1_3);
        graph.addEdge(e1_5);
        graph.addEdge(e2_4);
        graph.addEdge(e2_5);
        graph.addEdge(e3_2);
        graph.addEdge(e4_1);
        graph.addEdge(e4_3);
        graph.addEdge(e5_4);

        // TODO what should this value be to be correct?
        var pathResult = new FloydWarshall(graph).applyAlgorithm();

        // TODO what should THIS be?
        pathResult.shortestPathsTreeOf(v1).print();

        // System.out.println("path result:" + pathResult.shortestPathBetween(v5, v2).toString());

        // TODO why is this here?
        var johnsonResult = AllPairsShortPathFactory.newAllPairsShortPath(graph, AllPairsShortPathFactory.APSPAlgorithm.Johnson).applyAlgorithm();
    }
}
