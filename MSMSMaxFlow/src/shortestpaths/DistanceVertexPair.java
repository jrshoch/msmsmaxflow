package shortestpaths;

import graph.Vertex;

import java.math.BigDecimal;

public class DistanceVertexPair {

    private final Vertex vertex;
    private BigDecimal distance;
    
    public DistanceVertexPair(Vertex vertex){
	this.vertex = vertex;
	this.distance = null;
    }
    
    public DistanceVertexPair(Vertex vertex, BigDecimal distance){
	this.vertex = vertex;
	this.distance = distance;
    }
    
    public BigDecimal getDistance(){
	return distance;
    }
    
    public Vertex getVertex(){
	return vertex;
    }
    
    public void setDistanceToInfinity(){
	this.distance = null;
    }
    
    public void setDistance(BigDecimal distance){
	this.distance = distance;
    }
    
    /**
     * 
     * @param other
     * @return 1 if this distance is greater than other's distance
     * 0 if distances are equal
     * -1 if this distance is less than other's distance
     * 0 if both distances are null (and hence equal to infinity)
     */
    public int compareDistance(DistanceVertexPair other){
	BigDecimal otherDistance = other.getDistance();
	if (otherDistance == null && this.distance != null){
	    return 1;
	} else if (otherDistance != null && this.distance == null){
	    return -1;
	} else if (otherDistance == null && this.distance == null){
	    return 0;
	}
	return this.distance.compareTo(other.getDistance());
    }
    
}
