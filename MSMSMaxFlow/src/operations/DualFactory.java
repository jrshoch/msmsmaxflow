package operations;

import graph.Edge;
import graph.EdgeMatrix;
import graph.Graph;
import graph.Vertex;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dual.BasicFace;
import dual.BasicFaceEdge;
import dual.BasicFaceVertex;
import dual.Face;
import dual.FaceVertex;

public class DualFactory {

    public static Collection<Face> getFaces(Graph graph) {
        Map <Edge, Integer> edgeCounts = new HashMap <Edge, Integer> ();
        for (Edge edge : graph.getEdges()){
            edgeCounts.put(edge, 0);
        }

        List<Edge> faceEdges; 
        Collection<Face> output = new LinkedList<Face> ();
        Face newFace;
        String faceName;
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
            output.add(newFace);
        }

        return output;
    }

    /**
     * Finds a counter-clock-wise cycle beginning at the starting edge. 
     * 
     * @param startingEdge
     * @param edgeCounts This will be mutated, with counts being incremented
     * when the edge is used in a cycle.
     * @return A collection of edges which constitute a cycle
     */
    private static List<Edge> getCounterClockwiseCycles(Edge startingEdge, 
            Map <Edge, Integer> edgeCounts, Graph graph){
        List<Edge> cycleSet = new LinkedList <Edge> ();

        Vertex vertex = startingEdge.getHead();
        Vertex startVertex = startingEdge.getTail();
        Edge nextEdge;
        int previousCount;
        while (!vertex.equals(startVertex)){
            for (Vertex neighbor : vertex.getNeighboringVertices()){
                nextEdge = graph.getEdgeWithEndpoints(neighbor, vertex);
                if (nextEdge == null){
                    nextEdge = graph.getEdgeWithEndpoints(vertex, neighbor);
                }

                // If the nextEdge is not in edgeCounts, we have deleted it, and
                // its count is >= 2. 
                if (edgeCounts.containsKey(nextEdge)){
                    previousCount = edgeCounts.get(nextEdge);
                    if (previousCount < 1){
                        edgeCounts.put(nextEdge, previousCount+1);
                    } else {
                        edgeCounts.remove(nextEdge);
                    }
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

    protected static void constructGraphFromAdjacentFaces(Face[] faceList, 
	    EdgeMatrix edgeMatrix, Map<Face,FaceVertex> faceVertices){
        FaceVertex newVertex;
        Edge newEdge1;
        Edge newEdge2;
        
        FaceVertex rootVertex = getVertexFromFace(faceList[0], faceVertices, edgeMatrix);
        for (int i=1; i<faceList.length; i++){
            newVertex = getVertexFromFace(faceList[i], faceVertices, edgeMatrix);
            if (!edgeMatrix.areAdjacent(rootVertex, newVertex)){
        	newEdge1 = new BasicFaceEdge(rootVertex, newVertex);
        	edgeMatrix.insertEdge(newEdge1);
            }
            if (!edgeMatrix.areAdjacent(newVertex, rootVertex)){
        	newEdge2 = new BasicFaceEdge(newVertex, rootVertex);
        	edgeMatrix.insertEdge(newEdge2);
            }
        }
        
        // Do it recursively for the rest of the faces in the list.
        constructGraphFromAdjacentFaces(Arrays.copyOfRange(faceList, 1, 
        	faceList.length-1), edgeMatrix, faceVertices);
    }

    protected static <T> void addToAdjacentFacesList(Map<T,Set<Face>> adjacentFaces, 
	    T graphObject, Face face){
	Set<Face> currentAdjacentFaces;
	if (adjacentFaces.containsKey(graphObject)){
	    currentAdjacentFaces = adjacentFaces.get(graphObject);
	} else {
	    currentAdjacentFaces = new HashSet<Face> ();
	}
	currentAdjacentFaces.add(face);
    }
    
    public static Graph getDual(Graph graph){
        Collection<Face> faces = getFaces(graph);
        
        Map<Edge,Set<Face>> edgeAdjacentFaces = new HashMap<Edge,Set<Face>> ();
        for (Face face : faces){
            for (Edge edge : face.getEdgesInOrder()){
        	addToAdjacentFacesList(edgeAdjacentFaces, edge, face);
            }
        }

        EdgeMatrix edgeMatrix = new AdjacencyEdgeMatrix(); 
        Map<Face,FaceVertex> faceVertices = new HashMap<Face,FaceVertex> ();
        for (Set<Face> faceList: edgeAdjacentFaces.values()){
            constructGraphFromAdjacentFaces(faceList.toArray(new Face[]{}), 
        	    edgeMatrix, faceVertices);
        }
        return graph;
    }

}
