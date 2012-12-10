package shortestpaths;

import graph.Graph;
import graph.Vertex;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Dijkstras {
    
    public static ShortestPathsTree getShortestPathsTree(Graph graph, Vertex source){
	Map<Vertex,Vertex> predecessors = getShortestPathsPredecessors(graph, source);
	ShortestPathsTree output = new ShortestPathsTree(predecessors, source);
	return output;
    }
    
    public static Map<Vertex,Vertex> getShortestPathsPredecessors(Graph graph, Vertex source){
	
	Collection<Vertex> allVertices = graph.getVertices();
	Comparator<DistanceVertexPair> comparator = new DistanceVertexPairComparator();
	PriorityQueue<DistanceVertexPair> queue = new PriorityQueue<DistanceVertexPair>(allVertices.size(), comparator);
	Collection<Vertex> sourceNeighbors = source.getNeighboringVertices();
	BigDecimal currentDistance;
	Map<Vertex,DistanceVertexPair> distanceVertexHash = new HashMap<Vertex,DistanceVertexPair>();
	Map<Vertex,Vertex> predecessors = new HashMap<Vertex,Vertex> ();
	
	//Initialize the priority queue of distance vertex pairs.
	DistanceVertexPair currentDVPair;
	for (Vertex v : sourceNeighbors){
	    currentDistance = DistanceCalculation.getDijkstraDistance(source, v);
	    currentDVPair = new DistanceVertexPair(v, currentDistance);
	    queue.add(currentDVPair);
	    distanceVertexHash.put(v, currentDVPair);
	    predecessors.put(v, source);
	}
	for (Vertex v : graph.getVertices()){
	    if (!queue.contains(v)){
		currentDistance = DistanceCalculation.getDijkstraDistance(source, v);
	    	currentDVPair = new DistanceVertexPair(v, currentDistance);
	    	queue.add(currentDVPair);
	    	distanceVertexHash.put(v, currentDVPair);
	    }
	}
	
	// Start running Dijkstra's
	Vertex vertex; 
	while (!queue.isEmpty()){
	    currentDVPair = queue.remove();
	    vertex = currentDVPair.getVertex();
	    
	    for (Vertex neighbor : vertex.getNeighboringVertices()){
		if (queue.contains(neighbor)){
		    relaxEdges(vertex, neighbor, distanceVertexHash, queue, predecessors);
		}
	    }
	}
	
	return predecessors;
    }
    
    private static void relaxEdges(Vertex vertex, Vertex neighbor, 
	    Map<Vertex,DistanceVertexPair> distanceVertexHash, 
	    PriorityQueue<DistanceVertexPair> queue,
	    Map<Vertex,Vertex> predecessors){
	BigDecimal connectingDistance = DistanceCalculation.getDijkstraDistance(vertex, neighbor);
	BigDecimal previousBestDistance = distanceVertexHash.get(vertex).getDistance();
	DistanceVertexPair neighborDVPair = distanceVertexHash.get(neighbor);
	BigDecimal potentialNewDistance = previousBestDistance.add(connectingDistance);
	if (potentialNewDistance.compareTo(neighborDVPair.getDistance()) < 0){
	    queue.remove(neighborDVPair);
	    neighborDVPair.setDistance(potentialNewDistance);
	    queue.add(neighborDVPair);
	    predecessors.put(neighbor, vertex);
	}	
    }
}
