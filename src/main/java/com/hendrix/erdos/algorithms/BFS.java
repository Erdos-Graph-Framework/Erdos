package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.utils.SGraphUtils;
import com.hendrix.erdos.graphs.AbstractGraph;
import com.hendrix.erdos.algorithms.BFS.BreadthFirstTree;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author Tomer Shalev
 */
public class BFS extends AbstractGraphAlgorithm<BreadthFirstTree, IGraph>
{
    protected IVertex _sourceIVertex     = null;
    /**
     * The distance from the source s to IVertex u computed by the algorithm
     */
    protected HashMap<IVertex, Integer> _D                = null;
    /**
     * color state of vertices
     */
    protected HashMap<IVertex, SGraphUtils.COLOR> _COLOR  = null;
    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    protected LinkedHashMap<IVertex, IVertex> _PIE              = null;
    /**
     * utility queue for the algorithm
     */
    private Queue<IVertex>            _Q                = null;

    public BFS(IGraph graph_input, IVertex sourceIVertex)
    {
        super(graph_input, "Breadth First Search");

        _sourceIVertex = sourceIVertex;
        _D            = new HashMap<>();
        _COLOR        = new HashMap<>();
        _PIE          = new LinkedHashMap<>();
        _Q            = new LinkedList<>();
    }

    @Override
    public BreadthFirstTree applyAlgorithm()
    {
        if(_sourceIVertex==null || _graph_input==null)
            throw new AlgorithmException("_sourceIVertex==null || _graph_input==null", this);

        bfs(_sourceIVertex);

        _result_algorithm = new BreadthFirstTree(this);
        _result_algorithm.setTag("BFS result");

        return _result_algorithm;
    }

    @SuppressWarnings("UnusedDeclaration")
    public IVertex getSourceIVertex() {
        return _sourceIVertex;
    }
    @SuppressWarnings("UnusedDeclaration")
    public void setSourceIVertex(IVertex sourceIVertex) {
        _sourceIVertex = sourceIVertex;
    }

    @Override
    public void dispose() {
        super.dispose();

        _D.clear();
        _COLOR.clear();
        _PIE.clear();
        _Q.clear();

        _sourceIVertex = null;
        _D            = null;
        _COLOR        = null;
        _PIE          = null;
        _Q            = null;

    }

    //  BFS(G, s)
    //  1  for each IVertex u ∈ V [G] - {s}
    //  2       do color[u] ← WHITE
    //  3          d[u] ← ∞
    //  4          π[u] ← NIL
    //  5  color[s] ← GRAY
    //  6  d[s] ← 0
    //  7  π[s] ← NIL
    //  8  Q ← Ø
    //  9  ENQUEUE(Q, s)
    //  10  while Q ≠ Ø
    //  11      do u ← DEQUEUE(Q)
    //  12         for each v ∈ Adj[u]
    //  13             do if color[v] = WHITE
    //  14                   then color[v] ← GRAY
    //  15                        d[v] ← d[u] + 1
    //  16                        π[v] ← u
    //  17                        ENQUEUE(Q, v)
    //  18         color[u] ← BLACK
    //
    private void bfs(IVertex s)
    {
        Collection<IVertex> vertices = _graph_input.vertices();

        for (IVertex u : vertices) {
            if(u.equals(s))
                continue;

            _COLOR.put(u, SGraphUtils.COLOR.WHITE);
            _D.put(u, Integer.MAX_VALUE);
            _PIE.put(u, null);
        }

        _COLOR.put(s, SGraphUtils.COLOR.GREY);
        _D.put(s, 0);
        _PIE.put(s, null);

        _Q.add(s);

        IVertex u;

        Collection<IVertex> listAdj_u;

        while (!_Q.isEmpty())
        {
            u = _Q.poll();

            listAdj_u = _graph_input.getNeighborsOf(u);

            for (IVertex v : listAdj_u)
            {
                if(_COLOR.get(v).equals(SGraphUtils.COLOR.WHITE))
                {
                    _COLOR.put(v, SGraphUtils.COLOR.GREY);
                    _D.put(v, _D.get(u) + 1);
                    _PIE.put(v, u);
                    _Q.add(v);
                }

            }

            _COLOR.put(u, SGraphUtils.COLOR.BLACK);
        }

    }


    /**
     * the result of BFS based on the Predecessor Sub Graph, contains predecessor list, distances list
     * Breadth First Tree, the result of a BFS(s). represents all the
     * shortest paths from source IVertex to other vertices of the original graph.
     *
     * <ul>
     *      <li/>predecessor list
     *      <li/>starting times
     *      <li/>edge/vertex iterations are according to the discovery order
     * </ul>
     *
     * @author Tomer Shalev
     */
    public class BreadthFirstTree extends AbstractGraph
    {
        @SuppressWarnings("FieldCanBeLocal")
        private IVertex _sourceIVertex     = null;
        /**
         * The distance from the source s to IVertex u computed by the algorithm
         */
        private HashMap<IVertex, Integer> _D                = null;
        /**
         * color state of vertices
         */
        @SuppressWarnings("UnusedDeclaration")
        private HashMap<IVertex, SGraphUtils.COLOR> _COLOR  = null;
        /**
         * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
         */
        private HashMap<IVertex, IVertex> _PIE              = null;
        /**
         * init the bfs tree
         *
         * @param bfs the BFS algorithm
         */
        @SuppressWarnings("unchecked")
        private BreadthFirstTree(BFS bfs)
        {
            _sourceIVertex         = bfs._sourceIVertex;
            _D                    = (HashMap<IVertex, Integer>)bfs._D.clone();
            _PIE                  = (HashMap<IVertex, IVertex>)bfs._PIE.clone();

            Set<IVertex> set_keys = _PIE.keySet();

            IVertex parent;

            for (IVertex key : set_keys) {
                parent       = _PIE.get(key);
                if(parent != null) {
                    addVertex(parent);
                    addVertex(key);
                    addEdge(parent, key);
                }
            }

            addVertex(_sourceIVertex);
        }

        /**
         * get distance of a IVertex in this graph from the source IVertex
         * on which BFS was applied on.
         *
         * @param vertex the IVertex
         *
         * @return the distance
         */
        @SuppressWarnings("UnusedDeclaration")
        public int getDistance(IVertex vertex)
        {
            return _D.get(vertex);
        }

        //  PRINT-PATH(G, s, v)
        //  1  if v = s
        //  2     then print s
        //  3     else if π[v] = NIL
        //  4             then print "no path from" s "to" v "exists"
        //  5             else PRINT-PATH(G, s, π[v])
        //  6                  print v
        //
        /**
         * The following procedure prints out the vertices on a shortest path from {@code sVertex} to {@code eVertex},
         * assuming that BFS has already been run to compute the shortest-path tree.
         *
         * @param sVertex from IVertex
         * @param eVertex to IVertex
         */
        @SuppressWarnings("UnusedDeclaration")
        public void printPath(IVertex sVertex, IVertex eVertex)
        {
            if(sVertex == eVertex)
                printIVertex(sVertex);
            else if(_PIE.get(eVertex) == null) {
                print("no path from " + sVertex.getId() + "to" + eVertex.getId() + "exists");
            }
            else {
                printPath(sVertex, _PIE.get(eVertex));
                printIVertex(eVertex);
            }

        }

        protected void print(String msg)
        {
            System.out.print(msg);
        }

        protected void printIVertex(IVertex IVertex)
        {
            print("-" + IVertex.getId() + "-");
        }

        @Override
        public Edge.EDGE_DIRECTION getGraphType() {
            return _graph_input.getGraphType();
        }

        @Override
        public boolean hasMultiEdges() {
            return _graph_input.hasMultiEdges();
        }

        @Override
        public boolean hasSelfLoops() {
            return _graph_input.hasSelfLoops();
        }

        @Override
        public IGraphEngine graphEngineFactory() {
            return _graph_input.graphEngineFactory();
        }
    }

}
