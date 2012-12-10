package dual;

import graph.Edge;
import graph.IdFactory;
import graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class BasicFace <V extends Vertex, E extends Edge<V>> implements Face<V,E> {

    private final long id;
    private final String name;
    
    private final List<E> edges;
    private List<V> vertices;
    
    
    public BasicFace(String name, List<E> edges){
	this.id = IdFactory.getId();
	this.name = name;
	this.edges = edges;
    }
    
    @Override
    public List<E> getEdgesInOrder() {
	return edges;
    }
    
    private void populateVertices(){	
	vertices = new ArrayList<V> ();
	for (E edge : edges){
	    vertices.add(edge.getHead());
	}
    }
    
    @Override
    public List<V> getVerticesInOrder() {
	if (vertices == null){
	    populateVertices();
	}
	return vertices;
    }

    @Override
    public boolean isOnBoundaryOfFace(E edge) {
	return edges.contains(edge);
    }

    @Override
    public boolean isOnBoundaryOfFace(V vertex) {
	if (vertices == null){
	    populateVertices();
	}
	return vertices.contains(vertex);
    }

    @Override
    public boolean isAdjacentToFace(E edge) {
	V head = edge.getHead();
	V tail = edge.getTail();
	
	if (vertices == null){
	    populateVertices();
	}
	boolean headInside = vertices.contains(head);
	boolean tailInside = vertices.contains(tail);
	if (headInside){
	    return isAdjacentToFace(tail);
	} else if (tailInside){
	    return isAdjacentToFace(head);
	} 
	return false;
    }

    @Override
    public boolean isAdjacentToFace(V vertex) {
	if (vertices == null){
	    populateVertices();
	}
	for (Vertex v : vertices){
	    for (Vertex neighbor : v.getNeighboringVertices()){
		if (vertex.equals(neighbor)){
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public long getId() {
	return id;
    }

    @Override
    public String getName() {
	return name;
    }


}
