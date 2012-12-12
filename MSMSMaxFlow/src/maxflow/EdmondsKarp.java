package maxflow;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EdmondsKarp {

    public static long getMaxFlow(Graph graph, Vertex s, Vertex t) {
        Map<Edge, Long> edgesToFlowHash = new HashMap<Edge, Long>();
        BreadthFirstFlowSearchResult bfsResult;
        Vertex tailVertex;
        Vertex headVertex;
        Map<Vertex, Vertex> predecessors;
        Edge edge;

        long maxFlow = 0;
        long previousFlow;

        while (true) {
            bfsResult = breadthFirstFlowSearch(graph, s, t, edgesToFlowHash);
            if (bfsResult.getFlow() == 0) {
                break;
            }
            predecessors = bfsResult.getPredecessors();
            maxFlow = maxFlow + bfsResult.getFlow();

            // backtrack through the BFS and write the flow on this path
            headVertex = t;

            while (!(headVertex.equals(s))) {
                tailVertex = predecessors.get(headVertex);

                // For the forward edge, set F = F + m
                edge = graph.getEdgeFromTailHead(tailVertex, headVertex);
                previousFlow = getPreviousFlowFromHash(edge, edgesToFlowHash);
                edgesToFlowHash.put(edge, new Long(previousFlow + bfsResult.getFlow()));

                // For the backward edge, set F = F - m
                edge = graph.getEdgeFromTailHead(headVertex, tailVertex);
                previousFlow = getPreviousFlowFromHash(edge, edgesToFlowHash);
                edgesToFlowHash.put(edge, new Long(previousFlow - bfsResult.getFlow()));

                headVertex = tailVertex;
            }
        }
        return maxFlow;
    }

    private static long getPreviousFlowFromHash(Edge edge, Map<Edge, Long> edgesToFlowHash) {
        if (edgesToFlowHash.containsKey(edge)) {
            return edgesToFlowHash.get(edge).longValue();
        }
        edgesToFlowHash.put(edge, new Long(0));
        return 0;
    }

    public static BreadthFirstFlowSearchResult breadthFirstFlowSearch(Graph graph, Vertex s,
            Vertex t, Map<Edge, Long> edgesToFlowHash) {
        Map<Vertex, Vertex> predecessors = new HashMap<Vertex, Vertex>(graph.getVertices().size());
        Map<Vertex, Long> pathCapacity = new HashMap<Vertex, Long>(graph.getVertices().size());

        // Initialize the pathCapacity and predecessors table
        pathCapacity.put(s, new Long(Long.MAX_VALUE));
        predecessors.put(s, null);

        Queue<Vertex> frontier = new LinkedList<Vertex>();
        frontier.add(s);
        Vertex vertex;
        Edge edge;
        long newCapacity;

        // Start the search
        while (!(frontier.isEmpty())) {
            vertex = frontier.poll();
            // Look at the neighbors of the current vertex
            for (Vertex neighbor : graph.getNeighboringVertices(vertex)) {
                edge = graph.getEdgeFromTailHead(vertex, neighbor);
                // If the new flow is still greater than 0 and has not yet been
                // searched
                // we process the vertex
                if (edge.getCapacity() - edgesToFlowHash.get(edge).longValue() > 0
                        && !predecessors.containsKey(neighbor)) {
                    // newCapacity = min(pathCapacity[vertex], edgeCapacity -
                    // Flow)
                    newCapacity = edge.getCapacity() - edgesToFlowHash.get(edge).longValue();
                    if (pathCapacity.get(vertex).longValue() < newCapacity) {
                        newCapacity = pathCapacity.get(vertex).longValue();
                    }
                    predecessors.put(neighbor, vertex);
                    pathCapacity.put(neighbor, new Long(newCapacity));
                    if (neighbor.equals(t)) {
                        return new BreadthFirstFlowSearchResult(newCapacity, predecessors);
                    }
                    frontier.add(neighbor);
                }
            }
        }
        return new BreadthFirstFlowSearchResult(0, predecessors);
    }

}
