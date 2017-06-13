package com.althaf.graph;

import com.althaf.graph.api.IEdge;
import com.althaf.graph.api.IVertex;
import com.althaf.graph.api.IWeightedEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by althafmohammed
 */
public class TheEdgeFactory<T> {

  Map<String,IWeightedEdge<T>> edgeList = new HashMap<String,IWeightedEdge<T>>();

  TheVertexFactory<T> vf;

  private TheEdgeFactory(){}

  /**
   * Constructor to create the factory with vertex factory.
   * @param vfactory
   */
  public TheEdgeFactory(TheVertexFactory<T> vfactory) {
    this.vf = vfactory;
  }

  /**
   * Method to make the edge - weighted edge.
   * @param id1 - identifier for first vertex.
   * @param id2 - identifier for second vertex.
   * @param w - weigh of the edge
   * @return - the created edge.
   */
  public IWeightedEdge<T> makeWeightedEdge(T id1, T id2, double w){

    IVertex<T> v1 = vf.getOrMakeVertex(id1);

    IVertex<T> v2 = vf.getOrMakeVertex(id2);

    return this.makeWeightedEdge(v1,v2,w);
  }

  /**
   * Create an edge from two vertex objects.
   * @param v1 - first vertex object
   * @param v2 - second vertex object
   * @param w - weight of the edge.
   * @return - the created edge / the existing edge with this spec.
   */
  public IWeightedEdge<T> makeWeightedEdge(IVertex<T> v1, IVertex<T> v2,double w){
    synchronized (edgeList) {
      IWeightedEdge<T> we = this.getWeightedEdge(v1, v2);

      if (we == null) {

        we = new TheWeightedEdge<T>(v1, v2, w);

        this.edgeList.put(this.getKeyFromVertixes(v1, v2), we);

        v1.addEdge(we);

        v2.addEdge(we);

      }

      return we;
    }
  }

  /**
   * Get the weighted edge from the two vertices.
   * @param v1 - one vertex to find the edge
   * @param v2 - second vertex to find the edge
   * @return - the edge , with the above two vertices.
   */
  public IWeightedEdge<T> getWeightedEdge(IVertex<T> v1, IVertex<T> v2){

    // check the first form of the key
    IWeightedEdge<T> edge =  this.edgeList.get(getKeyFromVertixes(v1,v2));
    // if the edge is not present, then check the reverse order key.
    if(edge == null)
      edge = this.edgeList.get(getKeyFromVertixes(v2,v1));

    return edge;
  }

  protected String getKeyFromVertixes(IVertex<T> v1, IVertex<T> v2){

    return v1.toString()+":"+v2.toString();
  }

  /**
   * Method to get all the edges as a list
   * @return
   */
  public List<IWeightedEdge<T>> getAllEdges(){

    return new ArrayList<IWeightedEdge<T>>(this.edgeList.values());

  }
}