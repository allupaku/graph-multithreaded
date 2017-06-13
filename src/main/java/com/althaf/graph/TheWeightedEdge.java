package com.althaf.graph;

import com.althaf.graph.api.IEdge;
import com.althaf.graph.api.IVertex;
import com.althaf.graph.api.IWeightedEdge;

/**
 * Created by althaf mohammed.
 */
public class TheWeightedEdge<T> implements IWeightedEdge<T>  {

  protected double weight;

  protected IVertex<T> vertexOne;

  protected IVertex<T> vertexTwo;

  private TheWeightedEdge(){}

  /**
   *  Create an edge with two vertices
   * @param vOne - first vertices
   * @param vTwo - second vertice.
   * @param w - weight of the edge.
   */
  public TheWeightedEdge(IVertex<T> vOne, IVertex<T> vTwo, double w){

    this.vertexOne = vOne;

    this.vertexTwo = vTwo;

    this.weight = w;
  }

  /**
   * Get one of the vertices of this edge
   * @return - the vertex
   */
  public IVertex<T> getVOne() {
    return this.vertexOne;
  }

  /**
   * Get one of the vertices of this edge
   * @return - the vertex
   */
  public IVertex<T> getVTwo() {
    return this.vertexTwo;
  }

  /**
   * Get the other vertex, other than the passed one.
   * @param v - the vertex whose other end to be found  from this edge.
   * @return
   */
  public IVertex<T> other(IVertex<T> v) {
    if(v.getValue() == this.vertexOne.getValue())
      return this.vertexTwo;
    else
      return this.vertexOne;
  }

  /**
   * Get the weight of this edge.
   * @return
   */
  public double getWeight() {
    return this.weight;
  }

  /**
   * Set the weight of this edge.
   * @param weight
   */
  public void setWeight(double weight) {

    this.weight = weight;
  }

  /**
   * Compare between two edges.
   * @param o
   * @return
   */
  public int compareTo(IEdge<T> o) {
    IWeightedEdge obj = (IWeightedEdge)o;

    if(this.weight > obj.getWeight())
      return 1;
    else if(this.weight < obj.getWeight())
      return -1;
    else
      return 0;
  }
}
