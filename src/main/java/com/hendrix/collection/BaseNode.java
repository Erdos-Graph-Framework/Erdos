package com.hendrix.collection;

/**
 * Created by Tomer on 28/02/2015.
 */
public class BaseNode<T> implements INode<T> {

  public BaseNode(T data) {
    _data = data;
  }

  private T _data;
  private INode<T> _parent;

  @Override
  public INode getParent() {
    return _parent;
  }

  @Override
  public void setParent(INode p) {
    _parent = p;
  }

  @Override
  public void setData(T data) {
    _data = data;
  }

  @Override
  public T getData() {
    return _data;
  }
}
