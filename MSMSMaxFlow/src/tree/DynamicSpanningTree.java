package tree;

import graph.Edge;
import graph.Vertex;

public interface DynamicSpanningTree {

    public boolean areInSameComponent(Vertex vertex1, Vertex vertex2);

    public long getSlack(Edge edge);
    
    public void addSlackOnPath(Vertex start, Vertex end, long amount);
    
    public void addSlackOnReversePath(Vertex start, Vertex end, long amount);
    
    public Edge getMinimumEdgeOnPath(Vertex start, Vertex end);
    
    public void remove (Edge edge);
    
    public void insert (Edge edge);
}
