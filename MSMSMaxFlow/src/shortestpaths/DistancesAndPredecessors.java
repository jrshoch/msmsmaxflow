package shortestpaths;

import graph.Vertex;

import java.util.Map;

public class DistancesAndPredecessors {

    private final Map<Vertex, Long> distances;
    private final Map<Vertex, Vertex> predecessors;
    
    public DistancesAndPredecessors(Map<Vertex, Long> distances, Map<Vertex, Vertex> predecessors) {
        this.distances = distances;
        this.predecessors = predecessors;
    }
    
    public Map<Vertex, Long> getDistances() {
        return distances;
    }
    
    public Map<Vertex, Vertex> getPredecessors() {
        return predecessors;
    }
    
}
