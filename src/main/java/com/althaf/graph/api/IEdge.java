package com.althaf.graph.api;

/**
 * Created by althaf mohamed
 * This is the interface for defining a generic edge.
 */
public interface IEdge<T> extends Comparable<IEdge<T>>{

  public IVertex<T> getVOne();

  public IVertex<T> getVTwo();

  public IVertex<T> other(IVertex<T> v);
}
