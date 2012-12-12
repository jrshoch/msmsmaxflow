package slotin;

import java.io.FileWriter;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WriteResultsToFile {
    
    public static void WriteResultsToFile(String filename, 
	    Collection<List<MaxFlowProblemResult>> consumerQueues){
	 CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');
	 
	 MaxFlowProblemResult currentResult;
	 String[] entries;
	 for (ConcurrentLinkedQueue<MaxFlowProblemResult> queue : consumerQueues){
	     while (!queue.isEmpty()){
		 currentResult = queue.poll();
		 entries = convertProblemResult(currentResult);
		 writer.writeNext(entries);
	     }
	 }
	 writer.close();
    }
    
    private static String[] convertProblemResult(MaxFlowProblemResult result){
	String[] stringResults = new String[4];
	stringResults[0] = result.getAlgorithmName();
	stringResults[1] = String.valueOf(result.getNumVertices());
	stringResults[2] = String.valueOf(result.getNanoTime());
	stringResults[3] = String.valueOf(result.getComputedMaxFlow());
	stringResults[4] = String.valueOf(result.getCorrectMaxFlow());
	return stringResults;
    }
    
}
