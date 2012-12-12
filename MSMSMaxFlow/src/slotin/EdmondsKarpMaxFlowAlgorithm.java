package slotin;

import graph.Graph;
import graph.Vertex;
import maxflow.EdmondsKarp;

public class EdmondsKarpMaxFlowAlgorithm implements MaximumFlowAlgorithm {

    
    @Override
    public long getMaxFlow(Graph graph, Vertex s, Vertex t) {
	return EdmondsKarp.getMaxFlow(graph, s, t);
    }

    @Override
    public String getName() {
	return "EdmondsKarp";
    }

}
