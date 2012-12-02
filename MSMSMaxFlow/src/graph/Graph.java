package graph;

import java.util.Collection;

public interface Graph {

    public long getId();
    public String getName();
    
    public Collection<Vertex> getVertices();
    public Collection<Edge> getEdges();
    public Collection<Face> getFaces();
    
    public Edge getEdgeWithEndpoints(Vertex vertex1, Vertex vertex2);
    
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2);
}
