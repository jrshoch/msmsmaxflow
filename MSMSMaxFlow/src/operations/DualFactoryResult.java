package operations;

import graph.BasicGraph;
import graph.Edge;
import graph.Face;
import graph.Vertex;

import java.util.Map;

public class DualFactoryResult {

    private final BasicGraph dual;
    private final Map<Edge,Edge> primalEdgeToDualEdge;
    private final Map<Face,Vertex> primalFaceToDualVertex;
    private final Map<Vertex,Face> primalVertexToDualFace;
    
    public DualFactoryResult(BasicGraph dual, Map<Edge,Edge> primalEdgeToDualEdge, 
	    Map<Face,Vertex> primalFaceToDualVertex, 
	    Map<Vertex,Face> primalVertexToDualFace){
	this.dual = dual;
	this.primalEdgeToDualEdge = primalEdgeToDualEdge;
	this.primalFaceToDualVertex = primalFaceToDualVertex;
	this.primalVertexToDualFace = primalVertexToDualFace;
    }

    public BasicGraph getDual() {
	return dual;
    }

    public Map<Edge,Edge> getPrimalEdgeToDualEdge() {
	return primalEdgeToDualEdge;
    }

    public Map<Face,Vertex> getPrimalFaceToDualVertex() {
	return primalFaceToDualVertex;
    }

    public Map<Vertex,Face> getPrimalVertexToDualFace() {
	return primalVertexToDualFace;
    }
    
    
}
