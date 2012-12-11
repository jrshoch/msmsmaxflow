package graph;

import java.util.List;

public interface AdjacencyList {

    public Vertex getStartingVertexertex();
    
    public Edge getEdgeIfAdjacent(Vertex vertex);
    
    /**
     * A list of the neighboring vertices in clockwise order
     * @return
     */
    public List<Vertex> getNeighboringVertices(); 
    public List<Edge> getNeighboringEdges();
    
}
