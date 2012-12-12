package slotin;

import graph.Graph;
import graph.Vertex;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import maxflow.MaxFlowProblem;

public class GraphConsumer <T extends MaximumFlowAlgorithm> implements Runnable {

    private final T mfAlgo;
    private ConcurrentLinkedQueue<MaxFlowProblem> consumerQueue = 
	    new ConcurrentLinkedQueue<MaxFlowProblem> ();
    private List<MaxFlowProblemResult> results = new LinkedList<MaxFlowProblemResult> ();
    
    public GraphConsumer(T mfAlgo){
	this.mfAlgo = mfAlgo;
    }
    
    public List<MaxFlowProblemResult> getResults(){
	return results;
    }
    
    public ConcurrentLinkedQueue<MaxFlowProblem> getConsumerQueue(){
	return consumerQueue;
    }
    
    @Override
    public void run() {
	// TODO Auto-generated method stub
	Graph graph;
	Vertex s;
	Vertex t;
	MaxFlowProblem maxFlowProblem;
	MaxFlowProblemResult problemResult;
	long maxFlow;
	long startTime;
	long endTime;
	
	while (true){
	    maxFlowProblem = consumerQueue.poll();
	    if (maxFlowProblem == null){
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    } else {
		System.out.println("got graph.");
		// Get the source and sink vertices
		graph = maxFlowProblem.getGraph();
		s = maxFlowProblem.getS();
		t = maxFlowProblem.getT();
		startTime = System.nanoTime();
		maxFlow = mfAlgo.getMaxFlow(graph, s, t);
		endTime = System.nanoTime();
		
		problemResult = new MaxFlowProblemResult(mfAlgo.getName(), 
			graph.getVertices().size(), 0, 
			endTime - startTime, maxFlowProblem.getMaxFlow(), maxFlow);
		results.add(problemResult);
		System.out.print("Algorithm: " + problemResult.getAlgorithmName() + " " + 
			((problemResult.getComputedMaxFlow() == problemResult.getCorrectMaxFlow()) ? "CORRECT" : "WRONG") +
			", " + String.valueOf(problemResult.getNanoTime()) + " nanoseconds");
	    }
	}
    }
    
    public List<MaxFlowProblemResult> getResultsSoFar(){
	return Collections.unmodifiableList(results);
    }
     
    public void addToQueue(MaxFlowProblem problem){
	consumerQueue.add(problem);
    }

}
