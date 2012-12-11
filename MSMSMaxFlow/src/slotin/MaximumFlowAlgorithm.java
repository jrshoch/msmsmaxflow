package slotin;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

public interface MaximumFlowAlgorithm {

    public long getMaximumFlow(Graph<Vertex,Edge<Vertex>> graph);
    
}
