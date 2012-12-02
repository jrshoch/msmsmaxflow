package graph;

import java.util.Collection;

public interface Graph {

    public long getId();
    public String getName();
    
    public Collection<Vertex> getVertices();
    
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2);
    public boolean isDirectionallyAdjacent(Vertex fromVertex, Vertex toVertex);
}
