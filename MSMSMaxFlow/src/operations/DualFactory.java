package operations;

import graph.BasicFace;
import graph.Edge;
import graph.Face;
import graph.Graph;
import graph.Vertex;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DualFactory {

    public static Collection<Face> getFaces(Graph graph) {
        Map <Edge, Integer> edgeCounts = new HashMap <Edge, Integer> ();
        for (Edge edge : graph.getEdges()){
            edgeCounts.put(edge, 0);
        }

        Collection<Edge> faceEdges;    // TODO use the putAll method to make this faster
        Collection<Face> output = new LinkedList<Face> ();
        Face newFace;
        for (Edge edge : graph.getEdges()){
            if (edgeCounts.isEmpty()){
                break;
            }
            faceEdges = getCounterClockwiseCycles(edge, edgeCounts, graph);
            newFace = new BasicFace(faceEdges);
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
    private static Collection<Edge> getCounterClockwiseCycles(Edge startingEdge, 
            Map <Edge, Integer> edgeCounts, Graph graph){
        Collection<Edge> cycleSet = new LinkedList <Edge> ();

        Vertex vertex = startingEdge.getHead();
        Vertex startVertex = startingEdge.getTail();
        Edge nextEdge;
        int previousCount;
        while (!vertex.equals(startVertex)){
            for (Vertex neighbor : vertex.getUndirectedNeighboringVertices()){
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
    
    protected static Vertex getVertexFromFace(Face face, Map<Face,Vertex> faceVertices, Graph graph){
	Vertex output = faceVertices.get(face);
	if (output == null){
	    output = new FaceVertex(face);
	    faceVertices.put(face, output);
	    graph.insertVertex(output);
	}
	return output;
    }

    protected static void constructGraphFromAdjacentFaces(Face[] faceList, Graph graph,
	    Map<Face,Vertex> faceVertices){
        Vertex newVertex;
        Edge newEdge1;
        Edge newEdge2;
        
        Vertex rootVertex = getVertexFromFace(faceList[0], faceVertices, graph);
        for (int i=1; i<faceList.length; i++){
            newVertex = getVertexFromFace(faceList[i], faceVertices, graph);
            if (!graph.areAdjacent(rootVertex, newVertex)){
        	newEdge1 = new FaceEdge(rootVertex, newVertex);
        	graph.insertEdge(newEdge1);
            }
            if (!graph.areAdjacent(newVertex, rootVertex)){
        	newEdge2 = new FaceEdge(newVertex, rootVertex);
        	graph.insertEdge(newEdge2);
            }
        }
        
        // Do it recursively for the rest of the faces in the list.
        constructGraphFromAdjacentFaces(Arrays.copyOfRange(faceList, 1, faceList.length-1), graph, faceVertices);
    }

    protected static <T> void addToAdjacentFacesList(Map<T,Set<Face>> adjacentFaces, T graphObject,
	    Face face){
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
        
        // These two hash sets will contain all the faces that are adjacent to a given
        // edge or vertex.
        Map<Edge,Set<Face>> edgeAdjacentFaces = new HashMap<Edge,Set<Face>> ();
        Map<Vertex,Set<Face>> vertexAdjacentFaces = new HashMap<Vertex,Set<Face>> ();
        for (Face face : faces){
            for (Edge edge : face.getEdges()){
        	addToAdjacentFacesList(edgeAdjacentFaces, edge, face);
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
        return graph;
    }

}
