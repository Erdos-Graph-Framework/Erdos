package com.hendrix.erdos.types;

import com.hendrix.collection.ImmutableHashCode;
import com.hendrix.erdos.interfaces.IData;
import com.hendrix.erdos.interfaces.IDisposable;
import com.hendrix.erdos.interfaces.IIdImmutable;
import com.hendrix.erdos.interfaces.ITag;

/**
 * Interface for Graph Vertex
 * @param <T> the data type to store
 */
public interface IVertex<T> extends IIdImmutable, IData<T>, IDisposable, ITag, ImmutableHashCode
{
    /**
     * @return the color of the vertex
     */
    int getColor();

    /**
     * @param color the color
     */
    void setColor(int color);

    /**
     * @return the index of the vertex
     */
    int getIndex();
    /**
     * @param index the index of the vertex inside the graph (remove it from here)
     */
    int setIndex(int index);

    /**
     * assign weight to vertex
     * @param weight the weight of the edge
     */
    void assignWeight(float weight);

    /**
     * get weight of vertex
     * @return the weight of the vertex
     */
    float getWeight();

    String toString();
}
