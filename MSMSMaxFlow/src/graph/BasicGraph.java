package graph;

import java.util.Collection;

public class BasicGraph implements Graph {

    private final long id;
    private final String name;
    private final EdgeMatrix edgeMatrix;
    
    private BasicGraph(String name, boolean[][] booleanMatrix) {
	this.id = IdFactory.getId();
	this.name = name;
	this.edgeMatrix = BasicEdgeMatrix.create(booleanMatrix);
    }
    
    public static create BasicGraph(String name, boolean[][] booleanMatrix) {
	return new BasicGraph(name, booleanMatrix);
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
    public Collection<Vertex> getVertices() {
	return edgeMatrix.getVertices();
    }

    @Override
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2) {
	return edgeMatrix.areAdjacent(vertex1, vertex2);
    }

    @Override
    public boolean isDirectionallyAdjacent(Vertex fromVertex, Vertex toVertex) {
	return edgeMatrix.isDirectionallyAdjacent(fromVertex, toVertex);
    }

}
