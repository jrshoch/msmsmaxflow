package operations;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Map;

import com.google.common.collect.Maps;

public class SlackCalculator {
    
    private SlackCalculator() {
        // Utility class
    }
    
    public static Map<Edge, Long> getSlacksFromDistances(Graph graph, Map<Vertex, Long> distances) {
        Map<Edge, Long> slacks = Maps.newHashMap();
        for (Vertex vertex : graph.getVertices()) {
            for (Edge edge : graph.getNeighboringEdges(vertex)) {
                Vertex tail = edge.getTail();
                Vertex head = edge.getHead();
                long slack = distances.get(tail).longValue() + edge.getCapacity() - distances.get(head).longValue();
                slacks.put(edge, new Long(slack));
            }
        }
        return slacks;
    }

}
