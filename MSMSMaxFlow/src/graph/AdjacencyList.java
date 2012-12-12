package graph;

import java.util.List;

public interface AdjacencyList {

    public Edge getEdgeIfAdjacent(Vertex vertex);

    public List<Vertex> getNeighboringVertices();
    public List<Edge> getNeighboringEdges();

    public Edge getNextClockwiseEdge(Edge edge);
    public Edge getPreviousClockwiseEdge(Edge edge);
}
