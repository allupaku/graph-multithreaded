====================================================================================================
This programs finds the strongest path in a weighter undirected graph.

I have built this as a maven project, hence compiling will be very easy

Build the project using the command  : mvn clean install

You can run this program in two ways.
----------------------------------------------------------------------------------------------------
1. USING JAR directly.
----------------------------------------------------------------------------------------------------
The above command will build a jar file which can be executed with parameters.
JAR file will be generated at target - subfolder, with the name longestpath-weighted-1.0.jar.

So this program can be executed with command

  - java -jar target/longestpath-weighted-1.0.jar graph.txt source_target.txt output.txt

where
  - graph.txt - should be the file with all the edges in the graph.
  - source_targe.txt - should contain a source node as the first like and with a freeline,
    it should contain all the destination vertices to which the strongest path has to be calculated.

  - output.txt - optional , file name where you expect the output to be written to.
----------------------------------------------------------------------------------------------------
2. Using command mvn exec:exec
----------------------------------------------------------------------------------------------------
  - this will assume that two files is present in the same running directory,
    1. graph.txt
    2. source_targe.txt
    3. output.txt - for the program output



