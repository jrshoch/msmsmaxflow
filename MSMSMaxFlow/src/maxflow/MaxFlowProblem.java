package maxflow;

import graph.Graph;
import graph.Vertex;

public class MaxFlowProblem {
    
    private final Graph graph;
    private final Vertex s;
    private final Vertex t;
    private final long maxFlow;
    
    protected MaxFlowProblem(Graph graph, Vertex s, Vertex t, long maxFlow){
	this.graph = graph;
	this.s = s;
	this.t = t;
	this.maxFlow = maxFlow;
    }
    
    public static MaxFlowProblem create(Graph graph, Vertex s, Vertex t, long maxFlow){
	return new MaxFlowProblem(graph, s, t, maxFlow);
    }

    public Graph getGraph() {
	return graph;
    }

    public Vertex getS() {
	return s;
    }

    public Vertex getT() {
	return t;
    }

    public long getMaxFlow() {
	return maxFlow;
    }
}
