package shortestpaths;

import graph.Vertex;

public class DistanceVertexPair {

    private final Vertex vertex;
    private final long distance;

    private DistanceVertexPair(Vertex vertex, long distance) {
        this.vertex = vertex;
        this.distance = distance;
    }
    
    public static DistanceVertexPair create(Vertex vertex, long distance) {
        return new DistanceVertexPair(vertex, distance);
    }

    public static DistanceVertexPair createInfinite(Vertex vertex) {
        return new DistanceVertexPair(vertex, Long.MAX_VALUE);
    }

    public long getDistance() {
        return distance;
    }

    public Vertex getVertex() {
        return vertex;
    }

    @Override
    public String toString() {
	return "(" + vertex.getName() + ", " + String.valueOf(distance) + ")";
    }
}
