package graph;

import java.util.Collection;

public interface Graph {

    public long getId();
    public String getName();
    
    public Collection<Vertex> getVertices();
    public Collection<Edge> getEdges();
    
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2);
}
