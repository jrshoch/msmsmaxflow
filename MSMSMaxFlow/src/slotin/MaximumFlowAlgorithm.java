package slotin;

import graph.Graph;
import graph.Vertex;

public interface MaximumFlowAlgorithm {

    public long getMaxFlow(Graph graph, Vertex s, Vertex t);
    public String getName();
}
