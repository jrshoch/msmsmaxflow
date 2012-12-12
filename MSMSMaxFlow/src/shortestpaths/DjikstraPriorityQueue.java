package shortestpaths;

import graph.Vertex;

import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.common.collect.Maps;

public class DjikstraPriorityQueue implements DjikstraQueue {

    private final Queue<DistanceVertexPair> queue;
    private final Map<Vertex, DistanceVertexPair> vertexToElement;
    
    private DjikstraPriorityQueue(Vertex source, Collection<Vertex> vertices) {
        this.queue = new PriorityQueue<DistanceVertexPair>(
                vertices.size(), new DistanceVertexPairComparator());
        this.vertexToElement = Maps.newHashMap();
        for (Vertex vertex : vertices) {
            DistanceVertexPair element;
            if (vertex == source) {
                element = DistanceVertexPair.create(vertex, 0);
            } else {
                element = DistanceVertexPair.createInfinite(vertex);
            }
            vertexToElement.put(vertex, element);
            queue.add(element);
        }
    }
    
    public static DjikstraPriorityQueue create(Vertex source, Collection<Vertex> vertices) {
        return new DjikstraPriorityQueue(source, vertices);
    }
    
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    @Override
    public DistanceVertexPair getAndRemoveNearestUnvisited() {
        return queue.remove();
    }

    @Override
    public boolean decreaseKey(Vertex vertex, long newDistance) {
        DistanceVertexPair element = vertexToElement.get(vertex);
        long currentDistance = element.getDistance();
        if (newDistance < currentDistance) {
            if (queue.remove(element)) {
                DistanceVertexPair newElement = DistanceVertexPair.create(vertex, newDistance);
                vertexToElement.put(vertex, element);
                queue.add(newElement);
                return true;
            }
        }
        return false;
    }

}
