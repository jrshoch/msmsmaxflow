package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class BasicGraph implements Graph {

    private final long id;
    private final String name;
    
    @Override
    public long getId() {
	return this.id;
    }

    @Override
    public String getName() {
	return this.name;
    }

    @Override
    public Collection<Vertex> getVertices() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<Edge> getEdges() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2) {
	// TODO Auto-generated method stub
	return false;
    }

}
