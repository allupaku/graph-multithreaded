package com.althaf.graph;

import com.althaf.graph.api.IVertex;
import com.althaf.graph.api.IWeightedEdge;

import java.util.*;

/**
 * Created by althaf mohammed.
 */
public class ShortestPathFinderThread extends Thread {

  WeightedGraph<String> weightedGraph;

  IVertex<String> sourceVertex;

  private boolean finishedProcessing = false;

  // Store the visited nodes /vertices.
  private Set<IVertex<String>> settledNodes;
  // Store the un visited nodes/ vertices.
  private Set<IVertex<String>> unSettledNodes;
  // Store the predecessors, to retrace back the path.
  private Map<IVertex<String>, IVertex<String>> predecessors;
  // Store the distance as a map of vertices and weight.
  private Map<IVertex<String>, Double> distance;

  // We shouldnt let any one create this object without any properties in it.
  private ShortestPathFinderThread(){}

  /**
   * Constructor to create this thread. Inorder for this thread to function properly
   * we need these parameters.
   *
   * @param wgraph - weighted graph to operate the algorithm on.
   * @param sv  - source vertex, to calculate all the weights from.
   * @param settledNodes - settled nodes set
   * @param unSettledNodes - unsettled  / unvisited nodes set.
   * @param predecessors - predecessors set.
   * @param distance - map of weights.
   */
  public ShortestPathFinderThread(WeightedGraph<String> wgraph,
                                  IVertex<String> sv,
                                  Set<IVertex<String>> settledNodes,
                                  Set<IVertex<String>> unSettledNodes,
                                  Map<IVertex<String>, IVertex<String>> predecessors,
                                  Map<IVertex<String>, Double> distance){


    this.weightedGraph = wgraph;

    this.sourceVertex = sv;

    this.settledNodes = settledNodes;

    this.unSettledNodes = unSettledNodes;

    this.distance = distance;

    this.predecessors = predecessors;

  }

  /**
   * Method to find the neighbors which are not yet visited.
   * @param vNode
   * @return
   */
  private List<IVertex<String>> getNeighbors(IVertex<String> vNode){
      // Get a list of neighbors from the IVertex.
      List<IVertex<String>> neighbors = new ArrayList<IVertex<String>>(vNode.getNeighbors());

      Iterator<IVertex<String>> it = neighbors.iterator();
      // Iterate over the neighbors and remove the ones which are already visited.
      while(it.hasNext()) {
        if (isSettled(it.next())) {
          it.remove();
        }
      }
      return neighbors;
  }

  /**
   * Find the maximum weights of the node and all its neighbors.
   * @param node - the node in concern.
   */
  private void findMaxWeights(IVertex<String> node) {

      // Get the neighbors of the current node.
      List<IVertex<String>> adjacentNodes = getNeighbors(node);
      // Iterate over the neighbors
      for (IVertex<String> target : adjacentNodes) {
          // Get the present weight we have in list, if any.
          double targetWeightStrongest = getStrongestWeight(target);
          // Calculate new weight multiplying the targets weight
          double nodeAndTargetWeight = getStrongestWeight(node) * getWeight(node, target);
          // Check whether we have a higher weight in the new path.
          if (targetWeightStrongest < nodeAndTargetWeight ) {
            // Update the distance map.
            distance.put(target, nodeAndTargetWeight);
            // update the predeccorts map.
            predecessors.put(target, node);
            // update the unsettled nodes.
          }
          unSettledNodes.add(target);
      }
  }

  /**
   * Get the weight between two vertices, from the edge.
   * @param node - first node
   * @param target - second node , from which the weight has to be found.
   * @return the weight if edge is found, else return 0.0, means there is no edge.
   *
   */
  private double getWeight(IVertex<String> node, IVertex<String> target) {

    if(this.weightedGraph!=null) {

      IWeightedEdge<String> edge = this.weightedGraph.getEdge(node, target);

      return edge!=null ? edge.getWeight() : 0.0;
    }
    else {
      return 0.0;
    }
  }

  /**
   * Get the maximum weight from a set of vertexes / neighboring vertixes.
   * @param vertexes - Set of vertices to compare between.
   * @return - Vertex with max weight.
   */
  private IVertex<String> getMaximum(Set<IVertex<String>> vertexes) {

      IVertex<String> maximum = null;

      Iterator<IVertex<String>> iter = vertexes.iterator();

      while (iter.hasNext()) {

        IVertex<String> vertex = iter.next();

          if (maximum == null || getStrongestWeight(vertex) > getStrongestWeight(maximum) ) {

            maximum = vertex;

          }
      }
      return maximum;
  }

  /**
   * Check whether the vertex is present in the settled group.
   * @param vertex
   * @return
   */
  private boolean isSettled(IVertex<String> vertex) {
      return settledNodes.contains(vertex);
  }

  /**
   * Get the strongest weight, we have calculated till now.
   * @param destination - destination vertex to find the weight.
   * @return - weight, if already calculated or else 0.0
   */
  private double getStrongestWeight(IVertex<String> destination) {

      Double d = distance.get(destination);
      if (d == null) {
        return 0.0;
      } else {
        return d;
      }
  }

  /**
   *  Main thread function run indefinitely , till every thing is done.
   */
  public void run(){

    while(true){

      if (unSettledNodes.size() > 0) {

          try {

            synchronized (unSettledNodes) {
              // Get the maximum in unsettled nodes collection.
              IVertex<String> node = getMaximum(unSettledNodes);

              if(node!=null){
                // we are going to process that, so add it to settled.
                settledNodes.add(node);
                // remove from unsettled
                unSettledNodes.remove(node);
                // find max weights for this node.
                findMaxWeights(node);

              }else{
                this.sleepMe(10);
              }
            }
          }catch(Exception e){
            e.printStackTrace();
          }
      }else{

        this.sleepMe(10);

        if(this.finishedProcessing)
          return;
      }
    }

  }

  /**
   * Setter method to end the thread.
   * @param finishedProcessing
   */
  public void setFinishedProcessing(boolean finishedProcessing) {
    this.finishedProcessing = finishedProcessing;
  }

  /**
   * Utility method to sleep and handle the exception.
   */
  /**
   *  Utility method to sleep this thread.
   */
  protected void sleepMe(int sleeptime){
    try {
      Thread.sleep((sleeptime!=0 )? sleeptime : 10 );
    }catch(Exception e){

    }
  }
}
