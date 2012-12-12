package operations;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

public class DualCapacityUpdater {
    
    private DualCapacityUpdater() {
        // Utility class
    }
    
    public static void updateDualCapacities(Graph graph) {
        for (Vertex vertex : graph.getVertices()) {
            for (Edge edge : graph.getNeighboringEdges(vertex)) {
                graph.getDualOf(edge).setCapacity(edge.getCapacity());
            }
        }
    }
}
