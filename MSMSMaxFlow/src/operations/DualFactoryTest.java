package operations;

import graph.AdjacencyList;
import graph.BasicAdjacencyList;
import graph.BasicEdge;
import graph.BasicGraph;
import graph.BasicVertex;
import graph.Edge;
import graph.Face;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class DualFactoryTest {

    @Test
    public void testGetCounterClockwiseCycle() {
	Graph graph = createTwoCycleGraph();
	DualFactoryResult dfResult = DualFactory.getDual(graph);
	Graph dual = dfResult.getDual();
	
	// The dual will look like a triangle
	assert (dual.getVertices().size() == 3);
	for (Vertex vertex : dual.getVertices()){
	    assert(dual.getNeighboringVertices(vertex).size() == 2);
	}
	
	// There are 5 undirected edges, 10 directed edges in the dual
	Map<Edge,Edge> primalEdgeToDualEdge = dfResult.getPrimalEdgeToDualEdge();
	assert(primalEdgeToDualEdge.size() == 10);
	
	Map<Vertex,Face> primalVertexToDualFace = dfResult.getPrimalVertexToDualFace();
	Face dualFace;
	Face dualFaceNeighbor;
	Edge dualEdge;
	Edge primalEdge;
	
	// Check to sure sure the correct edges separate two adjacent faces of the dual.
	// In particular, for two vertices in the primal graph, their corresponding faces
	// in the dual should be separated by an edge in the dual which is mapped to an
	// edge in the primal separating the two vertices in the primal.
	for (Vertex vertex : graph.getVertices()){
	    dualFace = primalVertexToDualFace.get(vertex);
	    for (Vertex neighbor : graph.getNeighboringVertices(vertex)){
		dualFaceNeighbor = primalVertexToDualFace.get(neighbor);
		dualEdge = dual.getEdgeFromLeftRight(dualFace, dualFaceNeighbor);
		primalEdge = graph.getEdgeFromTailHead(vertex, neighbor);
		
		assert(primalEdgeToDualEdge.get(primalEdge).equals(dualEdge));
	    }
	}
    }
    
    /**
     * Creates a graph that looks like:
     * 
     *   | ------ A -------| 
     *   D        |        B
     *   | ------ C -------|
     * 
     * @return
     */
    public static Graph createTwoCycleGraph(){
	Vertex A = BasicVertex.create("A");
	Vertex B = BasicVertex.create("B");
	Vertex C = BasicVertex.create("C");
	Vertex D = BasicVertex.create("D");
	
	Edge AB = BasicEdge.create(A, B, (long) 5);
	Edge BA = BasicEdge.create(B, A, (long) 0);
	Edge BC = BasicEdge.create(B, C, (long) 5);
	Edge CB = BasicEdge.create(C, B, (long) 0);
	Edge CA = BasicEdge.create(C, A, (long) 0);
	Edge AC = BasicEdge.create(A, C, (long) 5);
	Edge AD = BasicEdge.create(A, D, (long) 5);
	Edge DA = BasicEdge.create(D, A, (long) 0);
	Edge CD = BasicEdge.create(C, D, (long) 0);
	Edge DC = BasicEdge.create(D, C, (long) 5);
	
	List<Edge> adjListEdges = new ArrayList<Edge> (2);
	List<Vertex> adjListVertices = new ArrayList<Vertex> (2);
	Map<Vertex,AdjacencyList> adjacencyLists = new HashMap<Vertex,AdjacencyList> ();
	AdjacencyList adjList;
	
	adjListEdges.add(AB);
	adjListEdges.add(AC);
	adjListEdges.add(AD);
	adjListVertices.add(B);
	adjListVertices.add(C);
	adjListVertices.add(D);
	adjList = BasicAdjacencyList.create(adjListVertices, adjListEdges);
	adjacencyLists.put(A, adjList);
	
	adjListEdges = new ArrayList<Edge> (1);
	adjListVertices = new ArrayList<Vertex> (1);
	adjListEdges.add(BC);
	adjListEdges.add(BA);
	adjListVertices.add(C);
	adjListVertices.add(A);
	adjList = BasicAdjacencyList.create(adjListVertices, adjListEdges);
	adjacencyLists.put(B, adjList);
	
	adjListEdges = new ArrayList<Edge> (1);
	adjListVertices = new ArrayList<Vertex> (1);
	adjListEdges.add(CD);
	adjListEdges.add(CA);
	adjListEdges.add(CB);
	adjListVertices.add(D);
	adjListVertices.add(A);
	adjListVertices.add(B);
	adjList = BasicAdjacencyList.create(adjListVertices, adjListEdges);
	adjacencyLists.put(C, adjList);

	adjListEdges = new ArrayList<Edge> (1);
	adjListVertices = new ArrayList<Vertex> (1);
	adjListEdges.add(DA);
	adjListEdges.add(DC);
	adjListVertices.add(A);
	adjListVertices.add(C);
	adjList = BasicAdjacencyList.create(adjListVertices, adjListEdges);
	adjacencyLists.put(D, adjList);
	
	Graph graph = BasicGraph.create("TwoCycle", adjacencyLists);
	return graph;
    }

}
