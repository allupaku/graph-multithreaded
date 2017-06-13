package com.althaf.graph;

import java.io.FileInputStream;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Class to create the graph from the text file.
 *
 * Created by althaf mohamed
 */
public class WeightedStringGraphBuilder {

  // Queue used to read and buffer into the memory.
  Queue<String> readQueue = new ArrayBlockingQueue<String>(Constants.READ_QUEUE_SIZE);

  WeightedGraphBuilderThread[] buildThread = new WeightedGraphBuilderThread[Constants.MAX_THREADS];

  FileInputStream finputStream;

  Scanner fileScanner;

  WeightedGraph<String> wgraph = new WeightedGraph<String>(); // empty graph which will be filled.

  /**
   * Build the graph, weighted graph from file.
   *
   * Based on the format :
   *
   *    node1  node2  weight
   *
   * @param filename
   * @return
   */

  public WeightedGraph<String> buildGraphFromFile(String filename){

    try {
      // initialize inputstream
      finputStream = new FileInputStream(filename);
      // initialize the scanner.
      fileScanner = new Scanner(finputStream);
      // record the starting time to evaluate the time taken.
      long startMilis  = System.currentTimeMillis();
      // Start building threads, these threads will act when there are elements
      // present in the readQueue
      this.startBuilding();
      // Loop till there is another line to be read.
      while(fileScanner.hasNextLine()){

        try {
          if(readQueue.size() < Constants.READ_QUEUE_SIZE) {

            readQueue.add(fileScanner.nextLine());

          }else {
            // Sleep if the queue is full.
            Thread.sleep(100);
          }
        }catch(Exception ex){
          ex.printStackTrace();
        }
      }
      // After reading finish the threads, by setting the flag and wait for the threads to finish.
      this.finishBuilding();

      long endMillis  = System.currentTimeMillis();

      System.out.println("TIME TAKEN TO BUILD THE GRAPH" + ((endMillis - startMilis) / 1000) + " seconds " );

      System.out.println("NUMBER OF EDGES IN GRAPH " + wgraph.getAllEdges().size());

      System.out.println("NUMBER OF VERTICES IN GRAPH " + wgraph.getAllVertices().size());

    }catch(Exception ex){
        ex.printStackTrace();
    }
    return wgraph;
  }

  /**
   *  Create and start the threads passing graph and readqueue.
   */
  protected void startBuilding(){

    for(int i =0 ; i < Constants.MAX_THREADS; i++){
      buildThread[i] = new WeightedGraphBuilderThread(wgraph, readQueue);
      buildThread[i].start();
    }
  }

  /**
   * Stop the threads by setting the flag and wait for the threads to finish.
   *
   * @throws Exception
   */
  protected void finishBuilding() throws Exception{
    for(int i =0 ; i < Constants.MAX_THREADS; i++){
     if(buildThread[i]!=null){
       buildThread[i].setFinishedFile(true);
       buildThread[i].join();
     }
    }
  }
}
