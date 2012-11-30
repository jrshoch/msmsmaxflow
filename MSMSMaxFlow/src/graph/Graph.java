package graph;

import java.util.Collection;

public interface Graph<V extends Vertex, E extends Edge> {

    public long getId();
    public String getName();
    
    public Collection<V> getVertices();
    public Collection<E> getEdges();
    
    public boolean areAdjacent(V vertex1, V vertex2);
}
