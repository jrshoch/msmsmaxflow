package slotin;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GraphGenerator implements Runnable {

    private ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>> producerQueue = 
	    new ConcurrentLinkedQueue<Graph<Vertex,Edge<Vertex>>>();
    
    private long graphsProduced = 0;
    private long numGraphsWanted;
    
    @Override
    public void run() {
	
	while (graphsProduced < numGraphsWanted){
	    // Produce a graph and add it to the queue
	    Graph<Vertex,Edge<Vertex>> graph;
	    // TODO use the graph generator to start creating graphs
	    
	    producerQueue.add(graph);
	    graphsProduced += 1;
	}
	
    }

    public Graph<Vertex,Edge<Vertex>> getNextGraph(){
	return producerQueue.poll();
    }
}
