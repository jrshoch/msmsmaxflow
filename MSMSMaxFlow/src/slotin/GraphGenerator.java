package slotin;

import graph.Graph;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GraphGenerator implements Runnable {

    private ConcurrentLinkedQueue<Graph> producerQueue = 
	    new ConcurrentLinkedQueue<Graph> ();
    
    private long graphsProduced = 0;
    private long numGraphsWanted;
    
    @Override
    public void run() {
	
	while (graphsProduced < numGraphsWanted){
	    // Produce a graph and add it to the queue
	    Graph graph;
	    // TODO use the graph generator to start creating graphs
	    
	    producerQueue.add(graph);
	    graphsProduced += 1;
	}
	
    }

    public Graph getNextGraph(){
	return producerQueue.poll();
    }
}
