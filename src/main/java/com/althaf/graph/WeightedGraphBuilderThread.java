package com.althaf.graph;

import com.althaf.graph.api.IEdge;

import java.util.Queue;

/**
 * Main thread to create edges in the graph from the line.
 * Created by althaf mohammed
 */
public class WeightedGraphBuilderThread extends Thread {

  WeightedGraph<String> wgraph ;

  Queue<String> readQueue;

  boolean finishedFile = false;

  private WeightedGraphBuilderThread(){}

  /**
   * Constructor passing the graph object shared between the threads and readQueue.
   * @param wgraph
   * @param readQueue
   */
  public WeightedGraphBuilderThread(WeightedGraph<String> wgraph, Queue<String> readQueue){

    this.wgraph = wgraph;

    this.readQueue = readQueue;
  }

  /**
   * Setter to finish the thread.
   * @param fin
   */
  public void setFinishedFile(boolean fin){
    this.finishedFile = fin;
  }

  /**
   * Method to add and edge from aline
   * This is where each line is parsed and added to edge if valid.
   * If it is not found valid, the line is ignored by priting a warning message.
   * @param line
   * @return
   */
  public IEdge<? extends String> addEdgeFromString(String line) {

    IEdge<String> edge = null;

    String [] parts = line.trim().split("[ |\t]");

    if(parts.length >= 3){

      double weight = Double.parseDouble(parts[2]);

      if(!parts[0].equals("") && !parts[1].equals("") && weight>=0 ) {

        edge = wgraph.addEdge(parts[0], parts[1], weight);

      }else{
        System.out.println("[WARNING] : Invalid information found - ignoring the edge : " + line);
      }

    }
    return edge;
  }

  /**
   * Main thread function.
   */
  public void run() {

    while (readQueue != null) {

      try {
        // read a line and remove from the queue.
        String line = readQueue.remove();
        // try to add an edge based on the line above.
        addEdgeFromString(line);

      }catch(Exception ex){
        // In case of exception, like Queue Empty, sleep for 20.
        sleepMe(1);
      }
      if(readQueue.size()==0 && finishedFile) {
          readQueue = null;
      }
    }
  }

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
