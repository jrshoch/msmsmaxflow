package dual;

import graph.Edge;
import graph.EdgeMatrix;
import graph.IdFactory;
import graph.Vertex;

import java.util.Collection;
import java.util.List;


public class BasicFaceVertex <V extends Vertex, E extends Edge<V>>
	implements FaceVertex {

    private final long id;
    private final String name;
    private final Face face;
    
    private final EdgeMatrix edgeMatrix;
    private Collection<V> neighboringVertices;
    private Collection<E> edgesFromMe;
    
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
    public Collection<V> getNeighboringVertices() {
	if (neighboringVertices == null){
	    neighboringVertices = edgeMatrix.getNeighboringVertices(this);
	}
	return neighboringVertices;
    }

    @Override
    public Collection<E> getEdgesFrom() {
	if (edgesFromMe == null){
	    edgesFromMe = edgeMatrix.getEdgesFrom(this);
	}
	return edgesFromMe;
    }

    @Override
    public boolean isAdjacentTo(E edge) {
	return edge.isAdjacentTo(this);
    }

    @Override
    public boolean isAdjacentTo(V vertex) {
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
    public List<V> getFaceVerticesInOrder() {
	return face.getVerticesInOrder();
    }

    @Override
    public List<E> getFaceEdgesInOrder() {
	return face.getEdgesInOrder();
    }

}
