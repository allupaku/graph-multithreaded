package com.althaf.graph;

/**
 * Shortest Path finding.
 *
 * Created by althaf mohammed - akoya@qf.org.qa
 */
public class App
{
    public static void main( String[] args )
    {
        if( args.length >= 2 && !args[0].equals("") && !args[1].equals("") ) {

            String graphFile = args[0].trim();

            String nodeFile = args[1].trim();

            WeightedGraph<String> graph = null; // Weighted graph to create the nodes and vertices.

            if(FileValidator.isNameValid(graphFile) && FileValidator.isNameValid(nodeFile)){
                // if the file names are valid

                WeightedStringGraphBuilder wgb = new WeightedStringGraphBuilder(); // create an graph builder

                graph = wgb.buildGraphFromFile(graphFile); // method to build the graph.

                if(graph!=null){
                    // Source reader to read the second file and get the source vertex and the
                    // destination vertices
                    SourceTargetReader sourceTargetReader = new SourceTargetReader(graph);
                    // check whether we have an output file specified in the command line.
                    if(args.length>2 &&  !args[2].equals("")){

                        String outputFile = args[2].trim();

                        if(FileValidator.isNameValid(outputFile)){
                            // if it is a valid file name, then use that file name to write the
                            // out put to.
                            sourceTargetReader.setOutputFileName(outputFile);
                        }
                    }
                    // Method to find the shortest paths from file.
                    sourceTargetReader.findStrongestPathsFromFile(nodeFile);
                }
            }else{
                // If the patsh are not valid.
                printMessageWithHeader("Invalid file paths / file names specified.");
            }
        }else{

            String message =    "\tTwo parameters with graph and node list file required \r\n" +
                                "\tUSAGE : graph.txt node.txt [output.txt] \r\n";
            printMessageWithHeader(message);
        }
    }

    /**
     * Print a message with header.
     * @param message
     */
    public static void printMessageWithHeader(String message){

        System.out.println("------------------------------------------------------------------");
        System.out.println("\tStrongest path program created by Althaf M , akoya@qf.org.qa");
        System.out.println("\t");
        System.out.println(message);
        System.out.println("------------------------------------------------------------------");

    }
}
