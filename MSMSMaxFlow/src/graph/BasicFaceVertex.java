package graph;

import java.util.Collection;
import java.util.List;

public class BasicFaceVertex implements FaceVertex {

    private final long id;
    private final String name;
    private final Face face;
    
    private final EdgeMatrix edgeMatrix;
    private Collection<Vertex> neighboringVertices;
    private Collection<Edge> edgesFromMe;
    
    public BasicFaceVertex(String name, Face face, EdgeMatrix edgeMatrix){
	this.face = face;
	this.id = IdFactory.getId();
	this.name = name;
	this.edgeMatrix = edgeMatrix;
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
	if (neighboringVertices == null){
	    neighboringVertices = edgeMatrix.getNeighboringVertices(this);
	}
	return neighboringVertices;
    }

    @Override
    public Collection<Edge> getEdgesFrom() {
	if (edgesFromMe == null){
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
	// Check if this is the tail and vertex is the head
	for (Vertex neighbor : this.neighboringVertices){
	    if (neighbor.equals(vertex)){
		return true;
	    }
	}
	// Check if vertex is the tail and this is the head
	for (Vertex neighbor : vertex.getNeighboringVertices()){
	    if (neighbor.equals(this)){
		return true;
	    }
	}
	return false;
    }

    @Override
    public Face getFace() {
	return face;
    }

    @Override
    public List<Vertex> getFaceVerticesInOrder() {
	return face.getVerticesInOrder();
    }

    @Override
    public List<Edge> getFaceEdgesInOrder() {
	return face.getEdgesInOrder();
    }

}
