package graph;

import java.util.Collection;

public class BasicVertex implements Vertex {

    private static long id_counter = 0;

    private final long id;
    private final String name;
    private final EdgeMatrix adjacencyMatrix;

    private Collection<Vertex> adjacentVertices;
    private Collection<Edge> adjacentEdges;

    private BasicVertex(String name, EdgeMatrix adjacencyMatrix) {
	this.name = name;
	this.id = id_counter++;
	this.adjacencyMatrix = adjacencyMatrix;
	this.adjacentVertices = null;
	this.adjacentEdges = null;
    }

    public static BasicVertex create(String name, EdgeMatrix edgeMatrix) {
	return new BasicVertex(name, edgeMatrix);
    }

    @Override
    public long getId() {
	return id;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public Collection<Vertex> getAdjacentVertices() {
	if (adjacentVertices == null) {
	    adjacentVertices = adjacencyMatrix.determineAdjacentVertices(this);
	}
	return adjacentVertices;
    }

    @Override
    public Collection<Edge> getAdjacentEdges() {
	if (adjacentEdges == null) {
	    adjacentEdges = adjacencyMatrix.getAdjacentEdges(this);
	}
	return adjacentEdges;
    }

    @Override
    public boolean isAdjacentTo(Edge edge) {
	return edge.isAdjacentTo(this);
    }

    @Override
    public boolean isAdjacentTo(Vertex vertex) {
	return adjacencyMatrix.areAdjacent(this, vertex);
    }

}
