package graph;

import java.util.Collection;

public interface Graph {

    public long getId();
    public String getName();
    
    public Collection<Vertex> getVertices();
    public Collection<Edge> getEdges();
    
    public Vertex getVertex(Long id);
    
    public Edge getEdgeWithEndpoints(Vertex tail, Vertex head);
    
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2);
    public boolean areDirectionallyAdjacent(Vertex tail, Vertex head);
    
    public Graph getDual();
    public Edge getDual(Edge edge);
    
}
