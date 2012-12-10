package maxflow;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.math.BigInteger;

import operations.DualFactory;

public class EricksonMaxFlow {
    
    public static <V extends Vertex, E extends Edge> BigInteger 
    		getMaxFlow(Graph graph){
	
	Graph dual = DualFactory.getDual(graph);
	dual.getEdges()
	// Construct the 
	return null;
    }
}
