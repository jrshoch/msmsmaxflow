package slotin;

import graph.Graph;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueProcessor implements Runnable {

    private final ConcurrentLinkedQueue<Graph> producerQueue;
    private final Collection<ConcurrentLinkedQueue<Graph>>
    		consumerQueues;
    
    public QueueProcessor(ConcurrentLinkedQueue<Graph> producerQueue, 
	    Collection<ConcurrentLinkedQueue<Graph>> consumerQueues){
	this.producerQueue = producerQueue;
	this.consumerQueues = consumerQueues;
    }
    
    @Override
    public void run() {
	Graph graph;
	while (true){
	    if (producerQueue.isEmpty()){
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    } else {
		graph = producerQueue.poll();
		for (ConcurrentLinkedQueue<Graph> nextQueue : consumerQueues){
		    nextQueue.add(graph);
		}
	    }
	}
    }

}
