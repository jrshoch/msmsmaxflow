package shortestpaths;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Map;

import com.google.common.collect.Maps;

public class Djikstras {

    public static DistancesAndPredecessors getSingleSourceDistancesAndPredecessors(Graph graph,
            Vertex source) {

        DjikstraQueue queue = DjikstraPriorityQueue.create(source, graph.getVertices());
        Map<Vertex, Long> distanceVertexHash = Maps.newHashMap();
        Map<Vertex, Vertex> predecessors = Maps.newHashMap();

        // Start running Dijkstra's
        DistanceVertexPair currentDVPair;
        while (!queue.isEmpty()) {
            currentDVPair = queue.getAndRemoveNearestUnvisited();
            Vertex tail = currentDVPair.getVertex();
            long distance = currentDVPair.getDistance();
            distanceVertexHash.put(tail, new Long(distance));

            for (Edge edge : graph.getNeighboringEdges(tail)) {
        	long capacity = edge.getCapacity();
        	if (capacity == 0) {
        	    continue;
        	}
                long newDistance = distance + capacity;
                Vertex head = edge.getHead();
                if (queue.decreaseKey(head, newDistance)) {
                    predecessors.put(head, tail);
                }
            }
        }

        return new DistancesAndPredecessors(distanceVertexHash, predecessors);
    }

}
