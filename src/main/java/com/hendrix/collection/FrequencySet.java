package com.hendrix.collection;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * FrequencySet is an implementation of a Set.
 * frequency set allows to add and remove the same object as many times.
 * it's frequency data will be updated, the complete removal of a member thus
 * only happens when it's frequency is zero.
 *
 * the implementation uses most of {@code HashSet} implementations (but not as extended
 * class because of some performance technical issues).
 *
 * <ul>
 *     <li/>use {@code add(), remove(), increaseFrequencyOf()} to update frequency of element.
 *     <li/>use {@code frequencyOf} to query frequency of element.
 *     <li/>use {@code iterator.remove()} to update frequency of element while iteration.
 *
 * @author Tomer Shalev
 */
@SuppressWarnings("UnusedDeclaration")
public class FrequencySet<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable {

    private static final long serialVersionUID = -5022323554453321676L;

    transient HashMap<E, Integer> backingMap;

    /**
     * Constructs a new empty instance of {@link FrequencySet}.
     */
    public FrequencySet() {
        this(new HashMap<E, Integer>());
    }

    /**
     * Constructs a new instance of {@code HashSet} with the specified capacity.
     *
     * @param capacity
     *            the initial capacity of this {@code HashSet}.
     */
    public FrequencySet(int capacity) {
        this(new HashMap<E, Integer>(capacity));
    }

    /**
     * Constructs a new instance of {@code HashSet} with the specified capacity
     * and load factor.
     *
     * @param capacity
     *            the initial capacity.
     * @param loadFactor
     *            the initial load factor.
     */
    public FrequencySet(int capacity, float loadFactor) {
        this(new HashMap<E, Integer>(capacity, loadFactor));
    }

    /**
     * Constructs a new instance of {@code HashSet} containing the unique
     * elements in the specified collection.
     *
     * @param collection
     *            the collection of elements to add.
     */
    public FrequencySet(Collection<? extends E> collection) {
        this(new HashMap<E, Integer>(collection.size() < 6 ? 11 : collection
          .size() * 2));
        for (E e : collection) {
            add(e);
        }
    }

    FrequencySet(HashMap<E, Integer> backingMap) {
        this.backingMap = backingMap;
    }

    /**
     * Adds the specified object to this {@code FrequencySet}.
     * if the object is already a member then it's frequency data
     * will be updated accordingly.
     *
     * @param object
     *            the object to add.
     * @return {@code true} when this {@code HashSet} did not already contain
     *         the object, {@code false} otherwise
     */
    @Override
    public boolean add(E object) {
        return backingMap.put(object, frequencyOf(object) + 1) == null;
    }

    /**
     * query the frequency that the same object appears in the {@code FrequencySet}.
     *
     * @param e the member whose frequency is of interest
     *
     * @return the member's frequency
     */
    public Integer frequencyOf(E e) {
        Integer value = backingMap.get(e);

        return value==null ? 0 : value;
    }

    /**
     * Removes all elements from this {@code HashSet}, leaving it empty.
     *
     * @see #isEmpty
     * @see #size
     */
    @Override
    public void clear() {
        backingMap.clear();
    }

    /**
     * Returns a new {@code HashSet} with the same elements and size as this
     * {@code HashSet}.
     *
     * @return a shallow copy of this {@code HashSet}.
     * @see Cloneable
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object clone() {
        try {
            FrequencySet<E> clone = (FrequencySet<E>) super.clone();
            clone.backingMap = (HashMap<E, Integer>) backingMap.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Searches this {@code HashSet} for the specified object.
     *
     * @param object
     *            the object to search for.
     * @return {@code true} if {@code object} is an element of this
     *         {@code HashSet}, {@code false} otherwise.
     */
    @Override
    public boolean contains(Object object) {
        return backingMap.containsKey(object);
    }

    /**
     * Returns true if this {@code HashSet} has no elements, false otherwise.
     *
     * @return {@code true} if this {@code HashSet} has no elements,
     *         {@code false} otherwise.
     * @see #size
     */
    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    /**
     * Returns an Iterator on the elements of this {@code FrequencySet}.
     * removing elements through the iterator will decrease their frequency
     * as expected.
     *
     * @return an Iterator on the elements of this {@code FrequencySet}.
     * @see java.util.Iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new FrequencyIterator();
    }

    private class FrequencyIterator implements Iterator<E> {

        private Iterator<E> backingIterator;
        private E current;

        private FrequencyIterator() {
            backingIterator = backingMap.keySet().iterator();
        }

        /**
         * Returns true if there is at least one more element, false otherwise.
         *
         * @see #next
         */
        @Override
        public boolean hasNext() {
            return backingIterator.hasNext();
        }

        /**
         * Returns the next object and advances the iterator.
         *
         * @return the next object.
         * @throws java.util.NoSuchElementException if there are no more elements.
         * @see #hasNext
         */
        @Override
        public E next() {
            return backingIterator.next();
        }

        /**
         * Removes the last object returned by {@code next} from the collection.
         * This method can only be called once between each call to {@code next}.
         *
         * @throws UnsupportedOperationException if removing is not supported by the collection being
         *                                       iterated.
         * @throws IllegalStateException         if {@code next} has not been called, or {@code remove} has
         *                                       already been called after the last call to {@code next}.
         */
        @Override
        public void remove() {
            int newFrequency = decreaseFrequencyOf(current);

            if(newFrequency==0)
                backingIterator.remove();
        }
    }

    /**
     * increase the frequency of a member
     *
     * @param e the member
     *
     * @return the new member's frequency
     */
    public int increaseFrequencyOf(E e) {
        int value = frequencyOf(e);
        int newValue = value + 1;

        backingMap.put(e, newValue);

        return newValue;
    }

    /**
     * decrease the frequency of a member
     *
     * @param e the member
     *
     * @return the new member's frequency
     */
    private int decreaseFrequencyOf(E e) {
        int value = frequencyOf(e);
        int newValue = value - 1;

        backingMap.put(e, newValue);

        return newValue;
    }

    /**
     * decrease the frequency of this member by 1.
     * if it's previous frequency was 1, then the member will
     * be completely removed from the set.
     *
     * @param object
     *            the object to remove.
     * @return {@code true} if the object was removed, {@code false} otherwise.
     */
    @Override
    public boolean remove(Object object) {
        E key = (E)object;

        if(key==null)
            return false;

        int frequency = decreaseFrequencyOf(key);

        if(frequency >= 1){
            return true;
        }

        return backingMap.remove(object) != null;
    }

    /**
     * Returns the number of unique elements in this {@code FrequencySet}.
     *
     * @return the number of elements in this {@code FrequencySet}.
     */
    @Override
    public int size() {
        return backingMap.size();
    }

    HashMap<E, FrequencySet<E>> createBackingMap(int capacity, float loadFactor) {
        return new HashMap<E, FrequencySet<E>>(capacity, loadFactor);
    }
}
