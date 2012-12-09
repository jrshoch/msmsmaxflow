package graph;

import java.util.Collection;

public interface EdgeMatrix {

    public Collection<Vertex> getVertices();
    
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2);
    
    public boolean isDirectionallyAdjacent(Vertex fromVertex, Vertex toVertex);

    public Edge getEdge(Vertex fromVertex, Vertex toVertex);
    
    public Collection<Vertex> getNeighboringVertices(Vertex fromVertex);
    
    public Collection<Edge> getEdgesFrom(Vertex fromVertex);
    
    public void insertVertex(Vertex vertex);
    public void insertEdge(Edge edge);
}
