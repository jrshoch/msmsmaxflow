package operations;

import graph.AdjacencyList;
import graph.AdjacencyListGraph;
import graph.BasicAdjacencyList;
import graph.BasicEdge;
import graph.BasicVertex;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DualFactoryTest {

    @Test
    public void testGetCounterClockwiseCycle() {
	Graph graph = createTwoCycleGraph();
	Graph dual = graph.getDual();
	
	// The dual will look like a triangle
	assert (dual.getVertices().size() == 3);
	
	for (Vertex vertex : dual.getVertices()){
	    assert(dual.getNeighboringVertices(vertex).size() == 2);
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
    private Graph createTwoCycleGraph(){
	Vertex A = BasicVertex.create("A");
	Vertex B = BasicVertex.create("B");
	Vertex C = BasicVertex.create("C");
	Vertex D = BasicVertex.create("D");
	
	Edge AB = BasicEdge.create(A, B);
	Edge BA = BasicEdge.create(B, A);
	Edge BC = BasicEdge.create(B, C);
	Edge CB = BasicEdge.create(C, B);
	Edge CA = BasicEdge.create(C, A);
	Edge AC = BasicEdge.create(A, C);
	Edge AD = BasicEdge.create(A, D);
	Edge DA = BasicEdge.create(D, A);
	Edge CD = BasicEdge.create(C, D);
	Edge DC = BasicEdge.create(D, C);
	
	List<Edge> adjListEdges = new ArrayList<Edge> (2);
	List<Vertex> adjListVertices = new ArrayList<Vertex> (2);
	AdjacencyList adjList;
	
	adjListEdges.add(AB);
	adjListEdges.add(AC);
	adjListEdges.add(AD);
	adjListVertices.add(B);
	adjListVertices.add(C);
	adjListVertices.add(D);
	adjList = BasicAdjacencyList.create(adjListEdges, adjListVertices);
	adjacencyLists.put(A, adjList);
	
	adjListEdges = new ArrayList<Edge> (1);
	adjListVertices = new ArrayList<Vertex> (1);
	adjListEdges.add(BC);
	adjListEdges.add(BA);
	adjListVertices.add(C);
	adjListVertices.add(A);
	adjList = BasicAdjacencyList.create(adjListEdges, adjListVertices);
	adjacencyLists.put(B, adjList);
	
	adjListEdges = new ArrayList<Edge> (1);
	adjListVertices = new ArrayList<Vertex> (1);
	adjListEdges.add(CD);
	adjListEdges.add(CA);
	adjListEdges.add(CB);
	adjListVertices.add(D);
	adjListVertices.add(A);
	adjListVertices.add(B);
	adjList = BasicAdjacencyList.create(adjListEdges, adjListVertices);
	adjacencyLists.put(C, adjList);

	adjListEdges = new ArrayList<Edge> (1);
	adjListVertices = new ArrayList<Vertex> (1);
	adjListEdges.add(DA);
	adjListEdges.add(DC);
	adjListVertices.add(A);
	adjListVertices.add(C);
	adjList = BasicAdjacencyList.create(adjListEdges, adjListVertices);
	adjacencyLists.put(D, adjList);
	
	Graph graph = AdjacencyListGraph.create("TwoCycle", adjacencyLists);
	return graph;
    }

}
