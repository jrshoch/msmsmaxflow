package graph;

import java.util.Collection;

public interface Graph <V extends Vertex, E extends Edge<V>> {

    public long getId();
    public String getName();
    
    public Collection<V> getVertices();
    public Collection<E> getEdges();
    
    public Vertex getVertex(Long id);
    
    public E getEdgeWithEndpoints(V tail, V head);
    
    public boolean areAdjacent(V vertex1, V vertex2);
    public boolean areDirectionallyAdjacent(V tail, V head);
    
}
