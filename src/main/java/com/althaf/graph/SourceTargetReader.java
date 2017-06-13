package com.althaf.graph;

import com.althaf.graph.api.IVertex;
import com.althaf.graph.api.IWeightedEdge;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by althaf mohammed.
 *
 * This class is to read from the file which contains the source node and the target nodes.
 */
public class SourceTargetReader {

    ShortestPathFinderThread[] shortestThread = new ShortestPathFinderThread[Constants.MAX_THREADS];

    FileInputStream finputStream;

    FileOutputStream foutStream;

    Scanner fileScanner;

    WeightedGraph<String> weightedGraph; // graph to operate on.

    String outputFileName = "output.txt"; // default file name for output.

    // Below Maps and sets are shared between the threads for processing.

    private Set<IVertex<String>> settledNodes;

    private Set<IVertex<String>> unSettledNodes;

    private Map<IVertex<String>, IVertex<String>> predecessors;

    private Map<IVertex<String>, Double> distance;

    // --

  /**
   *
   * @param wgraph
   */

    public SourceTargetReader(WeightedGraph<String> wgraph){

      this.weightedGraph = wgraph;

      settledNodes = new HashSet<IVertex<String>>();

      unSettledNodes = new HashSet<IVertex<String>>();

      distance = new HashMap<IVertex<String>, Double>();

      predecessors = new HashMap<IVertex<String>, IVertex<String>>();
    }

    public Map<IVertex<String>, Double> getDistances(){

      return this.distance;
    }

  /**
   *
   * @param source
   */

  public void findAllPahtsInGraphWithSource(IVertex<String> source){

    try {

      distance.put(source, 1.0);

      unSettledNodes.add(source);

      long startMilis  = System.currentTimeMillis();

      // start all the threads - this will start MAX_THREADS, which will
      // continuously work on the algorithm and find the max weight for all the nodes.
      this.startBuilding(source);

      // finish and join the threads
      this.finishBuilding();

      long endMillis  = System.currentTimeMillis();

      System.out.println("Weights Calculated for " + distance.size());

      System.out.println("Time taken to calculate the paths : " + ((endMillis - startMilis) / 1000) + " seconds " );

    }catch(Exception ex){
      ex.printStackTrace();
    }

  }

  /**
   * Method to find the strongest paths
   * @param filename
   */

  public void findStrongestPathsFromFile(String filename){

      try {

        finputStream = new FileInputStream(filename);

        fileScanner = new Scanner(finputStream);

        String sourceVertex = fileScanner.nextLine();

        IVertex<String> source = this.weightedGraph.getVertex(sourceVertex);

        if(source == null){
          throw new Exception("Invalid Source Vertex found");
        }

        this.findAllPahtsInGraphWithSource(source);

        fileScanner.nextLine(); // Empty line read out.. and ignore

        foutStream = new FileOutputStream(this.outputFileName); // open the file for writing

        while(fileScanner.hasNextLine()){

          try {

            String nodeNext = fileScanner.nextLine();

            IVertex<String> targetNode = this.weightedGraph.getVertex(nodeNext);

            if(targetNode!=null && distance.get(targetNode)!=null) {

              String lineout = sourceVertex + "\t" + nodeNext + " \t " + Double.toString(
                  distance.get(targetNode)) + " : ";

              lineout += printablePath(targetNode);

              lineout += "\r\n";

              foutStream.write(lineout.getBytes());
            }

          }catch(Exception ex){
            ex.printStackTrace();
          }
        }

        foutStream.close();

        finputStream.close();

      }catch(Exception ex){
        ex.printStackTrace();
      }
    }

    /**
     * Method to get the path from the source node as list - from predecessors.
     * @param target
     * @return
     */
    protected LinkedList<IVertex<String>> getPath(IVertex<String> target) {

      LinkedList<IVertex<String>> path = new LinkedList<IVertex<String>>();

      IVertex<String> step = target;

      // check if a path exists
      if (predecessors.get(step) == null) {
        return null;
      }
      path.add(step);
      while (predecessors.get(step) != null) {
        step = predecessors.get(step);
        path.add(step);
      }
      // Put it into the correct order
      Collections.reverse(path);
      return path;
    }

  /**
   * Get a string to pring the path and its weight in between.
   * @param target
   * @return
   */
    protected String printablePath(IVertex<String> target){

      String lineout = "";

      LinkedList<IVertex<String>> path = this.getPath(target);

      IVertex<String> previous = null;

      IVertex<String> next = null;

      if(path!=null) {
        Iterator<IVertex<String>> it = path.iterator();

        while (it.hasNext()) {

          previous = next;

          next = it.next();

          IWeightedEdge<String> prevedge = null;

          if(previous!=null && next!=null){
            prevedge = this.weightedGraph.getEdge(previous,next);
          }

          if(prevedge!=null)
            lineout+= " " +prevedge.getWeight() + " ";

          lineout += " " + next.getValue() + " ";
        }
      }
      return lineout;
    }

    /**
     *  Create the threads , upto MAX_THREADS and start them.
     * @param source
     */
    protected void startBuilding(IVertex<String> source){

      for(int i =0 ; i < Constants.MAX_THREADS; i++){
        shortestThread[i] = new ShortestPathFinderThread(this.weightedGraph, source,
                                                         this.settledNodes, this.unSettledNodes,
                                                         this.predecessors, this.distance);
        shortestThread[i].start();
      }
    }

    /**
     *  End the threads, by setting the flag and joining on all the threads.
     * @throws Exception
     */
    protected void finishBuilding() throws Exception{
      for(int i =0 ; i < Constants.MAX_THREADS; i++){
        if(shortestThread[i]!=null){
          shortestThread[i].setFinishedProcessing(true);
          shortestThread[i].join();
        }
      }
    }

  /**
   * Setter for setting the output file name.
   * @param outputFileName - name of the file.
   */
  public void setOutputFileName(String outputFileName) {
      this.outputFileName = outputFileName;
    }

}
