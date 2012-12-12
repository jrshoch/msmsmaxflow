package maxflow;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EdmondsKarp {

    public static Long getMaxFlow(Graph graph, Vertex s, Vertex t){
	Map<Edge,Long> edgesToFlowHash = new HashMap<Edge,Long> ();
	BreadthFirstFlowSearchResult bfsResult;
	Vertex tailVertex;
	Vertex headVertex;
	Map<Vertex,Vertex> predecessors;
	Edge edge;
	
	long maxFlow = 0;
	long previousFlow; 
	
	while (true){
	    bfsResult = breadthFirstFlowSearch(graph, s, t, edgesToFlowHash);
	    if (bfsResult.getFlow() == 0){
		break;
	    }
	    predecessors = bfsResult.getPredecessors();
	    maxFlow = maxFlow + bfsResult.getFlow();
	    
	    // backtrack through the BFS and write the flow on this path
	    headVertex = t;
	    
	    while (!(headVertex.equals(s))){
		tailVertex = predecessors.get(headVertex);
		
		// For the forward edge, set F = F + m
		edge = graph.getEdgeFromTailHead(tailVertex, headVertex);
		previousFlow = getPreviousFlowFromHash(edge, edgesToFlowHash);
		edgesToFlowHash.put(edge, previousFlow + bfsResult.getFlow());
		
		// For the backward edge, set F = F - m
		edge = graph.getEdgeFromTailHead(headVertex, tailVertex);
		previousFlow = getPreviousFlowFromHash(edge, edgesToFlowHash);
		edgesToFlowHash.put(edge, previousFlow - bfsResult.getFlow());
		
		headVertex = tailVertex;
	    }
	}
	return maxFlow;
    }
    
    private static Long getPreviousFlowFromHash(Edge edge, Map<Edge,Long> edgesToFlowHash){
	if (edgesToFlowHash.containsKey(edge)){
	    return edgesToFlowHash.get(edge);
	} else {
	    edgesToFlowHash.put(edge, (long) 0);
	    return (long) 0;
	}
    }
    
    public static BreadthFirstFlowSearchResult breadthFirstFlowSearch(Graph graph, 
	    Vertex s, Vertex t, Map<Edge,Long> edgesToFlowHash){
	Map<Vertex,Vertex> predecessors = new HashMap<Vertex,Vertex> (graph.getVertices().size());
	Map<Vertex,Long> pathCapacity = new HashMap<Vertex,Long> (graph.getVertices().size());
	
	// Initialize the pathCapacity and predecessors table
	pathCapacity.put(s, Long.MAX_VALUE);
	predecessors.put(s, null);
	
	Queue<Vertex> frontier = new LinkedList<Vertex> ();
	frontier.add(s);
	Vertex vertex;
	Edge edge;
	long newCapacity;
	
	// Start the search
	while (!(frontier.isEmpty())){
	    vertex = frontier.poll();
	    // Look at the neighbors of the current vertex
	    for (Vertex neighbor : graph.getNeighboringVertices(vertex)){
		edge = graph.getEdgeFromTailHead(vertex, neighbor);
		// If the new flow is still greater than 0 and has not yet been searched
		// we process the vertex
		if (edge.getCapacity() - edgesToFlowHash.get(edge) > 0 && 
			!predecessors.containsKey(neighbor)){
		    // newCapacity = min(pathCapacity[vertex], edgeCapacity - Flow)
		    newCapacity = edge.getCapacity() - edgesToFlowHash.get(edge);
		    if (pathCapacity.get(vertex) < newCapacity){
			newCapacity = pathCapacity.get(vertex);
		    }
		    predecessors.put(neighbor, vertex);
		    pathCapacity.put(neighbor, newCapacity);
		    if (neighbor.equals(t)){
			return new BreadthFirstFlowSearchResult(newCapacity, predecessors);
		    } else {
			frontier.add(neighbor);
		    }
		}
	    }
	}
	return new BreadthFirstFlowSearchResult((long) 0, predecessors);
    }
    
}
