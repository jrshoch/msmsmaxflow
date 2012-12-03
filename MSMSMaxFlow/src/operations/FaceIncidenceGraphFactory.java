package operations;

import graph.Face;
import graph.Graph;
import graph.Vertex;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FaceIncidenceGraphFactory extends DualFactory {

    public FaceIncidenceGraphFactory(Graph graph) {
	super(graph);
    }
    
    public Graph getFaceIncidenceGraph(){
	Collection<Face> faces = getFaces();
	Graph output = super.getDual();
	
	Map<Vertex,Set<Face>> adjacentFaces = new HashMap<Vertex,Set<Face>> ();
	
	Set<Face> currentlyAdjacent;
	for (Face face : faces){
	    currentlyAdjacent = new HashSet<Face> ();
	    for (Vertex vertex : face.getVertices()){
		//TODO finish this
	    }
	}
    }

}
