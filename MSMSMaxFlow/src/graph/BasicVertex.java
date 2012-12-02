package graph;

import java.util.Collection;

public class BasicVertex implements Vertex {

    private static long id_counter = 0;

    private final long id;
    private final String name;
    private final EdgeMatrix edgeMatrix;

    private Collection<Vertex> neighboringVertices;
    private Collection<Edge> edgesFromMe;

    private BasicVertex(String name, EdgeMatrix adjacencyMatrix) {
	this.name = name;
	this.id = id_counter++;
	this.edgeMatrix = adjacencyMatrix;
	this.neighboringVertices = null;
	this.edgesFromMe = null;
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
    public Collection<Vertex> getNeighboringVertices() {
	if (neighboringVertices == null) {
	    neighboringVertices = edgeMatrix.getNeighboringVertices(this);
	}
	return neighboringVertices;
    }

    @Override
    public Collection<Edge> getEdgesFrom() {
	if (edgesFromMe == null) {
	    edgesFromMe = edgeMatrix.getEdgesFrom(this);
	}
	return edgesFromMe;
    }

    @Override
    public boolean isAdjacentTo(Edge edge) {
	return edge.isAdjacentTo(this);
    }

    @Override
    public boolean isAdjacentTo(Vertex vertex) {
	return edgeMatrix.areAdjacent(this, vertex);
    }

}
