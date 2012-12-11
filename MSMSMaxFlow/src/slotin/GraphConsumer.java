package slotin;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GraphConsumer <T extends MaximumFlowAlgorithm> implements Runnable {

    private final T mfAlgo;
    private ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>> consumerQueue = 
	    new ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>>();
    private Map<Graph<Vertex,Edge<Vertex>>,Long> executionTimes = 
	    new HashMap<Graph<Vertex,Edge<Vertex>>,Long> ();
    
    public GraphConsumer(T mfAlgo){
	this.mfAlgo = mfAlgo;
    }
    
    @Override
    public void run() {
	// TODO Auto-generated method stub
	Graph<Vertex,Edge<Vertex>> graph;
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
		startTime = System.nanoTime();
		maxFlow = mfAlgo.getMaximumFlow(graph);
		endTime = System.nanoTime();
		
		executionTimes.put(graph, (startTime - endTime));
	    }
	}
    }
    
    public void addToQueue(Graph<Vertex,Edge<Vertex>> graph){
	consumerQueue.add(graph);
    }

}
