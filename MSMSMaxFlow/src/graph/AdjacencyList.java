package graph;

import java.util.List;

public interface AdjacencyList {

    public Edge getEdgeIfAdjacent(Vertex vertex);

    public List<Vertex> getNeighboringVertices();
    public List<Edge> getNeighboringEdges();

    public Edge getNextClockwise(Edge edge);
    public Edge getPreviousClockwise(Edge edge);

    public Vertex getNextClockwise(Vertex vertex);
    public Vertex getPreviousClockwise(Vertex vertex);
}
