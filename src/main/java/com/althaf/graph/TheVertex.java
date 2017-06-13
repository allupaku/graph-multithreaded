package com.althaf.graph;

import com.althaf.graph.api.IEdge;
import com.althaf.graph.api.IVertex;
import com.althaf.graph.api.IWeightedEdge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by althaf mohammed
 */
public class TheVertex<T> implements IVertex<T> {

  protected IEdge<T> shortestEdge = null;

  protected T identifier;

  protected ArrayList<IEdge<T>> edges = new ArrayList<IEdge<T>>();

  private TheVertex(){
  }

  /**
   * Constructor with identifier
   * @param id   - identifier.
   */
  public TheVertex(T id){
    this.identifier = id;
  }

  public T getValue() {
    return this.identifier;
  }

  /**
   * Get all edges in this vertex.
   * @return
   */
  public List<IEdge<T>> getEdges() {

    return this.edges;
  }

  /**
   * Method to check whether a particular edge is connected to this vertex.
   * @param edge - edge to check against.
   * @return
   */
  public boolean hasEdge(IEdge<T> edge) {
    return false;
  }

  /**
   *  Add and edge to the vertex (list of edges connected to a vertex are maintained)
    * @param edge - the edge to be added to
   */
  public void addEdge(IEdge<T> edge) {

    if(!this.edges.contains(edge)){

      this.edges.add( edge );

      this.shortestEdge = null;
    }
  }

  /**
   * Number of edges connected from this vertex.
   * @return
   */
  public long numberOfEdges() {
    return this.edges.size();
  }

  /**
   * Get the neighboring vertices connected via all the edges.
   * @return
   */
  public List<IVertex<T>> getNeighbors(){

    List<IVertex<T>> neighbors = new ArrayList<IVertex<T>>();

    for(IEdge<T> edge : this.getEdges()){

      neighbors.add(edge.other(this));

    }
    return neighbors;
  }

  /**
   * Get the edge with shortest weight from the present vertex.
   * @return
   */
  public IEdge<T> shortestEdge() {
    if (shortestEdge == null && this.edges.size() > 1) {
      Collections.sort(this.edges);
      this.shortestEdge = this.edges.get(0);
    }
    return this.shortestEdge;
  }

  public String toString(){
    return this.identifier.toString();

  }

  public int compareTo(IVertex<T> o) {
    // TODO
    return 0;
  }
}
