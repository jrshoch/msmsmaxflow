package slotin;

public class MaxFlowProblemResult {

    private final String algorithmName;
    private final long numVertices;
    private final long numEdges;
    private final long nanoTime;
    private final long correctMaxFlow;
    private final long computedMaxFlow;
    
    public MaxFlowProblemResult(String algorithmName, long numVertices, long numEdges, 
	    long nanoTime, long correctMaxFlow, long computedMaxFlow){
	this.algorithmName = algorithmName;
	this.numVertices = numVertices;
	this.numEdges = numEdges;
	this.nanoTime = nanoTime;
	this.computedMaxFlow = computedMaxFlow;
	this.correctMaxFlow = correctMaxFlow;
    }
    
    public long getNumEdges() {
        return numEdges;
    }

    public long getNanoTime() {
        return nanoTime;
    }

    public long getCorrectMaxFlow() {
        return correctMaxFlow;
    }

    public long getComputedMaxFlow() {
        return computedMaxFlow;
    }

    public String getAlgorithmName() {
	return algorithmName;
    }

    public long getNumVertices() {
	return numVertices;
    }
}
