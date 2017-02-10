package com.hendrix.erdos.graphs.engines;

import com.hendrix.erdos.graphs.IGraph;
import com.hendrix.erdos.exceptions.VertexNotFoundException;
import com.hendrix.erdos.types.IVertex;
import com.hendrix.erdos.types.Edge;
import com.hendrix.erdos.types.Edge.*;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * a graph engine implementation that uses both <b>Adjacency list</b> and <b>Incidence list</b><br/>
 * designed for optimal complexity for algorithms that require more than a moderate edge<br/>
 * queries. it does consume more memory as a consequence. can handle both directed and undirected edges.
 * todo: add multi edge capabilities and self loops
 *
 * @author Tomer Shalev
 * @see IGraph
 */
public class AdjIncidenceGraphEngine extends AbstractGraphEngine {
    /**
     * the Vertices list of the Graph G=(V, E)
     */
    protected LinkedHashMap<String, IVertex> _vertices = null;

    /**
     * the Vertices Adj list
     */
    protected HashMap<IVertex, HashSet<IVertex>> _colAdjLists = null;
    /**
     * the edges map
     */
    protected LinkedHashSet<Edge> _setEdges = null;
    /**
     * the presence map
     */
    protected HashMap<String, ArrayList<Edge>> _mapPresenceEdges = null;
    /**
     * the incidence in list
     */
    protected HashMap<IVertex, HashSet<Edge>> _inEdges = null;
    /**
     * the incidence out list
     */
    protected HashMap<IVertex, HashSet<Edge>> _outEdges = null;

    /**
     *
     */
    public AdjIncidenceGraphEngine() {
        _vertices           = new LinkedHashMap<>();
        _colAdjLists        = new HashMap<>();
        _setEdges           = new LinkedHashSet<>();
        _mapPresenceEdges   = new HashMap<>();
        _inEdges            = new HashMap<>();
        _outEdges           = new HashMap<>();
    }

    /**
     * set the graph that this engine works for.
     *
     * @param graph the graph
     */
    @Override
    public void setGraph(IGraph graph) {
        super.setGraph(graph);

        if (getGraphType() == EDGE_DIRECTION.UNDIRECTED)
            _inEdges = _outEdges;
    }


    /**
     * iterator over the vertices of the graph
     * example: <br/>
     * <code>
     * <pre/>
     * for (IVertex vertex : graph) {
     * }
     * </code>
     *
     * @return the vertices iterator
     */
    @Override
    public Iterator<IVertex> iterator() {
        return new VerticesIterator();
    }


    /**
     * {@inheritDoc}
     * <p/>
     * changes that are made to this collection will reflect
     * changes to the edges of the graph (removal wise)
     */
    @Override
    public java.util.Collection<IVertex> vertices() {
        return (verticesView == null) ? verticesView = new VerticesView() : verticesView;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * changes that are made to this collection will reflect
     * changes to the vertices of the graph
     */
    @Override
    public Collection<Edge> edges() {
        return (edgesView == null) ? edgesView = new EdgesView() : edgesView;
    }

    /**
     * get the accessible vertex neighbors of vertex v1
     *
     * @param vertex the vertex
     * @return the neighbors
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<IVertex> getNeighborsOf(IVertex vertex) {
        return new VertexAdjView(vertex);
    }

    /**
     * get the list of incident edges that go out from vertex
     *
     * @param vertex the vertex
     * @return the out incidence list of vertex
     */
    @Override
    public Collection<Edge> getIncidenceOutListOf(IVertex vertex) {
        return new IncidenceOutView(vertex);
    }

    /**
     * get the list of incident edges that go into the vertex
     *
     * @param vertex the vertex
     * @return the in incidence list of vertex
     */
    @Override
    public Collection<Edge> getIncidenceInListOf(IVertex vertex) {
        return new IncidenceInView(vertex);
    }

    /**
     * check whether <code>vertex</code> belongs to the graph's vertex list
     *
     * @param vertex the vertex in question
     * @return true if graph contains this vertex
     */
    @Override
    public boolean hasVertex(IVertex vertex) {
        return _vertices.containsValue(vertex);
    }

    /**
     * add <code>vertex</code> into the graph
     *
     * @return true if success
     */
    @Override
    public boolean addVertex(IVertex vertex) {
        if (hasVertex(vertex))
            return false;

        _vertices.put(vertex.getId(), vertex);

        if (_colAdjLists.get(vertex) == null)
            _colAdjLists.put(vertex, new HashSet<IVertex>());

        _inEdges.put(vertex, new HashSet<Edge>());
        _outEdges.put(vertex, new HashSet<Edge>());

        return true;
    }

    /**
     * remove <code>vertex</code> from the graph
     *
     * @return true if success
     */
    @Override
    public boolean removeVertex(IVertex vertex) {
        internal_removeEdgeNeighborsOfVertex(vertex);

        return _vertices.values().remove(vertex);
    }

    /**
     * responsive/modifiable collection views for iteration outside
     * the class.
     */
    transient volatile Collection<IVertex> verticesView;
    transient volatile Collection<Edge> edgesView;

    /**
     * vertices view
     */
    public class VerticesView extends AbstractCollection<IVertex> {
        private final Collection<IVertex> _values;

        public VerticesView() {
            _values = _vertices.values();
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public Iterator<IVertex> iterator() {
            return new VerticesIterator();
        }

        public int size() {
            return _values.size();
        }

        public boolean contains(Object o) {
            return _values.contains(o);
        }

        public boolean add(IVertex vertex) {
            return addVertex(vertex);
        }

        public boolean remove(Object o) {
            return removeVertex((IVertex) o);
        }
    }

    protected class VerticesIterator implements Iterator<IVertex> {
        private Iterator<IVertex> _iteratorParent;
        private IVertex _current;

        public VerticesIterator() {
            _iteratorParent = _vertices.values().iterator();
            _current = null;
        }

        @Override
        public boolean hasNext() {
            return _iteratorParent.hasNext();
        }

        @Override
        public IVertex next() {
            return _current = _iteratorParent.next();
        }

        @Override
        public void remove() {
            internal_removeEdgeNeighborsOfVertex(_current);
            _iteratorParent.remove();
        }
    }

    //

    /**
     * edges view
     */
    public class EdgesView extends AbstractCollection<Edge> {
        private final Collection<Edge> _values;

        public EdgesView() {
            _values = _setEdges;
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public Iterator<Edge> iterator() {
            return new EdgesIterator();
        }

        public int size() {
            return _values.size();
        }

        public boolean contains(Object o) {
            return _values.contains(o);
        }

        public boolean add(Edge edge) {
            return addEdge(edge) != null;
        }

        public boolean remove(Object o) {
            return removeEdge((Edge) o) != null;
        }
    }

    protected class EdgesIterator implements Iterator<Edge> {
        private Iterator<Edge> _iterParent;
        private Edge _current;

        public EdgesIterator() {
            _iterParent = _setEdges.iterator();
            _current = null;
        }

        @Override
        public boolean hasNext() {
            return _iterParent.hasNext();
        }

        @Override
        public Edge next() {
            return _current = _iterParent.next();
        }

        @Override
        public void remove() {
            // adj list update
            internal_removeEdge_adjList(_current);
            // incidence lists update
            internal_removeEdge_incidenceList(_current);
            internal_removeEdge_presenceList(_current);
            // remove from main edge list
            _iterParent.remove();
        }
    }

    /**
     * vertex adjacency list view.
     * <p/>
     * removal/addition of elements are not supported
     */
    public class VertexAdjView extends AbstractCollection<IVertex> {
        private final Collection<IVertex> _values;
        private final IVertex _vertex;

        public VertexAdjView(IVertex vertex) {
            _values = _colAdjLists.get(_vertex = vertex);
        }

        @SuppressWarnings("NullableProblems")
        public Iterator<IVertex> iterator() {
            return new VertexAdjIterator(_vertex);
        }

        public int size() {
            return _values.size();
        }

        public boolean contains(Object o) {
            return _values.contains(o);
        }

        public boolean add(IVertex vertex) {
            throw new UnsupportedOperationException("adding a vertex directly to the adj list is not supported!! use graph methods instead");
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead");
        }
    }

    protected class VertexAdjIterator implements Iterator<IVertex> {
        private Iterator<IVertex> _iterParent;

        public VertexAdjIterator(IVertex vertex) {
            _iterParent = _colAdjLists.get(vertex).iterator();
        }

        @Override
        public boolean hasNext() {
            return _iterParent.hasNext();
        }

        @Override
        public IVertex next() {
            return _iterParent.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead");
        }
    }

    /**
     * edges incidence out list view of a vertex.
     * <p/>
     * removal/addition of elements are not supported yet
     */
    public class IncidenceOutView extends AbstractCollection<Edge> {
        private final Collection<Edge> _values;
        private final IVertex _vertex;

        public IncidenceOutView(IVertex vertex) {
            _values = _outEdges.get(_vertex = vertex);
        }

        @SuppressWarnings("NullableProblems")
        public Iterator<Edge> iterator() {
            return new IncidenceOutViewIterator(_vertex);
        }

        public int size() {
            return _values.size();
        }

        public boolean contains(Object o) {
            return _values.contains(o);
        }

        public boolean add(Edge vertex) {
            throw new UnsupportedOperationException("adding a vertex directly to the adj list is not supported!! use graph methods instead");
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead");
        }
    }

    protected class IncidenceOutViewIterator implements Iterator<Edge> {
        private Iterator<Edge> _iterParent;

        public IncidenceOutViewIterator(IVertex vertex) {
            _iterParent = _outEdges.get(vertex).iterator();
        }

        @Override
        public boolean hasNext() {
            return _iterParent.hasNext();
        }

        @Override
        public Edge next() {
            return _iterParent.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead");
        }
    }

    /**
     * edges incidence in list view of a vertex.
     * <p/>
     * removal/addition of elements are not supported yet
     */
    public class IncidenceInView extends AbstractCollection<Edge> {
        private final Collection<Edge> _values;
        private final IVertex _vertex;

        public IncidenceInView(IVertex vertex) {
            _values = _inEdges.get(_vertex = vertex);
        }

        @SuppressWarnings("NullableProblems")
        public Iterator<Edge> iterator() {
            return new IncidenceOutViewIterator(_vertex);
        }

        public int size() {
            return _values.size();
        }

        public boolean contains(Object o) {
            return _values.contains(o);
        }

        @Override
        public boolean add(Edge edge) {
            throw new UnsupportedOperationException("adding a vertex directly to the adj list is not supported!! use graph methods instead");
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead");
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    protected class IncidenceInViewIterator implements Iterator<Edge> {
        private Iterator<Edge> _iteratorParent;

        public IncidenceInViewIterator(IVertex vertex) {
            _iteratorParent = _inEdges.get(vertex).iterator();
        }

        @Override
        public boolean hasNext() {
            return _iteratorParent.hasNext();
        }

        @Override
        public Edge next() {
            return _iteratorParent.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("removing a vertex directly to the adj list is not supported!! use graph methods instead");
        }
    }

    /**
     * @return the number of edges in the graph
     */
    @Override
    public int numEdges() {
        return _setEdges.size();
    }

    /**
     * check whether there exists an edge (v1, v2) is in graph
     *
     * @param v1 vertex v1
     * @param v2 vertex v2
     * @return true if graph contains this edge
     */
    @Override
    public boolean hasEdge(IVertex v1, IVertex v2) {
        return getEdge(v1, v2) != null;
    }

    /**
     * check whether edge instance is in graph
     *
     * @param edge the edge in question
     * @return true if graph contains this edge
     */
    @Override
    public boolean hasEdge(Edge edge) {
        return internal_hasEdge(edge);
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     *
     * @return the edge so use can query the id
     */
    @Override
    public Edge addEdge(IVertex v1, IVertex v2) {
        return addEdge(v1, v2, 0f);
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     * @param weight weight of the edge
     *
     * @return the edge so use can query the id
     */
    @Override
    public Edge addEdge(IVertex v1, IVertex v2, float weight) {
        Edge edge = new Edge(v1, v2, getGraphType(), weight);

        return addEdge(edge);
    }

    /**
     * connect an edge (v1, v2) into the graph, v1 and v2 have to be members
     *
     * @return the edge so use can query the id, or {@code null} if the edge is incompatible
     *         with the graph type
     */
    @Override
    public Edge addEdge(Edge edge) {
        IVertex v1 = edge.getV1();
        IVertex v2 = edge.getV2();

        // validation
        validateVertex(v1);
        validateVertex(v2);

        if (hasEdge(v1, v2) && !isMultiEdgesSupported())
            return null;

        if (v1.equals(v2) && !isSelfLoopsSupported())
            return null;

        if(edge.getEdgeType() != getGraphType())
            return null;

        // adj list update
        _colAdjLists.get(v1).add(v2);

        if (getGraphType() == EDGE_DIRECTION.UNDIRECTED)
            _colAdjLists.get(v2).add(v1);

        // edge list update
        _setEdges.add(edge);

        // edge description
        ArrayList<Edge> edges = _mapPresenceEdges.get(edge.getDesc());
        if (edges == null)
            _mapPresenceEdges.put(edge.getDesc(), edges = new ArrayList<>());

        edges.add(edge);

        // incidence lists update
        _outEdges.get(v1).add(edge);
        _inEdges.get(v2).add(edge);

        return edge;
    }

    /**
     * remove edge (v1, v2) from the graph
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     * @return the edge if success, or null if failed
     */
    @Override
    public Edge removeEdge(IVertex v1, IVertex v2) {
        Edge edge = getEdge(v1, v2);

        if (edge != null)
            return internal_removeEdge(edge);

        return null;
    }

    /**
     * remove edge (v1, v2) from the graph
     *
     * @param edge an edge that already belong to the graph
     *
     * @return the edge if success, or null if failed
     */
    @Override
    public Edge removeEdge(Edge edge) {
        return internal_removeEdge(edge);
    }

    /**
     * remove all the edges that connect the two vertices
     *
     * @param u first vertex
     * @param v second vertex
     *
     * @return a collection of edges that connect the two vertices that were removed if any
     *
     * @throws java.lang.UnsupportedOperationException if the engine does not support multi edges
     */
    @Override public Collection<Edge> removeMultiEdges(IVertex u, IVertex v)
    {
        validateMultiEdgeSupport();

        Collection<Edge> multiEdges = _mapPresenceEdges.get(Edge.getEdgeDesc(u, v, getGraphType()));

        multiEdges = new ArrayList<>(multiEdges);

        // we can remove
        for (Edge edge : multiEdges) {
            internal_removeEdge(edge);
        }

        return multiEdges;
    }


    /**
     * get the edge that connects (v1, v2) from the graph
     *
     * @param v1 a vertex that already belong to the graph
     * @param v2 a vertex that already belong to the graph
     *
     * @return the edge if success, or null if failed
     */
    @Override
    public Edge getEdge(IVertex v1, IVertex v2) {
        ArrayList<Edge> edges = _mapPresenceEdges.get(Edge.getEdgeDesc(v1, v2, getGraphType()));

        if (edges != null && edges.size() > 0)
            return edges.get(0);

        return null;
    }

    /**
     * get all the edges that connect the two vertices
     *
     * @param u first vertex
     * @param v second vertex
     *
     * @return a collection of edges that connect the two vertices if any
     *
     * @throws java.lang.UnsupportedOperationException if the engine does not support multi edges
     */
    @Override
    public Collection<Edge> getMultiEdges(IVertex u, IVertex v) {
       validateMultiEdgeSupport();
       return Collections.unmodifiableList(_mapPresenceEdges.get(Edge.getEdgeDesc(u, v, getGraphType())));
    }

    private void validateMultiEdgeSupport() {

    }

    /**
     * @return the number of vertices in the Graph
     */
    @Override
    public int numVertices() {
        return vertices().size();
    }

    /**
     * clear the graph into an empty graph
     */
    @Override
    public void clear() {
        _setEdges.clear();
        _vertices.clear();
        _colAdjLists.clear();
        _inEdges.clear();
        _outEdges.clear();
    }

    /**
     * @param vertex the vertex in question
     * @return the out degree of  the vertex
     */
    public int outDegreeOfVertex(IVertex vertex) {
        return _colAdjLists.get(vertex).size();
    }

    /**
     * @param vertex the vertex in question
     * @return the in degree of  the vertex
     */
    @SuppressWarnings("UnusedDeclaration")
    public int inDegreeOfVertex(IVertex vertex) {
        return _inEdges.get(vertex).size();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        for (IVertex vertex : _vertices.values()) {
            vertex.dispose();
        }

        _vertices.clear();
        _colAdjLists.clear();

        _vertices = null;
    }

    /**
     * internal method to query edge inclusion via id
     *
     * @param edge the edge
     * @return true if graph contains this edge
     */
    private boolean internal_hasEdge(Edge edge) {
        return _setEdges.contains(edge);
    }

    /**
     * validates if a vertex is in the graph and throws an error otherwise
     *
     * @param v the vertex
     * @throws VertexNotFoundException
     */
    private void validateVertex(IVertex v) {
        if (!hasVertex(v))
            throw new VertexNotFoundException(v, this.getGraph());
    }

    /**
     * internal method for removal the neighbor edges of a vertex
     *
     * @param vertex the vertex whose neighbors are to be removed
     */
    private void internal_removeEdgeNeighborsOfVertex(IVertex vertex) {
        if (vertex == null || !hasVertex(vertex))
            return;

        HashSet<Edge> setIn = _inEdges.get(vertex);
        HashSet<Edge> setOut = _outEdges.get(vertex);

        Edge edge;

        for (Iterator<Edge> iterator = setIn.iterator(); iterator.hasNext(); ) {
            edge = iterator.next();
            iterator.remove();
            internal_removeEdge(edge);
        }

        for (Iterator<Edge> iterator = setOut.iterator(); iterator.hasNext(); ) {
            edge = iterator.next();
            iterator.remove();
            internal_removeEdge(edge);
        }

        _colAdjLists.remove(vertex).clear();

        setIn.clear();
        setOut.clear();

        _inEdges.remove(vertex);
        _outEdges.remove(vertex);
    }

    /**
     * internal removal of edge by it's id
     *
     * @param edge the edge in question
     * @return the removed edge, or null if not found
     */
    private Edge internal_removeEdge(Edge edge) {
        // adj list update
        internal_removeEdge_adjList(edge);
        // incidence lists update
        internal_removeEdge_incidenceList(edge);
        // edge list update
        _setEdges.remove(edge);
        internal_removeEdge_presenceList(edge);
        return edge;
    }

    /**
     * internal method for removing edge from the adjacency lists it is connected to
     *
     * @param edge the edge
     * @return the removed edge, or null if none
     */
    private Edge internal_removeEdge_adjList(Edge edge) {
        // validation
        if (!hasEdge(edge))
            return null;

        IVertex v1 = edge.getV1();
        IVertex v2 = edge.getV2();

        if (!hasVertex(v1) || !hasVertex(v2))
            return null;

        // adj list update
        _colAdjLists.get(v1).remove(v2);


        if (getGraphType() == EDGE_DIRECTION.UNDIRECTED)
            _colAdjLists.get(v2).remove(v1);

        return edge;
    }

    /**
     * internal method for removing edge from the incidence lists it is connected to
     *
     * @param edge the edge
     * @return the removed edge, or null if none
     */
    private Edge internal_removeEdge_incidenceList(Edge edge) {
        // validation
        if (!hasEdge(edge))
            return null;

        IVertex v1 = edge.getV1();
        IVertex v2 = edge.getV2();

        if (!hasVertex(v1) || !hasVertex(v2))
            return null;

        // incidence lists update
        HashSet<Edge> setOut = _outEdges.get(v1);
        HashSet<Edge> setIn = _inEdges.get(v2);

        setOut.remove(edge);
        setIn.remove(edge);

        return edge;
    }

    private Edge internal_removeEdge_presenceList(Edge edge) {
        ArrayList<Edge> edges = _mapPresenceEdges.get(edge.getDesc());
        edges.remove(edge);

        return edge;
    }

}