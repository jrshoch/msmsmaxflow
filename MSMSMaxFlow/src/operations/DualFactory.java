package operations;

import graph.Edge;
import graph.EdgeMatrix;
import graph.Graph;
import graph.Vertex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dual.BasicFace;
import dual.BasicFaceEdge;
import dual.BasicFaceVertex;
import dual.Face;
import dual.FaceEdge;
import dual.FaceVertex;

public class DualFactory {

    public static Map<Edge, List<Face>> getFaces(Graph<Vertex, Edge<Vertex>> graph) {
        Map <Edge, Integer> edgeCounts = new HashMap <Edge, Integer> ();
        Map <Edge, List<Face>> facesSharingEdge = new HashMap<Edge, List<Face>> ();
        for (Edge edge : graph.getEdges()){
            if (graph.getEdgeWithEndpoints(edge.getHead(), edge.getTail()) != null){
        	edgeCounts.put(edge, 0);
            }
        }

        List<Edge> faceEdges; 
        List<Face> currentFaceList;
        Face newFace;
        String faceName;
        Edge representativeEdge;
        Edge representativeEdge2;
        for (Edge edge : graph.getEdges()){
            if (edgeCounts.isEmpty()){
                break;
            }
            faceEdges = getCounterClockwiseCycles(edge, edgeCounts, graph);
            faceName = "Face(";
            for (Edge e : faceEdges){
        	faceName += e.getName() + "; ";
            }
            faceName += ")";
            newFace = new BasicFace(faceName, faceEdges);
            
            for (Edge e : faceEdges){
        	representativeEdge = getRepresentativeEdge(e, edgeCounts, graph);
        	if (facesSharingEdge.containsKey(representativeEdge)){
        	    currentFaceList = facesSharingEdge.get(representativeEdge);
        	    currentFaceList.add(newFace);
        	} else {
        	    currentFaceList = new LinkedList<Face> ();
        	    currentFaceList.add(newFace);
        	    facesSharingEdge.put(getRepresentativeEdge(edge, edgeCounts, graph),
        		    currentFaceList);
        	}
            }
        }

        return facesSharingEdge;
    }

    protected static Edge getRepresentativeEdge(Edge edge, Map<Edge, Integer> edgeCounts,
	    Graph graph){
	Edge representativeEdge;
	if (edgeCounts.containsKey(edge)){
	    representativeEdge = edge;
	} else {
	    representativeEdge = graph.getEdgeWithEndpoints(edge.getHead(), edge.getTail());
	}
	return representativeEdge;
    }
    
    /**
     * Finds a counter-clock-wise cycle beginning at the starting edge. 
     * 
     * @param startingEdge
     * @param edgeCounts This will be mutated, with counts being incremented
     * when the edge is used in a cycle.
     * @return A collection of edges which constitute a cycle
     */
    protected static List<Edge> getCounterClockwiseCycles(Edge startingEdge, 
            Map <Edge, Integer> edgeCounts, Graph graph){
        List<Edge> cycleSet = new LinkedList <Edge> ();

        Vertex vertex = startingEdge.getHead();
        Vertex startVertex = startingEdge.getTail();
        Edge nextEdge;
        Edge representativeEdge;
        int previousCount;
        while (!vertex.equals(startVertex)){
            for (Vertex neighbor : vertex.getNeighboringVertices()){
                nextEdge = graph.getEdgeWithEndpoints(vertex, neighbor);
                representativeEdge = getRepresentativeEdge(nextEdge, edgeCounts, graph);

                // If the nextEdge is not in edgeCounts, we have deleted it, and
                // its count is >= 2. 
                previousCount = edgeCounts.get(representativeEdge);
                if (previousCount < 1){
                    edgeCounts.put(representativeEdge, previousCount+1);
                    cycleSet.add(nextEdge);
                    vertex = nextEdge.getHead();
                    break;
                }
            }
        }
        return cycleSet;
    }
    
    protected static FaceVertex getVertexFromFace(Face face, 
	    Map<Face,FaceVertex> faceVertices, EdgeMatrix edgeMatrix){
	FaceVertex output = faceVertices.get(face);
	if (output == null){
	    output = new BasicFaceVertex(face.getName(), face, edgeMatrix);
	    faceVertices.put(face, output);
	    edgeMatrix.insertVertex(output);
	}
	return output;
    }
    
    public static Graph getDual(Graph graph){
        Map<Edge,List<Face>> facesSharingEdge = getFaces(graph);

        EdgeMatrix edgeMatrix = new AdjacencyEdgeMatrix(); 
        Map<Face,FaceVertex> faceVertices = new HashMap<Face,FaceVertex> ();
        FaceVertex vertex1;
        FaceVertex vertex2; 
        Edge representativeEdge;
        FaceEdge newFaceEdge;
        for (Map.Entry<Edge,List<Face>> facesSharingSet: facesSharingEdge.entrySet()){
            vertex1 = getVertexFromFace(facesSharingSet.getValue().get(0),  
        	    faceVertices, edgeMatrix);
            vertex2 = getVertexFromFace(facesSharingSet.getValue().get(1),
        	    faceVertices, edgeMatrix);
            
            representativeEdge = facesSharingSet.getKey();
            // Get the orientation correct
            if (vertex2.existsInFace(representativeEdge)){
        	// edge is in the same orientation as vertex2, which means vertex2 is the
        	// tail of the dual edge
        	newFaceEdge = new BasicFaceEdge(vertex1, vertex2, representativeEdge);
            } else {
        	newFaceEdge = new BasicFaceEdge(vertex2, vertex1, representativeEdge);
            }
            edgeMatrix.insertEdge(newFaceEdge);
        }
        return graph;
    }

}
