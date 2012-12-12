package maxflow;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.List;
import java.util.Map;

import shortestpaths.SpanningTree;

public class EricksonMaxFlow {

    public static long getMaxFlow(Graph graph, Vertex source, Vertex sink) {

        Graph dual = graph.getDual();
        // Origin is arbitrary
        Vertex origin = dual.getVertices().iterator().next();
        DistancesAndPredecessors distancesAndPredecessors = Djikstras.getSingleSourceDistances(
                dual, origin);
        Map<Vertex, Long> distances = distancesAndPredecessors.getDistances();
        Map<Vertex, Vertex> predecessors = distancesAndPredecessors.getPredecessors();
        Map<Edge, Long> slacks = SlackCalculator.getSlacksFromDistances(dual, distances);
        SpanningTree spanningTree = SpanningTree.createFromDualSlacks(graph, slacks);
        while (spanningTree.areInSameComponent(source, sink)) {
            List<Edge> path = spanningTree.getPath(source, sink);
            Edge minimumEdge = spanningTree.getMinimumEdgeOnPath(source, sink);
            Long slack = slacks.get(graph.getDualOf(minimumEdge));
            for (Edge pathEdge : path) {
                Edge dualEdge = graph.getDualOf(pathEdge);
                slacks.put(dualEdge, slacks.get(dualEdge) - slack);
                Edge dualReverseEdge = graph.getDualOf(graph.getReverseEdge(pathEdge));
                slacks.put(reverseDualEdge, slacks.get(reverseDualEdge) - slack);
            }
            spanningTree.remove(graph.getDualOf(minimumEdge));
            Vertex minimumEdgeHead = minimumEdge.getHead();
            Vertex headPredecessor = predecessors.get(minimumEdgeHead);
            if (headPredecessor != origin) {
                Edge edgeToHead = graph.getEdgeFromTailHead(headPredecessor, minimumEdgeHead);
                spanningTree.insert(graph.getDualOf(edgeToHead));
            }
            predecessors.put(minimumEdgeHead, minimumEdge.getTail());
        }
        long flow = 0;
        for (Edge neighborEdge : graph.getNeighboringEdges(source)) {
            flow += neighborEdge.getCapacity()
                    - slacks.get(graph.getDualOf(neighborEdge)).longValue();
        }
        return flow;
    }
}
