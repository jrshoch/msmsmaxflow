package shortestpaths;

import graph.Vertex;

import java.util.Collection;

public interface SpanningTree {
    
    public Vertex getTail(Vertex vertex);
    
    public Collection<Vertex> getVertices();
    public Vertex getRoot();
    
    public Collection<Vertex> getChildren(Vertex vertex);
}
