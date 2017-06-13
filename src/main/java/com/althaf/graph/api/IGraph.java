package com.althaf.graph.api;

import java.util.List;

/**
 * Created by althaf mohamed
 * Interface to define the graph.
 */
public interface IGraph<T> {

  public IEdge<T> addEdge(T v1, T v2, double w);

  public IVertex<T> addVertex(T vid);

  public List<? extends IEdge<T>> getAllEdges();

  public List<IVertex<T>> getAllVertices();
}
