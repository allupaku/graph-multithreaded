package com.althaf.graph;

import com.althaf.graph.api.IVertex;
import com.althaf.graph.api.IVertexFactory;
import com.althaf.graph.api.IWeightedEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by althaf mohammed
 */
public class TheVertexFactory<T> implements IVertexFactory<T> {

  // Map to store all the vertices
  Map<T, IVertex<T>> vertexMap = new HashMap<T,IVertex<T>>();

  /**
   * Mehtod to get or create a vertice.
   * - this method checks whether the vertice with value exists or not,
   *  if not then create one and return it.
   * @param identifier
   * @return
   */
  public IVertex<T>  getOrMakeVertex(T identifier) {

    synchronized (this.vertexMap) {

      IVertex<T> vert = this.getVertex(identifier);

      if (vert == null) {

        vert = new TheVertex<T>(identifier);

        vertexMap.put(identifier, vert);

      }
      return vert;
    }
  }

  /**
   * Get the vertex with the id.
   * @param identifier
   * @return
   */
  public IVertex<T> getVertex(T identifier){

    return this.vertexMap.get(identifier);
  }

  /**
   * Get the vertex map
   * @return
   */
  public Map<T,IVertex<T>> getVertexMap(){

    return this.vertexMap;
  }

  /**
   * Get all vertices as a list.
   * @return
   */
  public List<IVertex<T>> getAllVertices(){

    return new ArrayList<IVertex<T>>(this.vertexMap.values());

  }

}
