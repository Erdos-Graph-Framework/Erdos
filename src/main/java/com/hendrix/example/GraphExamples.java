package com.hendrix.example;

import com.hendrix.erdos.Erdos;
import com.hendrix.erdos.algorithms.AbstractGraphAlgorithm;
import com.hendrix.erdos.algorithms.AbstractShortestPathAlgorithm;
import com.hendrix.erdos.algorithms.AllPairsShortPathResult;
import com.hendrix.erdos.algorithms.BFS;
import com.hendrix.erdos.algorithms.BellmanFordShortestPath;
import com.hendrix.erdos.algorithms.DFS;
import com.hendrix.erdos.algorithms.DijkstraShortestPath;
import com.hendrix.erdos.algorithms.FloydWarshall;
import com.hendrix.erdos.algorithms.MSTKruskal;
import com.hendrix.erdos.algorithms.MSTPrim;
import com.hendrix.erdos.algorithms.SCC;
import com.hendrix.erdos.algorithms.ShortestPathsTree;
import com.hendrix.erdos.algorithms.TopologicalSort;
import com.hendrix.erdos.algorithms.TransitiveClosure;
import com.hendrix.erdos.algorithms.factories.AllPairsShortPathFactory;
import com.hendrix.erdos.algorithms.factories.MinSpanTreeFactory;
import com.hendrix.erdos.algorithms.factories.MinSpanTreeFactory.MstAlgorithm;
import com.hendrix.erdos.algorithms.factories.SingleSourceShortPathFactory;
import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.AbstractGraph;
import com.hendrix.erdos.graphs.DirectedGraph;
import com.hendrix.erdos.graphs.IDirectedGraph;
import com.hendrix.erdos.graphs.IUndirectedGraph;
import com.hendrix.erdos.graphs.SimpleDirectedGraph;
import com.hendrix.erdos.graphs.SimpleGraph;
import com.hendrix.erdos.graphs.UndirectedGraph;
import com.hendrix.erdos.graphs.engines.AdjIncidenceGraphEngine;
import com.hendrix.erdos.types.DirectedEdge;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.Vertex;
import com.hendrix.erdos.utils.SMatrixUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Tomer Shalev
 */
public class GraphExamples {
    //private AbstractGraph<IGraphEngine> graph;
    //private AbstractGraph graph;
    private IVertex _v0;

    public GraphExamples() {
        AbstractGraph ag = Erdos.newGraphWithEngine(new AdjIncidenceGraphEngine(), Edge.EDGE_DIRECTION.DIRECTED, false, false);

        //generalTest();
        //mst();
        //BellmanFord();
        //DijkstraShortestPath();
        //matrixUtils();
        // floyd_Warshall_and_johnson();
        //transitiveClosure();
    }

    private void transitiveClosure() {
        SimpleDirectedGraph graph = new SimpleDirectedGraph();

        Vertex v1 = new Vertex();
        v1.setTag("1");
        Vertex v2 = new Vertex();
        v2.setTag("2");
        Vertex v3 = new Vertex();
        v3.setTag("3");
        Vertex v4 = new Vertex();
        v4.setTag("4");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        Edge e2_3     = new DirectedEdge(v2, v3, 3);
        Edge e2_4     = new DirectedEdge(v2, v4, 8);

        Edge e3_2     = new DirectedEdge(v3, v2, 8);

        Edge e4_1     = new DirectedEdge(v4, v1, 8);
        Edge e4_3     = new DirectedEdge(v4, v3, 8);

        graph.addEdge(e2_3);
        graph.addEdge(e2_4);
        graph.addEdge(e3_2);
        graph.addEdge(e4_1);
        graph.addEdge(e4_3);


        IDirectedGraph graph_res = new TransitiveClosure(graph).applyAlgorithm();

        graph_res.print();

        System.out.println("");

    }

    private void matrixUtils()
    {
        SimpleDirectedGraph graph = new SimpleDirectedGraph();

        Vertex s = new Vertex();
        s.setTag("s");
        Vertex t = new Vertex();
        t.setTag("t");
        Vertex x = new Vertex();
        x.setTag("x");
        Vertex y = new Vertex();
        y.setTag("y");
        Vertex z = new Vertex();
        z.setTag("z");

        graph.addVertex(s);
        graph.addVertex(t);
        graph.addVertex(x);
        graph.addVertex(y);
        graph.addVertex(z);

        Edge e1     = new Edge(s, t, Edge.EDGE_DIRECTION.DIRECTED, 10);
        Edge e2     = new Edge(t, x, Edge.EDGE_DIRECTION.DIRECTED, 1);
        Edge e3     = new Edge(s, y, Edge.EDGE_DIRECTION.DIRECTED, 5);
        Edge e4     = new Edge(y, z, Edge.EDGE_DIRECTION.DIRECTED, 2);
        Edge e5     = new Edge(z, x, Edge.EDGE_DIRECTION.DIRECTED, 6);
        Edge e6     = new Edge(t, y, Edge.EDGE_DIRECTION.DIRECTED, 2);
        Edge e7     = new Edge(y, t, Edge.EDGE_DIRECTION.DIRECTED, 3);
        Edge e8     = new Edge(x, z, Edge.EDGE_DIRECTION.DIRECTED, 4);
        Edge e9     = new Edge(y, x, Edge.EDGE_DIRECTION.DIRECTED, 9);
        Edge e10    = new Edge(z, s, Edge.EDGE_DIRECTION.DIRECTED, 7);

        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);
        graph.addEdge(e6);
        graph.addEdge(e7);
        graph.addEdge(e8);
        graph.addEdge(e9);
        graph.addEdge(e10);

        float[][] matrix_adjacency  = SMatrixUtils.adjacencyMatrixOf(graph, 0f, true, Float.POSITIVE_INFINITY);
        float[][] matrix_adjacency2 = SMatrixUtils.adjacencyMatrixOf(graph, 0f, true, Float.POSITIVE_INFINITY);
        float[][] matrix_incidence  = SMatrixUtils.incidenceMatrixOf(graph);

        System.out.println("");
    }

    private void DijkstraShortestPath()
    {
        SimpleDirectedGraph graph = new SimpleDirectedGraph();

        Vertex s = new Vertex();
        s.setTag("s");
        Vertex t = new Vertex();
        t.setTag("t");
        Vertex x = new Vertex();
        x.setTag("x");
        Vertex y = new Vertex();
        y.setTag("y");
        Vertex z = new Vertex();
        z.setTag("z");

        graph.addVertex(s);
        graph.addVertex(t);
        graph.addVertex(x);
        graph.addVertex(y);
        graph.addVertex(z);

        Edge e_s_t  = new DirectedEdge(s, t, 10);
        Edge e_t_x  = new DirectedEdge(t, x, 1);
        Edge e_s_y  = new DirectedEdge(s, y, 5);
        Edge e_y_z  = new DirectedEdge(y, z, 2);
        Edge e_z_x  = new DirectedEdge(z, x, 6);
        Edge e_t_y  = new DirectedEdge(t, y, 2);
        Edge e_y_t  = new DirectedEdge(y, t, 3);
        Edge e_x_z  = new DirectedEdge(x, z, 4);
        Edge e_y_x = new DirectedEdge(y, x, 9);
        Edge e_z_s = new DirectedEdge(z, s, 7);

        graph.addEdge(e_s_t);
        graph.addEdge(e_t_x);
        graph.addEdge(e_s_y);
        graph.addEdge(e_y_z);
        graph.addEdge(e_z_x);
        graph.addEdge(e_t_y);
        graph.addEdge(e_y_t);
        graph.addEdge(e_x_z);
        graph.addEdge(e_y_x);
        graph.addEdge(e_z_s);

        graph.print();

        ShortestPathsTree res = new DijkstraShortestPath(graph).setStartVertex(s).applyAlgorithm();

        res.print();

        System.out.println("");
    }

    private void BellmanFord()
    {
        SimpleDirectedGraph graph = new SimpleDirectedGraph();

        Vertex s = new Vertex("s");
        Vertex t = new Vertex("t");
        Vertex x = new Vertex("x");
        Vertex y = new Vertex("y");
        Vertex z = new Vertex("z");

        graph.addVertex(s);
        graph.addVertex(t);
        graph.addVertex(x);
        graph.addVertex(y);
        graph.addVertex(z);

        graph.addEdge(s, t, 6);
        graph.addEdge(t, x, 5);
        graph.addEdge(x, t, -2);
        graph.addEdge(s, y, 7);
        graph.addEdge(y, z, 9);
        graph.addEdge(t, y, 8);
        graph.addEdge(z, x, 7);
        graph.addEdge(t, z, -4);
        graph.addEdge(y, x, -3);
        graph.addEdge(z, s, 2);

        graph.setTag("graph");
        graph.print();

        ShortestPathsTree res = new BellmanFordShortestPath(graph).setStartVertex(s).applyAlgorithm();

        res.print();

        System.out.println("");
    }
    private void general_erdos() {
        boolean allow_self_loops = true;
        boolean allow_multi_edges = true;

        UndirectedGraph graph_undirected = Erdos.newUndirectedGraphWithEngine(new AdjIncidenceGraphEngine(), allow_self_loops, allow_multi_edges);
        DirectedGraph graph = Erdos.newGraphWithEngine(new AdjIncidenceGraphEngine(), Edge.EDGE_DIRECTION.DIRECTED, allow_self_loops, allow_multi_edges);

        graph.vertices().iterator().remove();

        Iterator<IVertex> iterator = graph.vertices().iterator();

        while (iterator.hasNext()) {
            IVertex next = iterator.next();

            if(next.getTag().equals("tag_v2"))
                iterator.remove();
        }

    }

    private void generalTest() {
        SimpleDirectedGraph graph = new SimpleDirectedGraph();
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();
        Vertex v3 = new Vertex();

        _v0 = v0;

        graph.addVertex(v0);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        graph.addEdge(v0, v1);
        graph.addEdge(v0, v2);
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v2, v0);

        graph.print();

        //
        Collection<IVertex> coll = graph.vertices();
        Collection<Edge> coll_edges = graph.edges();
        //coll.remove(v0);

        for(Iterator<Edge> iter = coll_edges.iterator(); iter.hasNext();) {
            Edge edge = iter.next();
            //iter.remove();
        }

        for(Iterator<IVertex> iter = coll.iterator(); iter.hasNext();) {
            IVertex vv = iter.next();
             //iter.remove();
        }

        for (Edge edge : graph.edges()) {
            System.out.println(edge.toString());
        }


        for (IVertex vertex : graph) {
            vertex.toString();
        }

        for (IVertex vertex : coll) {
            //System.out.println(vertex.getId());
        }


        //
        BFS.BreadthFirstTree breadthFirstTree = new BFS(graph, _v0).applyAlgorithm();

        breadthFirstTree.print();

        for (Edge edge : breadthFirstTree.edges()) {
            System.out.print(edge.toString() + ",");
        }

        DFS.DepthFirstForest depthFirstForest = new DFS(graph).applyAlgorithm();

        depthFirstForest.print();

        for (Edge edge : depthFirstForest.edges()) {
            System.out.print(edge.toString() + ",");
        }

        ArrayList<HashSet<IVertex>> hashSets = new SCC(graph).applyAlgorithm();

        for (HashSet<IVertex> hashSet : hashSets) {

            System.out.print("SCC : {");
            for (IVertex vertex : hashSet) {

                System.out.print(vertex.getId() + ",");
            }

            System.out.println("}");

        }

        try {
            LinkedList<IVertex> res_sort = new TopologicalSort(graph).applyAlgorithm();
            System.out.println("TopoSort");

            for (int i = 0; i < res_sort.size(); i++) {
                System.out.print(res_sort.get(i).getId() + ",");
            }
        }catch (AlgorithmException err){
            err.printStackTrace();
        }

    }

    private void mst()
    {
        UndirectedGraph graph = new SimpleGraph();
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();
        Vertex v3 = new Vertex();

        Edge e1 = new Edge(v0, v1, Edge.EDGE_DIRECTION.UNDIRECTED, 1);
        Edge e2 = new Edge(v0, v2, Edge.EDGE_DIRECTION.UNDIRECTED, 5);
        Edge e3 = new Edge(v1, v2, Edge.EDGE_DIRECTION.UNDIRECTED, 2);
        Edge e4 = new Edge(v2, v3, Edge.EDGE_DIRECTION.UNDIRECTED, 21);
        Edge e5 = new Edge(v1, v3, Edge.EDGE_DIRECTION.UNDIRECTED, 27);

        graph.addVertex(v0);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);

        graph.print();

        UndirectedGraph mst_kruskal = new MSTKruskal(graph).applyAlgorithm();

        mst_kruskal.print();

        UndirectedGraph mst_prim = new MSTPrim(graph).setStartVertex(v0).applyAlgorithm();

        mst_prim.print();

    }

}
