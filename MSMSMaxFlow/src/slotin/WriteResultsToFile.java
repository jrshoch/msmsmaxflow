package slotin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class WriteResultsToFile {

    public static void WriteResultsToFile(String filename,
	    Collection<List<MaxFlowProblemResult>> consumerQueues) {
	try {
	    CSVWriter writer = new CSVWriter(new FileWriter(filename), '\t');

	    String[] entries;
	    for (List<MaxFlowProblemResult> queue : consumerQueues) {
		for (MaxFlowProblemResult currentResult : queue) {
		    entries = convertProblemResult(currentResult);
		    writer.writeNext(entries);
		}
	    }
	    writer.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static String[] convertProblemResult(MaxFlowProblemResult result) {
	String[] stringResults = new String[4];
	stringResults[0] = result.getAlgorithmName();
	stringResults[1] = String.valueOf(result.getNumVertices());
	stringResults[2] = String.valueOf(result.getNanoTime());
	stringResults[3] = String.valueOf(result.getComputedMaxFlow());
	stringResults[4] = String.valueOf(result.getCorrectMaxFlow());
	return stringResults;
    }

}
