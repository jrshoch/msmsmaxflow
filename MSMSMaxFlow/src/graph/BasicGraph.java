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
    public Collection<Face> getFaces() {
	Map <Edge, Integer> edgeCounts = new HashMap <Edge, Integer> ();
	// TODO use the putAll method to make this faster
	for (Edge edge : this.getEdges()){
	    edgeCounts.put(edge, 0);
	}
	
	Collection<Edge> faceEdges;
	Collection<Face> output = new LinkedList<Face> ();
	Face newFace;
	for (Edge edge : this.getEdges()){
	    if (edgeCounts.isEmpty()){
		break;
	    }
	    faceEdges = getCounterClockwiseCycles(edge, edgeCounts);
	    newFace = Face(faceEdges);
	    output.add(newFace);
	}
	
	return output;
    }
    
    /**
     * Finds a counter-clock-wise cycle beginning at the starting edge. 
     * 
     * @param startingEdge
     * @param edgeCounts This will be mutated, with counts being incremented
     * when the edge is used in a cycle.
     * @return A collection of edges which constitute a cycle
     */
    private Collection<Edge> getCounterClockwiseCycles(Edge startingEdge, 
	    Map <Edge, Integer> edgeCounts){
	Collection<Edge> cycleSet = new LinkedList <Edge> ();
	
	Vertex vertex = startingEdge.getHead();
	Vertex startVertex = startingEdge.getTail();
	Edge nextEdge;
	int previousCount;
	while (!vertex.equals(startVertex)){
    	    for (Vertex neighbor : vertex.getUndirectedNeighboringVertices()){
    	        nextEdge = getEdgeWithEndpoints(neighbor, vertex);
    	        if (nextEdge == null){
    		    nextEdge = getEdgeWithEndpoints(vertex, neighbor);
    	        }
    	        
    	        // If the nextEdge is not in edgeCounts, we have deleted it, and
    	        // its count is >= 2. 
    	        if (edgeCounts.containsKey(nextEdge)){
    	            previousCount = edgeCounts.get(nextEdge);
        	    if (previousCount < 1){
       	                edgeCounts.put(nextEdge, previousCount+1);
        	    } else {
        		edgeCounts.remove(nextEdge);
        	    }
        	    cycleSet.add(nextEdge);
        	    vertex = nextEdge.getHead();
        	    break;
    	        }
            }
	}
	return cycleSet;
    }

    @Override
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2) {
	// TODO Auto-generated method stub
	return false;
    }

}
