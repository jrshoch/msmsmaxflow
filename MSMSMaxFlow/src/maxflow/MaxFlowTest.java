package maxflow;

import graph.Graph;
import graph.Vertex;
import operations.DualFactoryTest;

import org.junit.Test;

import slotin.MaximumFlowAlgorithm;

public class MaxFlowTest {

    @Test
    public void testMaxFlowOnTwoCycles(MaximumFlowAlgorithm mfAlg) {
	Graph graph = DualFactoryTest.createTwoCycleGraph();
	Vertex A = null; 
	Vertex C = null;
	for (Vertex v : graph.getVertices()){
	    if (v.getName().equals("A")){
		A = v;
	    } else if (v.getName().equals("C")){
		C = v;
	    }
	}
	assert (mfAlg.getMaxFlow(graph, A, C) == 15);
    }
    
    

}
