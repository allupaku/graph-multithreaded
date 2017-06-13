package com.althaf.graph.api;

/**
 * Created by althaf mohammed
 */
public interface IWeightedEdge<T> extends IEdge<T> {

  public double getWeight();

  public void setWeight(double weight);
}
