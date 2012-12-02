package graph;

import java.util.Collection;

public interface Vertex {

    public long getId();
    public String getName();
    
    public Collection<Vertex> getNeighboringVertices();
    public Collection<Vertex> getUndirectedNeighboringVertices();
    public Collection<Edge> getEdgesFrom();
    
    public boolean isAdjacentTo(Edge edge);
    public boolean isAdjacentTo(Vertex vertex);
}
