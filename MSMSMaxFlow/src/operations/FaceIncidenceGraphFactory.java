package operations;

import graph.Edge;
import graph.Face;
import graph.Graph;
import graph.Vertex;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FaceIncidenceGraphFactory extends DualFactory {
    
    public static Graph getFaceIncidenceGraph(Graph graph){
        Collection<Face> faces = getFaces(graph);
        
        // These two hash sets will contain all the faces that are adjacent to a given
        // edge or vertex.
        Map<Edge,Set<Face>> edgeAdjacentFaces = new HashMap<Edge,Set<Face>> ();
        Map<Vertex,Set<Face>> vertexAdjacentFaces = new HashMap<Vertex,Set<Face>> ();
        for (Face face : faces){
            for (Edge edge : face.getEdges()){
        	addToAdjacentFacesList(edgeAdjacentFaces, edge, face);
            }
            for (Vertex vertex : face.getVertices()){
        	addToAdjacentFacesList(vertexAdjacentFaces, vertex, face);
            }
        }

        Vertex newVertex1;
        Vertex newVertex2;
        Edge newEdge;
        Graph graph = new Graph(); 
        Map<Face,Vertex> faceVertices = new HashMap<Face,Vertex> ();
        for (Set<Face> faceList: edgeAdjacentFaces.values()){
            constructGraphFromAdjacentFaces(faceList.toArray(new Face[]{}), graph, faceVertices);
        }
        for (Set<Face> faceList: vertexAdjacentFaces.values()){
            constructGraphFromAdjacentFaces(faceList.toArray(new Face[]{}), graph, faceVertices);
        }
        return graph;
	
	for (List<Face> faceList: adjacentFacesHash.values()){
	    constructGraphFromAdjacentFaces(faceList, output);
	}
	return output;
    }

}
