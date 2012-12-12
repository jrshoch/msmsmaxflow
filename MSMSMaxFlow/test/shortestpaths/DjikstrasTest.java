package shortestpaths;

import graph.BasicGraph;
import graph.Vertex;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class DjikstrasTest {

    private BasicGraph basicGraph;
    private Vertex v0;
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;
    private Vertex v4;
    private Vertex v5;
    private Vertex v6;
    private Vertex v7;
    private Vertex v8;
    private Vertex v9;
    private Vertex v10;
    private Vertex v11;
    private Vertex v12;
    private Vertex v13;
    private Vertex v14;
    private Vertex v15;
    private Vertex source;

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
	v0 = nameToVertex.get("v0");
	v1 = nameToVertex.get("v1");
	v2 = nameToVertex.get("v2");
	v3 = nameToVertex.get("v3");
	v4 = nameToVertex.get("v4");
	v5 = nameToVertex.get("v5");
	v6 = nameToVertex.get("v6");
	v7 = nameToVertex.get("v7");
	v8 = nameToVertex.get("v8");
	v9 = nameToVertex.get("v9");
	v10 = nameToVertex.get("v10");
	v11 = nameToVertex.get("v11");
	v12 = nameToVertex.get("v12");
	v13 = nameToVertex.get("v13");
	v14 = nameToVertex.get("v14");
	v15 = nameToVertex.get("v15");
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
    }

    @Test
    public void testDjikstras() {
	DistancesAndPredecessors distancesAndPredecessors = Djikstras
		.getSingleSourceDistancesAndPredecessors(basicGraph, source);
	Map<Vertex, Long> distances = distancesAndPredecessors.getDistances();
	Map<Vertex, Vertex> predecessors = distancesAndPredecessors.getPredecessors();
	Assert.assertEquals(10, distances.get(v1).longValue());
	Assert.assertEquals(v0, predecessors.get(v1));
	Assert.assertEquals(6, distances.get(v2).longValue());
	Assert.assertEquals(v0, predecessors.get(v2));
	Assert.assertEquals(9, distances.get(v3).longValue());
	Assert.assertEquals(v0, predecessors.get(v3));
	Assert.assertEquals(12, distances.get(v4).longValue());
	Assert.assertEquals(v3, predecessors.get(v4));
	Assert.assertEquals(15, distances.get(v5).longValue());
	Assert.assertEquals(v6, predecessors.get(v5));
	Assert.assertEquals(13, distances.get(v6).longValue());
	Assert.assertEquals(v7, predecessors.get(v6));
	Assert.assertEquals(10, distances.get(v7).longValue());
	Assert.assertEquals(v2, predecessors.get(v7));
	Assert.assertEquals(13, distances.get(v8).longValue());
	Assert.assertEquals(v9, predecessors.get(v8));
	Assert.assertEquals(11, distances.get(v9).longValue());
	Assert.assertEquals(v11, predecessors.get(v9));
	Assert.assertEquals(10, distances.get(v10).longValue());
	Assert.assertEquals(v2, predecessors.get(v10));
	Assert.assertEquals(11, distances.get(v11).longValue());
	Assert.assertEquals(v10, predecessors.get(v11));
	Assert.assertEquals(11, distances.get(v12).longValue());
	Assert.assertEquals(v11, predecessors.get(v12));
	Assert.assertEquals(13, distances.get(v13).longValue());
	Assert.assertEquals(v12, predecessors.get(v13));
	Assert.assertEquals(13, distances.get(v14).longValue());
	Assert.assertEquals(v7, predecessors.get(v14));
	Assert.assertEquals(16, distances.get(v15).longValue());
	Assert.assertEquals(v8, predecessors.get(v15));
	Assert.assertEquals(0, distances.get(v0).longValue());
	Assert.assertNull(predecessors.get(v0));
    }
}
