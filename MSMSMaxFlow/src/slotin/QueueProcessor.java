package slotin;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueProcessor implements Runnable {

    private final ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>> producerQueue;
    private final Collection<ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>>>
    		consumerQueues;
    
    public QueueProcessor(ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>> producerQueue, 
	    Collection<ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>>> consumerQueues){
	this.producerQueue = producerQueue;
	this.consumerQueues = consumerQueues;
    }
    
    @Override
    public void run() {
	Graph<Vertex,Edge<Vertex>> graph;
	while (true){
	    if (producerQueue.isEmpty()){
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    } else {
		graph = producerQueue.poll();
		for (ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>> nextQueue : consumerQueues){
		    nextQueue.add(graph);
		}
	    }
	}
    }

}
