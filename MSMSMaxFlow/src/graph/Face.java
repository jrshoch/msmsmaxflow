package graph;

import java.util.List;

public interface Face {
    
    public long getId();
    public String getName();
    
    public List<Edge> getEdgesInOrder();
    public List<Vertex> getVerticesInOrder();
    
    public boolean isInsideFace(Edge edge);
    public boolean isInsideFace(Vertex vertex);
    
    public boolean isAdjacentToFace(Edge edge);
    public boolean isAdjacentToFace(Vertex vertex);

}
