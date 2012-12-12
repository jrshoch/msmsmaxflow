package maxflow;

import graph.BasicGraph;
import graph.Vertex;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class MaxFlowAlgorithmsTest {

    private BasicGraph basicGraph;
    private Vertex source;
    private Vertex sink;

    @Before
    @SuppressWarnings("boxing")
    public void setUp() {
	List<Integer> neighbors0 = ImmutableList.of(1, 2, 3);
	List<Integer> neighbors1 = ImmutableList.of(0, 4, 6);
	List<Integer> neighbors2 = ImmutableList.of(0, 7, 10);
	List<Integer> neighbors3 = ImmutableList.of(0, 10, 12, 4);
	List<Integer> neighbors4 = ImmutableList.of(1, 3, 13, 5);
	List<Integer> neighbors5 = ImmutableList.of(4, 15, 6);
	List<Integer> neighbors6 = ImmutableList.of(1, 5, 14, 7);
	List<Integer> neighbors7 = ImmutableList.of(2, 6, 14, 8);
	List<Integer> neighbors8 = ImmutableList.of(7, 15, 9);
	List<Integer> neighbors9 = ImmutableList.of(8, 11, 10);
	List<Integer> neighbors10 = ImmutableList.of(2, 9, 11, 3);
	List<Integer> neighbors11 = ImmutableList.of(9, 15, 12, 10);
	List<Integer> neighbors12 = ImmutableList.of(3, 11, 13);
	List<Integer> neighbors13 = ImmutableList.of(4, 12, 15);
	List<Integer> neighbors14 = ImmutableList.of(6, 15, 7);
	List<Integer> neighbors15 = ImmutableList.of(5, 13, 11, 8, 14);
	@SuppressWarnings("unchecked")
	List<List<Integer>> neighbors = ImmutableList
		.of(neighbors0, neighbors1, neighbors2, neighbors3, neighbors4,
			neighbors5, neighbors6, neighbors7, neighbors8,
			neighbors9, neighbors10, neighbors11, neighbors12,
			neighbors13, neighbors14, neighbors15);
	basicGraph = BasicGraph.create("G", neighbors);
	Map<String, Vertex> nameToVertex = Maps.newHashMap();
	for (Vertex vertex : basicGraph.getVertices()) {
	    nameToVertex.put(vertex.getName(), vertex);
	}
	Vertex v0 = nameToVertex.get("v0");
	Vertex v1 = nameToVertex.get("v1");
	Vertex v2 = nameToVertex.get("v2");
	Vertex v3 = nameToVertex.get("v3");
	Vertex v4 = nameToVertex.get("v4");
	Vertex v5 = nameToVertex.get("v5");
	Vertex v6 = nameToVertex.get("v6");
	Vertex v7 = nameToVertex.get("v7");
	Vertex v8 = nameToVertex.get("v8");
	Vertex v9 = nameToVertex.get("v9");
	Vertex v10 = nameToVertex.get("v10");
	Vertex v11 = nameToVertex.get("v11");
	Vertex v12 = nameToVertex.get("v12");
	Vertex v13 = nameToVertex.get("v13");
	Vertex v14 = nameToVertex.get("v14");
	Vertex v15 = nameToVertex.get("v15");
	basicGraph.getEdgeFromTailHead(v0, v1).setCapacity(10);
	basicGraph.getEdgeFromTailHead(v0, v2).setCapacity(6);
	basicGraph.getEdgeFromTailHead(v0, v3).setCapacity(9);
	basicGraph.getEdgeFromTailHead(v1, v4).setCapacity(5);
	basicGraph.getEdgeFromTailHead(v1, v6).setCapacity(4);
	basicGraph.getEdgeFromTailHead(v2, v7).setCapacity(4);
	basicGraph.getEdgeFromTailHead(v2, v10).setCapacity(4);
	basicGraph.getEdgeFromTailHead(v3, v4).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v3, v10).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v3, v12).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v4, v3).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v4, v13).setCapacity(2);
	basicGraph.getEdgeFromTailHead(v4, v5).setCapacity(5);
	basicGraph.getEdgeFromTailHead(v5, v15).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v6, v5).setCapacity(2);
	basicGraph.getEdgeFromTailHead(v6, v14).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v6, v7).setCapacity(1);
	basicGraph.getEdgeFromTailHead(v7, v6).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v7, v14).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v7, v8).setCapacity(8);
	basicGraph.getEdgeFromTailHead(v8, v15).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v9, v8).setCapacity(2);
	basicGraph.getEdgeFromTailHead(v9, v11).setCapacity(6);
	basicGraph.getEdgeFromTailHead(v10, v9).setCapacity(6);
	basicGraph.getEdgeFromTailHead(v10, v11).setCapacity(1);
	basicGraph.getEdgeFromTailHead(v11, v15).setCapacity(6);
	basicGraph.getEdgeFromTailHead(v12, v11).setCapacity(2);
	basicGraph.getEdgeFromTailHead(v12, v13).setCapacity(2);
	basicGraph.getEdgeFromTailHead(v13, v12).setCapacity(3);
	basicGraph.getEdgeFromTailHead(v13, v15).setCapacity(6);
	basicGraph.getEdgeFromTailHead(v14, v15).setCapacity(3);
	source = v0;
	sink = v15;
    }
    
    @Test
    public void testEdmondsKarp() {
	Assert.assertEquals(19, EdmondsKarp.getMaxFlow(basicGraph, source, sink));
    }
    
    @Test
    public void testErickson() {
	Assert.assertEquals(19, EricksonMaxFlow.getMaxFlow(basicGraph, source, sink));
    }
}
