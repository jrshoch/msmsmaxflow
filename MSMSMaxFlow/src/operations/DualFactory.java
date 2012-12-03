package operations;

import graph.BasicFace;
import graph.Edge;
import graph.Face;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    protected static constructGraphFromAdjacentFaces(List<Face> faceList, Graph graph){
        // Assume that the first face in the list is the original face
        Face rootFace = faceList.get(0);
        Face neighboringFace;
        Vertex vertex;
        for (int i=1; i<faceList.length; i++){
            neighboringFace = faceList.get(i);
            // TODO check to make sure vertex hasn't already been made
            newVertex1 = new FaceVertex(rootFace);
            newVertex2 = new FaceVertex(neighboringFace);
            newEdge = new FaceEdge(newVertex1, newVertex2);
        }
    }

    public static Graph getDual(Graph graph){
        Collection<Face> faces = getFaces(graph);
        Map<Edge, List<Face>> adjacentFacesHash = new HashMap<Edge, List<Face>> ();

        List<Face> currentAdjacentFaces;
        Vertex newVertex1;
        Vertex newVertex2;
        Edge newEdge;
        for (Face face : faces){
            for (Edge edge : face.getEdges()){
                if (adjacentFacesHash.containsKey(edge)){
                    currentAdjacentFaces = adjacentFacesHash.get(edge);
                } else {
                    currentAdjacentFaces = new ArrayList<Face> ();
                }
                currentAdjacentFaces.add(face);
            }
        }

        Graph graph; 
        for (List<Face> faceList: adjacentFacesHash.values()){
            constructGraphFromAdjacentFaces(faceList, graph);
        }
        return graph;
    }

}
