package graph;

import java.util.List;

public class BasicVertexNeighborInfo implements VertexNeighborInfo {

    private final List<Integer> neighbors;
    private final List<Long> capacities;

    private BasicVertexNeighborInfo(List<Integer> neighbors, List<Long> capacities) {
        this.neighbors = neighbors;
        this.capacities = capacities;
    }

    public static BasicVertexNeighborInfo createWithNeighborsAndCapacities(List<Integer> neighbors,
            List<Long> capacities) {
        return new BasicVertexNeighborInfo(neighbors, capacities);
    }

    @Override
    public List<Integer> getNeighbors() {
        return neighbors;
    }

    @Override
    public List<Long> getCapacities() {
        return capacities;
    }

}
