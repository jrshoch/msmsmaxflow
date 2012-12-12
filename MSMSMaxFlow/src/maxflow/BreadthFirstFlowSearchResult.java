package maxflow;

import graph.Vertex;

import java.util.Map;

public class BreadthFirstFlowSearchResult {

    private final long flow;
    private final Map<Vertex,Vertex> predecessors;
    
    public BreadthFirstFlowSearchResult(long flow, Map<Vertex,Vertex> predecessors){
	this.predecessors = predecessors;
	this.flow = flow;
    }

    public long getFlow() {
	return flow;
    }

    public Map<Vertex,Vertex> getPredecessors() {
	return predecessors;
    }
}
