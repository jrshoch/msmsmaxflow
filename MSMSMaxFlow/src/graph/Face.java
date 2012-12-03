package graph;

import java.util.Collection;

public interface Face {
    
    public Collection<Edge> getEdges();
    public Collection<Vertex> getVertices();
    
    public boolean isInsideFace(Edge edge);
    public boolean isInsideFace(Vertex vertex);
    
    public boolean isAdjacentToFace(Edge edge);
    public boolean isAdjacentToFace(Vertex vertex);

}
