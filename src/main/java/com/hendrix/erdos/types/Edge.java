package com.hendrix.erdos.types;

import com.hendrix.erdos.interfaces.IData;
import com.hendrix.erdos.interfaces.IIdImmutable;
import com.hendrix.erdos.interfaces.ITag;

/**
 * a graph edge<br/>
 * can represent both <code>DIRECTED</code> and <code>UNDIRECTED</code> edges.<br/>
 * defaults to <code>DIRECTED</code>, the direction property is immutable,
 * and can only be set during construction<br/>
 * directed edges with similar vertices and direction are equal (Java equality).<br/>
 * undirected edges with similar vertices are equal <code>(v1,v2).equals(v2,v1)=true</code><br/>
 * <li/>edge tag is automatically assaigned e0, e1,....
 *
 * @author Tomer Shalev
 */
public class Edge implements IIdImmutable, ITag, IData<Object>
{
    /**
     * the first vertex
     */
    protected   IVertex         _v1;
    /**
     * the second vertex
     */
    protected   IVertex         _v2;
    /**
     * the weight of the edge
     */
    private     float           _weight     = 0f;
    /**
     * the unique immutable identifier of the edge
     */
    private     String          _id         = null;
    /**
     * the tag of the edge
     */
    private     String          _tag        = null;
    /**
     * the tag of the edge
     */
    private     String          _desc       = null;
    /**
     * extra data
     */
    private     Object          _data       = null;
    /**
     * internal automatic tag generator helper
     */
    private     static int _autoId = 0;
    /**
     * the edge direction
     */
    private     EDGE_DIRECTION  _edgeType;

    /**
     * edge direction enum
     */
    public enum EDGE_DIRECTION {
        DIRECTED, UNDIRECTED
    }

    /**
     * <li/>if directed edge: v1->v2<br/>
     * <li/>if undirected min(v1.id, v2.id)<->max(v1.id,v2.id)
     *
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param edgeType DIRECTED or UNDIRECTED
     *
     * @return the identifier
     */
    public static String getEdgeDesc(IVertex v1, IVertex v2, EDGE_DIRECTION edgeType) {
        String res      = null;

        if(edgeType==EDGE_DIRECTION.DIRECTED) {
            res         =  (v1.getId() + "->" + v2.getId());
        }
        else if(edgeType==EDGE_DIRECTION.UNDIRECTED) {
            int compare = v1.getId().compareTo(v2.getId());
            if(compare < 0)
                res     = v1.getId() + "<->" + v2.getId();
            else
                res     = v2.getId() + "<->" + v1.getId();
        }

        return res;
    }

    /**
     * @see #getEdgeDesc(IVertex, IVertex, Edge.EDGE_DIRECTION)
     */
    public static String getEdgeDesc(Edge edge, EDGE_DIRECTION edgeType)
    {
        return getEdgeDesc(edge.getV1(), edge.getV2(), edgeType);
    }

    /**
     * @see #getEdgeDesc(Edge, Edge.EDGE_DIRECTION)
     * @return edge description
     */
    public String getDesc() {
        return _desc;
    }

    /**
     * automatic unique edge tag generator
     * @return the suggested automatic tag
     */
    private static String getAutoId() {return "e" + _autoId++;}

    /**
     *
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param edgeType <code>DIRECTED or UNDIRECTED</code>
     */
    public Edge(IVertex v1, IVertex v2, EDGE_DIRECTION edgeType) {
        _v1         = v1;
        _v2         = v2;
        _edgeType   = edgeType;

        _id         = getAutoId();
        _desc       = getEdgeDesc(this, _edgeType);
        _tag        = null;
    }

    /**
     *
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param edgeType <code>DIRECTED or UNDIRECTED</code>
     * @param weight the assigned weight
     */
    public Edge(IVertex v1, IVertex v2, EDGE_DIRECTION edgeType, float weight) {
        this(v1, v2, edgeType);

        _weight = weight;
    }

    /**
     * get the edge type
     * @return <code>DIRECTED, UNDIRECTED</code>
     */
    @SuppressWarnings("UnusedDeclaration")
    public EDGE_DIRECTION getEdgeType() {
        return _edgeType;
    }

    /**
     *
     * @return the first vertex of the edge
     */
    public IVertex getV1() {
        return _v1;
    }

    /**
     *
     * @return the second vertex of the edge
     */
    public IVertex getV2() {
        return _v2;
    }

    /**
     *
     * @return the weight of the edge
     */
    @SuppressWarnings("UnusedDeclaration")
    public float getWeight() {
        return _weight;
    }

    /**
     * set the weight of the edge
     * @param weight the assigned weight
     */
    @SuppressWarnings("UnusedDeclaration")
    public void setWeight(float weight) {
        _weight = weight;
    }

    /**
     * the unique immutable identifier of the edge
     *
     * @return the string identifier of the object
     */
    @Override
    public String getId() {
        return _id;
    }

    /**
     * does object has an identifier?
     *
     * @return true if id is set, false otherwise
     */
    @Override
    public boolean hasId() {
        return (_id!=null);
    }

    /**
     * set the tag/description of an object
     *
     * @param tag the tag
     */
    @Override
    public void setTag(String tag) {
        _tag = tag;
    }

    /**
     *
     * @return the tag of the object
     */
    @Override
    public String getTag() {
        return _tag;
    }

    /**
     * {@inheritDoc}
     */
    public void setData(Object data) {
        _data = data;
    }

    /**
     * {@inheritDoc}
     */
    public Object getData() {
        return _data;
    }

    @Override
    public String toString() {
        if(_tag != null)
            return _tag;

        String res      = null;

        String v1_r     = _v1.toString();
        String v2_r     = _v2.toString();

        if(_edgeType==EDGE_DIRECTION.DIRECTED) {
            res         =  (v1_r + "->" + v2_r);
        }
        else if(_edgeType==EDGE_DIRECTION.UNDIRECTED) {
            int compare = v1_r.compareTo(v2_r);
            if(compare < 0)
                res     = v1_r + "<->" + v2_r;
            else
                res     = v2_r + "<->" + v1_r;
        }

        return res;
    }

}
