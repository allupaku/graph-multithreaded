package com.althaf.graph.api;

import java.util.List;

/**
 * Created by althaf mohammed.
 */
public interface IVertex<T> extends Comparable<IVertex<T>>{

  public List<? extends IEdge<T>> getEdges();

  public List<? extends IVertex<T>> getNeighbors();

  public long numberOfEdges();

  public IEdge<T> shortestEdge();

  public boolean hasEdge(IEdge<T> edge);

  public void addEdge(IEdge<T> edge);

  public T getValue();

}
