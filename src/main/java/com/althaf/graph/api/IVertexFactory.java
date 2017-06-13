package com.althaf.graph.api;

/**
 * Created by althaf mohammed.
 */
public interface IVertexFactory<T> {

  public IVertex<T> getOrMakeVertex(T identifier);

}
