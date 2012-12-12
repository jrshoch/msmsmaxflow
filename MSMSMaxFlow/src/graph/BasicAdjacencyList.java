package graph;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class BasicAdjacencyList implements AdjacencyList {

    private final List<Vertex> neighboringVertices;
    private final Map<Vertex, Edge> vertexToEdge;
    private final Map<Edge, Edge> edgeToNextEdge;
    private final Map<Edge, Edge> edgeToPreviousEdge;
    private final List<Edge> neighboringEdges;

    private BasicAdjacencyList(List<Vertex> verticesInOrder, List<Edge> edgesInOrder) {
        this.neighboringVertices = ImmutableList.<Vertex> copyOf(verticesInOrder);
        this.neighboringEdges = ImmutableList.<Edge> copyOf(edgesInOrder);
        this.vertexToEdge = Maps.newHashMap();
        this.edgeToNextEdge = Maps.newHashMap();
        this.edgeToPreviousEdge = Maps.newHashMap();
        int numberOfNeighbors = neighboringVertices.size();
        for (int i = 0; i < numberOfNeighbors; i++) {
            vertexToEdge.put(neighboringVertices.get(i), neighboringEdges.get(i));
        }
        Edge first = neighboringEdges.get(0);
        Edge last = neighboringEdges.get(numberOfNeighbors - 1);
        for (int i = 0; i < numberOfNeighbors - 1; i++) {
            edgeToNextEdge.put(neighboringEdges.get(i), neighboringEdges.get(i + 1));
        }
        edgeToNextEdge.put(last, first);
        for (int i = 1; i < numberOfNeighbors; i++) {
            edgeToPreviousEdge.put(neighboringEdges.get(i), neighboringEdges.get(i - 1));
        }
        edgeToPreviousEdge.put(first, last);
    }

    public static BasicAdjacencyList create(List<Vertex> verticesInOrder, List<Edge> edgesInOrder) {
        return new BasicAdjacencyList(verticesInOrder, edgesInOrder);
    }

    @Override
    public List<Vertex> getNeighboringVertices() {
        return neighboringVertices;
    }

    @Override
    public List<Edge> getNeighboringEdges() {
        return neighboringEdges;
    }

    @Override
    public Edge getEdgeIfAdjacent(Vertex vertex) {
        return vertexToEdge.get(vertex);
    }

    @Override
    public Edge getNextClockwiseEdge(Edge edge) {
        return edgeToNextEdge.get(edge);
    }

    @Override
    public Edge getPreviousClockwiseEdge(Edge edge) {
        return edgeToPreviousEdge.get(edge);
    }
}
