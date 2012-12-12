package graph;

import java.util.Collection;
import java.util.List;

public interface Graph extends Named {

    public long getId();
    
    public Collection<Vertex> getVertices();
    public Collection<Face> getFaces();
    
    public Edge getReverseEdge(Edge edge);
    public Edge getEdgeFromTailHead(Vertex tail, Vertex head);
    public Edge getEdgeFromLeftRight(Face left, Face right);
    
    public Graph getDual();
    public Edge getDualOf(Edge edge);
    public Face getDualOf(Vertex vertex);
    public Vertex getDualOf(Face face);
    
    public List<Vertex> getNeighboringVertices(Vertex vertex);
    public List<Edge> getNeighboringEdges(Vertex vertex);
    
    public List<Face> getAdjacentFaces(Face face);
    
}
