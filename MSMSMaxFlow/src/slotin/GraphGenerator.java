package slotin;

import graphgeneration.GeneratePlanarGraph;
import graphgeneration.NoPathExistsException;

import java.util.concurrent.ConcurrentLinkedQueue;

import maxflow.MaxFlowProblem;

public class GraphGenerator implements Runnable {

    private ConcurrentLinkedQueue<MaxFlowProblem> producerQueue = 
	    new ConcurrentLinkedQueue<MaxFlowProblem> ();
    
    private long graphsProduced = 0;
    private final GraphDistribution distribution;
    private final long maxCapacityOnRandomWalk;
    private final long maxCapacityOffCut;
    private final long numPaths;
    
    public GraphGenerator(GraphDistribution distribution, long maxCapacityOnRandomWalk, 
	    long maxCapacityOffCut, long numPaths){
	this.distribution = distribution;
	this.maxCapacityOffCut = maxCapacityOffCut;
	this.maxCapacityOnRandomWalk = maxCapacityOnRandomWalk;
	this.numPaths = numPaths;
    }
    
    public ConcurrentLinkedQueue<MaxFlowProblem> getProducerQueue(){
        return producerQueue;
    }
    
    @Override
    public void run() {
	MaxFlowProblem mfProb;
	while (graphsProduced < distribution.getNumGraphsWanted()){
	    try {
		mfProb = GeneratePlanarGraph.generateMaxFlowProblem(distribution.getWidth(),
		    distribution.getHeight(), maxCapacityOnRandomWalk, 
		    maxCapacityOffCut, numPaths);
		
		producerQueue.add(mfProb);
		distribution.signalForNextGraph();
	    } catch (NoPathExistsException e) {
		e.printStackTrace();
	    }
	}
    }

    public MaxFlowProblem getNextGraph(){
	return producerQueue.poll();
    }
}
