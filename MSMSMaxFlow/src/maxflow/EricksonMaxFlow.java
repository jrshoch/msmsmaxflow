package maxflow;

import graph.Graph;

import java.math.BigInteger;

import operations.DualFactory;

public class EricksonMaxFlow {
    
    public static BigInteger getMaxFlow(Graph graph){
	
	Graph dual = DualFactory.getDual(graph);
    }
}
