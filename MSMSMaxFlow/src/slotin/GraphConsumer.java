package slotin;

import graph.Graph;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GraphConsumer <T extends MaximumFlowAlgorithm> implements Runnable {

    private final T mfAlgo;
    private ConcurrentLinkedQueue<Graph> consumerQueue = 
	    new ConcurrentLinkedQueue<Graph> ();
    private Map<Graph,Long> executionTimes = 
	    new HashMap<Graph,Long> ();
    
    public GraphConsumer(T mfAlgo){
	this.mfAlgo = mfAlgo;
    }
    
    @Override
    public void run() {
	// TODO Auto-generated method stub
	Graph graph;
	long maxFlow;
	
	long startTime;
	long endTime;
	while (true){
	    graph = consumerQueue.poll();
	    if (graph == null){
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    } else {
		// Get the source and sink vertices
		startTime = System.nanoTime();
		maxFlow = mfAlgo.getMaxFlow(graph);
		endTime = System.nanoTime();
		
		executionTimes.put(graph, (startTime - endTime));
	    }
	}
    }
    
    public void addToQueue(Graph graph){
	consumerQueue.add(graph);
    }

}
