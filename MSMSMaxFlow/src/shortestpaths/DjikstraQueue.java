package shortestpaths;

import graph.Vertex;

public interface DjikstraQueue {
    
    public DistanceVertexPair getAndRemoveNearestUnvisited();
    
    public boolean decreaseKey(Vertex vertex, long newDistance);

    boolean isEmpty();

}
