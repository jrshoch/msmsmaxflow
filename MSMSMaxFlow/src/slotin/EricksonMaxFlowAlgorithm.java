package slotin;

import graph.Graph;
import graph.Vertex;
import maxflow.EricksonMaxFlow;

public class EricksonMaxFlowAlgorithm implements MaximumFlowAlgorithm {

    @Override
    public long getMaxFlow(Graph graph, Vertex s, Vertex t) {
	return EricksonMaxFlow.getMaxFlow(graph, s, t);
    }

    @Override
    public String getName() {
	return "EricksonNaive";
    }

    
}
