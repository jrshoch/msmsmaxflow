package maxflow;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Map;

import operations.DualCapacityUpdater;
import operations.SlackCalculator;
import shortestpaths.DistancesAndPredecessors;
import shortestpaths.Djikstras;
import tree.DynamicSpanningTree;
import tree.NaiveDynamicSpanningTree;

public class EricksonMaxFlow {

    public static long getMaxFlow(Graph graph, Vertex source, Vertex sink) {
        DualCapacityUpdater.updateDualCapacities(graph);
        Graph dual = graph.getDual();
        // Origin is arbitrary
        Vertex origin = dual.getVertices().iterator().next();
        DistancesAndPredecessors distancesAndPredecessors = 
                Djikstras.getSingleSourceDistancesAndPredecessors(dual, origin);
        Map<Vertex, Long> distances = distancesAndPredecessors.getDistances();
        Map<Vertex, Vertex> predecessors = distancesAndPredecessors.getPredecessors();
        Map<Edge, Long> slacks = SlackCalculator.getSlacksFromDistances(dual, distances);
        DynamicSpanningTree spanningTree = NaiveDynamicSpanningTree.createFromDualSlacks(graph,
                slacks);
        while (spanningTree.areInSameComponent(source, sink)) {
            Edge minimumEdge = spanningTree.getMinimumEdgeOnPath(source, sink);
            long slack = spanningTree.getSlack(minimumEdge);
            spanningTree.addSlackOnPath(source, sink, -slack);
            spanningTree.addSlackOnReversePath(source, sink, slack);
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
            flow += neighborEdge.getCapacity() - spanningTree.getSlack(neighborEdge);
        }
        return flow;
    }
}
