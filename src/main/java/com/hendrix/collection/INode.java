package com.hendrix.collection;

/**
 * Created by Tomer on 28/02/2015.
 */
public interface INode<T> {
  INode getParent();
  void setParent(INode p);

  void setData(T data);
  T getData();
}
