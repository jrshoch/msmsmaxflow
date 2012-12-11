package operations;

import static org.junit.Assert.fail;
import graph.AdjacencyList;
import graph.BasicAdjacencyList;
import graph.BasicEdge;
import graph.BasicGraph;
import graph.BasicVertex;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DualFactoryTest {

    @Test
    public void testGetCounterClockwiseCycle() {
	
	fail("Not yet implemented");
    }
    
    private Graph<Vertex,Edge<Vertex>> createTwoCycleGraph(){
	Map<Vertex,AdjacencyList<Vertex,Edge<Vertex>>> adjacencyLists = 
		new HashMap<Vertex,AdjacencyList<Vertex,Edge<Vertex>>> ();
 
	Vertex A = BasicVertex.create("A");
	Vertex B = BasicVertex.create("B");
	Vertex C = BasicVertex.create("C");
	Edge<Vertex> AB = BasicEdge.create(B, A);
	Edge<Vertex> BC = BasicEdge.create(C, B);
	Edge<Vertex> CA = BasicEdge.create(A, C);
	
	Vertex D = BasicVertex.create("D");
	Vertex E = BasicVertex.create("E");
	Vertex F = BasicVertex.create("F");
	Edge<Vertex> AD = BasicEdge.create(D, A);
	Edge<Vertex> DE = BasicEdge.create(E, D);
	Edge<Vertex> EF = BasicEdge.create(F, E);
	Edge<Vertex> FB = BasicEdge.create(B, F);
	Edge<Vertex> BA = BasicEdge.create(A, B);
	
	AdjacencyList<Vertex,Edge<Vertex>> adjacencyA = BasicAdjacencyList.create(A);
	adjacencyA.addAdjacentVertexEdgePair(B, AB);
	adjacencyA.addAdjacentVertexEdgePair(D, AD);
	adjacencyLists.put(A, adjacencyA);
	
	AdjacencyList<Vertex,Edge<Vertex>> adjacencyB = BasicAdjacencyList.create(B);
	adjacencyB.addAdjacentVertexEdgePair(B, BC);
	adjacencyLists.put(B, adjacencyB);

	AdjacencyList<Vertex,Edge<Vertex>> adjacencyC = BasicAdjacencyList.create(C);
	adjacencyB.addAdjacentVertexEdgePair(A, CA);
	adjacencyLists.put(C, adjacencyC);
	
	AdjacencyList<Vertex,Edge<Vertex>> adjacencyD = BasicAdjacencyList.create(D);
	adjacencyB.addAdjacentVertexEdgePair(E, DE);
	adjacencyLists.put(D, adjacencyD);
	
	AdjacencyList<Vertex,Edge<Vertex>> adjacencyE = BasicAdjacencyList.create(E);
	adjacencyB.addAdjacentVertexEdgePair(F, EF);
	adjacencyLists.put(E, adjacencyE);
	
	AdjacencyList<Vertex,Edge<Vertex>> adjacencyF = BasicAdjacencyList.create(F);
	adjacencyB.addAdjacentVertexEdgePair(B, FB);
	adjacencyLists.put(F, adjacencyF);
	
	Graph graph = BasicGraph.create("TwoCycle", adjacencyLists);
    }

}
