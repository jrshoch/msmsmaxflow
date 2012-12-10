package graph;

import java.util.Collection;

public interface EdgeMatrix <V extends Vertex, E extends Edge>{

    public Collection<V> getVertices();
    
    public boolean areAdjacent(V vertex1, V vertex2);
    
    public boolean isDirectionallyAdjacent(V fromVertex, V toVertex);

    public E getEdge(V fromVertex, V toVertex);
    
    public Collection<V> getNeighboringVertices(V fromVertex);
    
    public Collection<E> getEdgesFrom(V fromVertex);
    
    public void insertVertex(V vertex);
    public void insertEdge(E edge);
}
