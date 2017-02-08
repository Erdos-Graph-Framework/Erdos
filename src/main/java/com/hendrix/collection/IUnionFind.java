package com.hendrix.collection;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Union Find Data Structure
 * @param <T> the class type of the members of the sets
 */
public interface IUnionFind<T> {

  /**
   * unites the dynamic sets that contain member1 and member2
   */
  public void UNION(T member1, T member2);

  /**
   * creates a new set whose only member (and thus representative) is member
   */
  public void MAKESET(T member);

  /**
   * returns a reference to the set containing member
   * @return the set containing member
   */
  public HashSet<T> FINDSET(T member);

  /**
   * @return the collection of disjoint sets
   */
  public ArrayList<HashSet<T>> getDisjointSets();
}
