package com.althaf.graph;

import com.althaf.graph.api.IEdge;
import com.althaf.graph.api.IGraph;
import com.althaf.graph.api.IVertex;
import com.althaf.graph.api.IWeightedEdge;

import java.util.List;

/**
 * Created by althaf mohammed
 */
public class WeightedGraph<T> implements IGraph<T> {

  TheVertexFactory<T> vf = new TheVertexFactory<T>();

  TheEdgeFactory<T> ef = new TheEdgeFactory<T>(vf);

  protected long edgeCount = 0;

  public IEdge<T> addEdge(T v1, T v2 , double w) {

    IEdge<T> edge = this.ef.makeWeightedEdge(v1, v2, w);

    return edge;
  }

  public IVertex<T> addVertex(T vid) {
    IVertex<T> vert = this.vf.getOrMakeVertex(vid);

    return vert;
  }

  /**
   * Get all the edges
   * @return
   */
  public List<IWeightedEdge<T>> getAllEdges() {
    return this.ef.getAllEdges();
  }

  public IWeightedEdge<T> getEdge(IVertex<T> v1, IVertex<T> v2){

    return this.ef.getWeightedEdge(v1,v2);
  }

  /**
   *  Get and edge with the identifier.
   * @param id1
   * @param id2
   * @return
   */
  public IWeightedEdge<T> getEdge(T id1, T id2){

    return this.ef.getWeightedEdge(this.vf.getVertex(id2),this.vf.getVertex(id2));
  }

  /**
   * Get all vertices in the graph
   * @return
   */
  public List<IVertex<T>> getAllVertices() {

    return this.vf.getAllVertices();
  }

  /**
   * Get a particular vertex with the identifier
   * @param id
   * @return
   */
  public IVertex<T> getVertex(T id){

    return this.vf.getVertex( id );
  }

}
