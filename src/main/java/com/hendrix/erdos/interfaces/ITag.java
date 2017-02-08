package com.hendrix.erdos.interfaces;

/**
 * @author Tomer Shalev
 */
public interface ITag {
    /**
     * set the tag/description of an object
     *
     * @param tag the tag
     */
    void setTag(String tag);

    /**
     *
     * @return the tag of the object
     */
    String getTag();
}
