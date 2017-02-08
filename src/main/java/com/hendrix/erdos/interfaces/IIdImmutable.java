package com.hendrix.erdos.interfaces;

public interface IIdImmutable {

    /**
     *
     * @return the string identifier of the object
     */
    String 	getId();

    /**
     * does object has an identifier?
     *
     * @return true if id is set, false otherwise
     */
    boolean hasId();
}
