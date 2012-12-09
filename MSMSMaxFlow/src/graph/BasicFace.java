package graph;

import java.util.ArrayList;
import java.util.List;

public class BasicFace implements Face {

    private final long id;
    private final String name;
    
    private final List<Edge> edges;
    private List<Vertex> vertices;
    
    
    public BasicFace(String name, List<Edge> edges){
	this.id = IdFactory.getId();
	this.name = name;
	this.edges = edges;
    }
    
    @Override
    public List<Edge> getEdgesInOrder() {
	return edges;
    }
    
    private void populateVertices(){	
	vertices = new ArrayList<Vertex> ();
	for (Edge edge : edges){
	    vertices.add(edge.getHead());
	}
    }
    
    @Override
    public List<Vertex> getVerticesInOrder() {
	if (vertices == null){
	    populateVertices();
	}
	return vertices;
    }

    @Override
    public boolean isInsideFace(Edge edge) {
	return edges.contains(edge);
    }

    @Override
    public boolean isInsideFace(Vertex vertex) {
	if (vertices == null){
	    populateVertices();
	}
	return vertices.contains(vertex);
    }

    @Override
    public boolean isAdjacentToFace(Edge edge) {
	Vertex head = edge.getHead();
	Vertex tail = edge.getTail();
	
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
    public boolean isAdjacentToFace(Vertex vertex) {
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
