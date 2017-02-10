package com.hendrix.erdos.algorithms;

import com.hendrix.erdos.exceptions.AlgorithmException;
import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.graphs.engines.IGraphEngine;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.utils.SGraphUtils;
import com.hendrix.erdos.graphs.AbstractGraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Tomer Shalev
 */
public class DFS extends AbstractGraphAlgorithm<DFS.DepthFirstForest, IGraph>
{
    /**
     * edge classification
     */
    public enum EDGE_CLASS{
        TREE_EDGE, BACK_EDGE, FORWARD_EDGE, CROSS_EDGE
    }

    /**
     * starting time of a vertex
     */
    protected HashMap<IVertex, Integer> _D                = null;
    /**
     * finishing time of a vertex
     */
    protected HashMap<IVertex, Integer> _F                = null;
    /**
     * sorted vertices according to finishing times sorted
     * from greatest finishing time to lowest. used for topological sorting
     */
    protected LinkedList<IVertex> _F_SORTED               = null;
    /**
     * color state of verices
     */
    protected HashMap<IVertex, SGraphUtils.COLOR> _COLOR  = null;
    /**
     * the predecessor of u is stored in PIE. If u has no predecessor (for example, if u = s or u has not been discovered), then π[u] = NIL
     */
    protected LinkedHashMap<IVertex, IVertex> _PIE              = null;
    /**
     * edge classification map
     */
    protected HashMap<String, EDGE_CLASS> _mapEdgeClass   = null;
    /**
     * utility queue for the algorithm
     */
    private int            time                         = 0;

    /**
     * a flag indication of whether to classify edges
     */
    protected boolean _flagClassifyEdges                  = true;
    /**
     * a flag indication of whether a back edge was found
     */
    protected boolean _flagContainsCycle                  = false;


    /**
     * indicates the algorithm will run on a subset of V,
     * that the user specified. good for running the algorithm
     * on a singleton. and also good for handing the algorithm a permutation
     * of the vertices, which is essential for some graph algorithms such as SCC
     */
    private Collection<IVertex>  _inputSubset           = null;

    public DFS(IGraph graph_input)
    {
        super(graph_input, "Depth First Search");

        _D              = new HashMap<>();
        _COLOR          = new HashMap<>();
        _PIE            = new LinkedHashMap<>();
        _F_SORTED       = new LinkedList<>();
        _F              = new HashMap<>();
        _mapEdgeClass   = new HashMap<>();
    }

    /**
     * a flag indication of whether to classify edges
     */
    @SuppressWarnings("UnusedDeclaration")
    public boolean isFlagClassifyEdges() { return _flagClassifyEdges;}
    @SuppressWarnings("UnusedDeclaration")
    public DFS setFlagClassifyEdges(boolean flagClassifyEdges) {
        _flagClassifyEdges = flagClassifyEdges;

        return this;
    }

    @Override
    public DepthFirstForest applyAlgorithm()
    {
        if(_graph_input==null)
            throw new AlgorithmException("_graph_input==null", this);

        dfs();

        _result_algorithm = new DepthFirstForest(this);
        _result_algorithm.setTag("DFS result");

        return _result_algorithm;
    }

    @Override
    public void dispose() {
        super.dispose();

        _D.clear();
        _F.clear();
        _COLOR.clear();
        _PIE.clear();

        _D            = null;
        _F            = null;
        _F_SORTED     = null;
        _COLOR        = null;
        _PIE          = null;
    }

    /**
     * indicates the algorithm will run on a subset of V,
     * that the user specified. good for running the algorithm
     * on a singleton. and also good for handing the algorithm a permutation
     * of the vertices, which is essential for some graph algorithms such as SCC
     * @throws AlgorithmException
     */
    public DFS setInputSubset(Collection<IVertex> subset)
    {
        _inputSubset  = subset;

        return this;
    }

    //  DFS(G)
    //  1  for each vertex u ∈ V [G]
    //  2       do color[u] ← WHITE
    //  3          π[u] ← NIL
    //  4  time ← 0
    //  5  for each vertex u ∈ V [G]
    //  6       do if color[u] = WHITE
    //  7             then DFS-VISIT(u)
    //
    private void dfs()
    {
        validateInputSubset();

        Collection<IVertex> vertices = (_inputSubset == null) ? _graph_input.vertices() : _inputSubset;

        for (IVertex vertex : vertices) {
            _COLOR.put(vertex, SGraphUtils.COLOR.WHITE);
            _PIE.put(vertex, null);
        }

        time  = 0;

        for (IVertex vertex : vertices) {
            if(_COLOR.get(vertex).equals(SGraphUtils.COLOR.WHITE))
                DFS_VISIT(vertex);
        }

    }

    //  DFS-VISIT(u)
    //  1  color[u] ← GRAY     ▹White vertex u has just been discovered.
    //  2  time ← time +1
    //  3  d[u] time
    //  4  for each v ∈ Adj[u]  ▹Explore edge(u, v).
    //  5       do if color[v] = WHITE
    //  6             then π[v] ← u
    //  7                  DFS-VISIT(v)
    //  8  color[u] BLACK      ▹ Blacken u; it is finished.
    //  9  f [u] ▹ time ← time +1
    private void DFS_VISIT(IVertex u)
    {
        _COLOR.put(u, SGraphUtils.COLOR.GREY);

        time                        = time + 1;

        _D.put(u, time);

        Collection<IVertex> adjList_u  = _graph_input.getNeighborsOf(u);

        for (IVertex v : adjList_u) {
            if(_flagClassifyEdges)
                classifyEdge(u, v);
            if(_COLOR.get(v).equals(SGraphUtils.COLOR.WHITE)) {
                _PIE.put(v, u);
                DFS_VISIT(v);
            }

        }

        _COLOR.put(u, SGraphUtils.COLOR.BLACK);

        time                    = time + 1;

        _F.put(u, time);
        _F_SORTED.addFirst(u);

    }

    /**
     * classify edge (u, v)
     */
    private void classifyEdge(IVertex u, IVertex v)
    {
        if(_COLOR.get(v).equals(SGraphUtils.COLOR.WHITE)) {
            _mapEdgeClass.put(Edge.getEdgeDesc(u, v, _graph_input.getGraphType()), EDGE_CLASS.TREE_EDGE);
        }
        else if(_COLOR.get(v).equals(SGraphUtils.COLOR.GREY)) {
            _mapEdgeClass.put(Edge.getEdgeDesc(u, v, _graph_input.getGraphType()), EDGE_CLASS.BACK_EDGE);
            _flagContainsCycle = true;
        }
        else if(_COLOR.get(v).equals(SGraphUtils.COLOR.BLACK)) {
            if(_D.get(u) < _D.get(v))
                _mapEdgeClass.put(Edge.getEdgeDesc(u, v, _graph_input.getGraphType()), EDGE_CLASS.FORWARD_EDGE);
            else if(_D.get(u) > _D.get(v))
                _mapEdgeClass.put(Edge.getEdgeDesc(u, v, _graph_input.getGraphType()), EDGE_CLASS.CROSS_EDGE);
        }

    }

    private void validateInputSubset()
    {
        if(_graph_input == null)
            throw new AlgorithmException("_graph_input == null", this);

        Collection<IVertex> vertices = _graph_input.vertices();

        if(_inputSubset == null)
            return;

        for (IVertex vertex : _inputSubset) {
            if(!vertices.contains(vertex))
                throw new AlgorithmException("_inputSubset is not a true subset!!!", this);
        }

    }




    /**
     * the result of DFS based on the Predecessor Sub Graph, contains:
     *
     * <ul><li/>predecessor list
     * <li/>starting times
     * <li/>finishing times
     * <li/>edge classification queries
     * <li/>edge/vertex iterations are according to the discovery order
     * </ul>
     * Depth First forest
     * the result of a DFS(G).
     *
     * @author Tomer Shalev
     */
    public class DepthFirstForest extends AbstractGraph
    {
        /**
         * starting time of a vertex
         */
        private HashMap<IVertex, Integer> _D                = null;
        /**
         * finishing time of a vertex
         */
        private HashMap<IVertex, Integer> _F                = null;
        /**
         * sorted vertices according to finishing times sorted
         * from greatest finishing time to lowest. used for topological sorting
         */
        private LinkedList<IVertex> _F_SORTED               = null;
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
         * edge classification map
         */
        private HashMap<String, EDGE_CLASS> _mapEdgeClass   = null;
        /**
         * a flag indication of whether a back edge was found
         */
        private boolean _flagContainsCycle                  = true;

        @SuppressWarnings("unchecked")
        private DepthFirstForest(DFS dfs) {
            _PIE                                  = (HashMap<IVertex, IVertex>) dfs._PIE.clone();
            _D                                    = (HashMap<IVertex, Integer>) dfs._D.clone();
            _F                                    = (HashMap<IVertex, Integer>) dfs._F.clone();
            _F_SORTED                             = (LinkedList<IVertex>) dfs._F_SORTED.clone();
            _mapEdgeClass                         = dfs._mapEdgeClass;
            _flagContainsCycle                    = dfs._flagContainsCycle;

            Set<IVertex> setOriginalGraphVertices = _PIE.keySet();

            for (IVertex vertex : setOriginalGraphVertices) {
                addVertex(vertex);
            }

            IVertex vertexParent;

            for (IVertex vertex : setOriginalGraphVertices) {
                vertexParent                        = _PIE.get(vertex);
                if(vertexParent != null)
                    addEdge(vertexParent, vertex);
            }

        }

        @Override
        public Edge.EDGE_DIRECTION getGraphType() {
            return getInputGraph().getGraphType();
        }

        @Override
        public boolean hasMultiEdges() {
            return getInputGraph().hasMultiEdges();
        }

        @Override
        public boolean hasSelfLoops() {
            return getInputGraph().hasSelfLoops();
        }

        @Override
        public IGraphEngine graphEngineFactory() {
            return getInputGraph().graphEngineFactory();
        }

        @SuppressWarnings("UnusedDeclaration")
        public HashMap<IVertex, Integer> getD() {
            return _D;
        }

        /**
         * the predecessor list
         */
        public HashMap<IVertex, IVertex> getPIE() {
            return _PIE;
        }

        @SuppressWarnings("UnusedDeclaration")
        public HashMap<IVertex, Integer> getF() {
            return _F;
        }

        /**
         * sorted vertices according to finishing times sorted
         * from greatest finishing time to lowest(descending). used for topological sorting
         */
        public LinkedList<IVertex> get_F_SORTED() {
            return _F_SORTED;
        }

        /**
         * does contain cycle, only valid if DFS was started with classification
         */
        public boolean isFlagContainsCycle() {
            return _flagContainsCycle && (_mapEdgeClass.size()>0);
        }

        @SuppressWarnings("UnusedDeclaration")
        public EDGE_CLASS getEdgeClass(Edge edge) {
            return getEdgeClassById(edge.getId());
        }
        @SuppressWarnings("UnusedDeclaration")
        public EDGE_CLASS getEdgeClass(IVertex v1, IVertex v2) {
            return getEdgeClassById(Edge.getEdgeDesc(v1, v2, getGraphType()));
        }
        protected EDGE_CLASS getEdgeClassById(String id) {
            return _mapEdgeClass.get(id);
        }
    }

}
