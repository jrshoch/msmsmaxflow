package graph;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class BasicGraphTest {

    private BasicGraph basicGraph;
    
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
        List<List<Integer>> neighbors = ImmutableList.of(neighbors0, neighbors1, neighbors2,
                neighbors3, neighbors4, neighbors5, neighbors6, neighbors7, neighbors8, neighbors9,
                neighbors10, neighbors11, neighbors12, neighbors13, neighbors14, neighbors15);
        basicGraph = BasicGraph.create("G", neighbors);
    }
    
    @Test
    public void testBasicGraph() {
        Assert.assertEquals(16, basicGraph.getVertices().size());
        Assert.assertEquals(14, basicGraph.getFaces().size());
    }
    
    @Test
    public void testDualGraph() {
        Graph dual = basicGraph.getDual();
        Assert.assertEquals(14, dual.getVertices().size());
        Assert.assertEquals(16, dual.getFaces().size());
    }
    
    @Test
    public void testDualGraphEveryEdge() {
        Graph dual = basicGraph.getDual();
        for (Face left : basicGraph.getFaces()) {
            for (Face right : basicGraph.getAdjacentFaces(left)) {
                Edge edge = basicGraph.getEdgeFromLeftRight(left, right);
                Vertex tail = edge.getTail();
                Vertex head = edge.getHead();
                Edge dualEdge = basicGraph.getDualOf(edge);
                Face dualTail = basicGraph.getDualOf(tail);
                Face dualHead = basicGraph.getDualOf(head);
                Assert.assertEquals(dualEdge, dual.getEdgeFromLeftRight(dualTail, dualHead));
            }
        }
    }

}
