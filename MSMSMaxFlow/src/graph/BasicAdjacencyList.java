package graph;

import java.util.List;
import java.util.Map;

import util.ListToCyclicMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class BasicAdjacencyList implements AdjacencyList {

    private final List<Vertex> neighboringVertices;
    private final Map<Vertex, Edge> vertexToEdge;
    private final List<Edge> neighboringEdges;
    private final Map<Edge, Edge> edgeToNextEdge;
    private final Map<Edge, Edge> edgeToPreviousEdge;
    private final Map<Vertex, Vertex> vertexToNextVertex;
    private final Map<Vertex, Vertex> vertexToPreviousVertex;

    private BasicAdjacencyList(List<Vertex> verticesInOrder, List<Edge> edgesInOrder) {
        this.neighboringVertices = ImmutableList.<Vertex> copyOf(verticesInOrder);
        this.neighboringEdges = ImmutableList.<Edge> copyOf(edgesInOrder);
        this.vertexToEdge = Maps.newHashMap();
        int numberOfNeighbors = neighboringVertices.size();
        for (int i = 0; i < numberOfNeighbors; i++) {
            vertexToEdge.put(neighboringVertices.get(i), neighboringEdges.get(i));
        }
        edgeToNextEdge = ListToCyclicMap.getForwardsCyclicMap(neighboringEdges);
        edgeToPreviousEdge = ListToCyclicMap.getBackwardsCyclicMap(neighboringEdges);
        vertexToNextVertex = ListToCyclicMap.getForwardsCyclicMap(neighboringVertices);
        vertexToPreviousVertex = ListToCyclicMap.getBackwardsCyclicMap(neighboringVertices);
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
    public Edge getNextClockwise(Edge edge) {
        return edgeToNextEdge.get(edge);
    }

    @Override
    public Edge getPreviousClockwise(Edge edge) {
        return edgeToPreviousEdge.get(edge);
    }

    @Override
    public Vertex getNextClockwise(Vertex vertex) {
        return vertexToNextVertex.get(vertex);
    }

    @Override
    public Vertex getPreviousClockwise(Vertex vertex) {
        return vertexToPreviousVertex.get(vertex);
    }
}
