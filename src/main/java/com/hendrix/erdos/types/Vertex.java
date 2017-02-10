package com.hendrix.erdos.types;

/**
 * a base implementation of IVertex
 * @param <T> the data type to store
 * @see IVertex
 * @author Tomer Shalev
 */
public class Vertex<T> implements IVertex<T>
{
    protected int                     _color        = 0;
    protected String                  _id           = null;
    protected T                       _data         = null;
    protected int                     _index        = -1;
    protected float                   _weight       = 0;
    protected String                  _tag          = null;

    private static int _autoId                      = 0;

    private static String getAutoId() {return "v" + _autoId++;}

    public Vertex()
    {
        this(null);
    }

    /**
     *
     * @param tag tag the vertex
     */
    public Vertex(String tag)
    {
        _tag = tag;
        _id = getAutoId();
    }

    /**
     * {@inheritDoc}
     */
    public int getColor() {
        return _color;
    }

    /**
     * {@inheritDoc}
     */
    public void setColor(int color) {
        _color = color;
    }

    /**
     * {@inheritDoc}
     */
    public int getIndex() {
        return _index;
    }

    /**
     * @param index
     * @return the index of the vertex
     */
    /**
     * {@inheritDoc}
     */
    public int setIndex(int index) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public void assignWeight(float weight) {
        _weight = weight;
    }

    /**
     * {@inheritDoc}
     */
    public float getWeight() {
        return _weight;
    }

    /**
     * {@inheritDoc}
     */
    public void setData(T data) {
        _data = data;
    }
    /**
     * {@inheritDoc}
     */
    public T getData() {
        return _data;
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return _id;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasId() {
        return _id!=null;
    }

    /**
     * hashCode depends on _id, and id is immutable,
     * hence hashcode never changes, which is crucial
     * because the framework uses ALOT of HashSet and HashMap
     * with plain hashcode.
     */
    /**
     * {@inheritDoc}
     */
    public final int hashCode() {
        return "graph_vertex".hashCode() + _id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public void dispose()
    {
        _data = null;
    }

    /**
     * {@inheritDoc}
     */
    public void setTag(String tag) {
        _tag = tag;
    }

    /**
     * {@inheritDoc}
     */
    public String getTag() {
        return _tag;
    }

    @Override
    public String toString() {
        return toString(this);
    }

    static public String toString(IVertex vertex){
        String tag = vertex.getTag();

        return (tag != null) ? tag : vertex.getId();
    }

}
