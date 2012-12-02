package operations;

import graph.Face;
import graph.Graph;
import java.util.Collection;

public class Dualizer {

    private final Graph graph; 
    
    public Dualizer(Graph graph){
	    this.graph = graph;
    }
    
    public Collection<Face> getFaces(Graph graph) {
    	Map <Edge, Integer> edgeCounts = new HashMap <Edge, Integer> ();
    	// TODO use the putAll method to make this faster
    	for (Edge edge : graph.getEdges()){
    		edgeCounts.put(edge, 0);
    	}

    	Collection<Edge> faceEdges;
    	Collection<Face> output = new LinkedList<Face> ();
    	Face newFace;
    	for (Edge edge : graph.getEdges()){
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
    
    public static Graph getDual(Graph graph){
    	Collection<Face> faces = graph.getFaces();
    	Map<Edge, Face> adjacentFacesHash = new HashMap<Edge, Face> ();
    	
    	Face currentAdjFace;
    	FaceVertex newVertex1;
    	FaceVertex newVertex2;
    	FaceEdge newEdge;
    	for (Face currentFace : faces){
    		for (Edge currentEdge : currentFace.getEdges()){
    			currentAdjFace = adjacentFacesHash.get(currentEdge);
    			if (currentAdjFace == null){
    				adjacentFacesHash.put(currentEdge, currentFace);
    			} else {
    				//TODO add these to the graph implementation somehow.
    				newVertex1 = FaceVertex(currentFace, currentAdjFace);
    				newVertex2 = FaceVertex(currentAdjFace, currentFace);
    				newEdge = FaceEdge(newVertex1, newVertex2);
    				
    				adjacentFacesHash.remove(currentAdjFace);
    			}
    		}
    	}
    }
    
}
