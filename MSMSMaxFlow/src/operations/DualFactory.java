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
  
    for (Map.Entry<Edge,List<Face>> entry: adjacentFacesHash.entrySet()){
      Edge edge = entry.getKey();
      List<Face> facesList = entry.getValue();
      
      Face face0 = facesList.get(0);
      Face face1 = facesList.get(1);
      
      newVertex1 = new FaceVertex(face0, face1);
      newVertex2 = new FaceVertex(face1, face0);
      newEdge = new FaceEdge(newVertex1, newVertex2);
      // TODO add the vertices and edges to a graph
    }
    return null;
  }

}
