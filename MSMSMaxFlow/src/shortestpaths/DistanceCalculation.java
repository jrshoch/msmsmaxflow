package shortestpaths;

import flow.FlowEdge;
import graph.Graph;
import graph.Vertex;

public class DistanceCalculation {
    
    public static <V extends Vertex> long getDijkstraDistance(V a, V b, Graph<V, FlowEdge> graph){
	// Some calculation which returns the distance between vertices a and b;
	if (graph.isDirectionallyAdjacent(a, b)){
	    FlowEdge edge = graph.getEdgeWithEndpoints(a, b);
	    return edge.getResidualFlow();
	}
	return Long.MAX_VALUE;
    }
}
