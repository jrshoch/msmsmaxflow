package shortestpaths;

import graph.Vertex;

public class DistanceVertexPair {

    private final Vertex vertex;
    private long distance;
    
    public DistanceVertexPair(Vertex vertex){
	this.vertex = vertex;
	this.distance = Long.MAX_VALUE;
    }
    
    public DistanceVertexPair(Vertex vertex, long distance){
	this.vertex = vertex;
	this.distance = distance;
    }
    
    public long getDistance(){
	return distance;
    }
    
    public Vertex getVertex(){
	return vertex;
    }
    
    public void setDistanceToInfinity(){
	this.distance = Long.MAX_VALUE;
    }
    
    public void setDistance(long distance){
	this.distance = distance;
    }
    
}
