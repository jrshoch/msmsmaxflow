package operations;

import graph.Face;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceIncidenceGraphFactory extends DualFactory {
    
    public static Graph getFaceIncidenceGraph(Graph graph){
	Collection<Face> faces = getFaces(graph);
	Graph output = getDual(graph);
	
	Map<Vertex,List<Face>> adjacentFacesHash = new HashMap<Vertex,List<Face>> ();
	
	List<Face> currentAdjacentFaces;
	for (Face face : faces){
	    for (Vertex vertex : face.getVertices()){
		//TODO finish this
		if (adjacentFacesHash.containsKey(vertex)){
		    currentAdjacentFaces = adjacentFacesHash.get(vertex);
		} else {
		    currentAdjacentFaces = new ArrayList<Face> ();
		}
		currentAdjacentFaces.add(face);
	    }
	}
	
	for (List<Face> faceList: adjacentFacesHash.values()){
	    constructGraphFromAdjacentFaces(faceList, output);
	}
	return output;
    }

}
