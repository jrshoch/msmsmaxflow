package graph;

import java.util.Collection;


public class BasicGraph <V extends Vertex, E extends Edge> implements Graph<V,E> {

    private final long id;
    private final String name;

    private final EdgeMatrix edgeMatrix;
    
    protected BasicGraph(String name, boolean[][] booleanMatrix) {
	this.id = IdFactory.getId();
	this.name = name;
	this.edgeMatrix = BasicEdgeMatrix.create(booleanMatrix);
    }
    
    public static BasicGraph create(String name, boolean[][] booleanMatrix) {
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
    public Collection<V> getVertices() {
	return edgeMatrix.getVertices();
    }

    @Override
    public boolean areAdjacent(V vertex1, V vertex2) {
	return edgeMatrix.areAdjacent(vertex1, vertex2);
    }

    @Override
    public boolean isDirectionallyAdjacent(V fromVertex, V toVertex) {
	return edgeMatrix.isDirectionallyAdjacent(fromVertex, toVertex);
    }

    @Override
    public Collection<E> getEdges() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public E getEdgeWithEndpoints(V vertex1, V vertex2) {
	// TODO Auto-generated method stub
	return null;
    }

}
