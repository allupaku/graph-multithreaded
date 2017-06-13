package com.althaf.graph.api;

/**
 * Created by althaf mohamed
 * Factory TO build / make weighted edge.
 */
public interface IEdgeFactory<T> {

  public IWeightedEdge<T> makeWeightedEdge(T id1, T id2, double w);

  public IWeightedEdge<T> makeWeightedEdge(IVertex<T> v1, IVertex<T> v2,double w);


}
