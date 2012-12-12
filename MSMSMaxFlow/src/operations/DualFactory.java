package operations;

import graph.AdjacencyList;
import graph.BasicAdjacencyList;
import graph.BasicEdge;
import graph.BasicFace;
import graph.BasicGraph;
import graph.BasicVertex;
import graph.Edge;
import graph.Face;
import graph.Graph;
import graph.Named;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class DualFactory {

    public static String getDualName(Named named) {
        return "(" + named.getName() + ")*";
    }

    public static DualFactoryResult getDual(Graph graph) {
        Map<Vertex, Face> dualVertexToPrimalFace = new HashMap<Vertex, Face>();
        Map<Face, Vertex> primalFaceToDualVertex = new HashMap<Face, Vertex>();
        Map<Edge, Edge> primalEdgeToDualEdge = new HashMap<Edge, Edge>();
        Map<Vertex, AdjacencyList> vertexToAdjacencyList = new HashMap<Vertex, AdjacencyList>();
        Map<Face, List<Face>> faceToAdjacentFaces = new HashMap<Face, List<Face>>();

        Vertex dualizedFace;
        for (Face face : graph.getFaces()) {
            dualizedFace = BasicVertex.create("(" + face.getName() + ")*");
            primalFaceToDualVertex.put(face, dualizedFace);
            dualVertexToPrimalFace.put(dualizedFace, face);
        }

        Edge separatingEdge;
        Edge dualizedEdge;
        Vertex tailVertex;
        Vertex headVertex;
        AdjacencyList currentAdjList;
        int arrayListCapacity;

        // Get the edges and vertices in the dual
        for (Face face : graph.getFaces()) {
            tailVertex = primalFaceToDualVertex.get(face);
            arrayListCapacity = graph.getAdjacentFaces(face).size();
            List<Edge> adjListEdges = new ArrayList<Edge>(arrayListCapacity);
            List<Vertex> adjListVertices = new ArrayList<Vertex>(arrayListCapacity);
            for (Face adjFace : graph.getAdjacentFaces(face)) {
                headVertex = primalFaceToDualVertex.get(adjFace);

                separatingEdge = graph.getEdgeFromLeftRight(adjFace, face);
                dualizedEdge = BasicEdge.create(tailVertex, headVertex);

                primalEdgeToDualEdge.put(separatingEdge, dualizedEdge);
                adjListEdges.add(dualizedEdge);
                adjListVertices.add(headVertex);
            }
            currentAdjList = BasicAdjacencyList.create(adjListVertices, adjListEdges);
            vertexToAdjacencyList.put(tailVertex, currentAdjList);
        }

        // Get the adjacent faces in the dual
        Face dualFace;
        Face dualFaceNeighbor;
        Map<Vertex, Face> primalVertexToDualFace = new HashMap<Vertex, Face>();
        for (Vertex vertex : graph.getVertices()) {
            dualFace = BasicFace.create(getDualName(vertex));
            primalVertexToDualFace.put(vertex, dualFace);
        }

        List<Face> adjFacesList;
        for (Vertex vertex : graph.getVertices()) {
            dualFace = primalVertexToDualFace.get(vertex);
            adjFacesList = new ArrayList<Face>(graph.getNeighboringVertices(vertex).size());
            for (Vertex neighbor : graph.getNeighboringVertices(vertex)) {
                dualFaceNeighbor = primalVertexToDualFace.get(neighbor);
                adjFacesList.add(dualFaceNeighbor);
            }
            faceToAdjacentFaces.put(dualFace, adjFacesList);
        }

        // Get the dual's (left face, right face)-> edge map
        Map<Face, Map<Face, Edge>> leftToRightToEdge = Maps.newHashMap();
        for (Vertex tail : graph.getVertices()) {
            Face left = primalVertexToDualFace.get(tail);
            Map<Face, Edge> rightToEdge = Maps.newHashMap();
            for (Edge edge : graph.getNeighboringEdges(tail)) {
                Edge dualEdge = primalEdgeToDualEdge.get(edge);
                Vertex head = edge.getHead();
                rightToEdge.put(primalVertexToDualFace.get(head), dualEdge);
            }
            leftToRightToEdge.put(left, rightToEdge);
        }

        Graph dual = BasicGraph.create(getDualName(graph), vertexToAdjacencyList,
                faceToAdjacentFaces, leftToRightToEdge);
        DualFactoryResult result = new DualFactoryResult(dual, primalEdgeToDualEdge,
                primalFaceToDualVertex, primalVertexToDualFace);

        return result;
    }

}
