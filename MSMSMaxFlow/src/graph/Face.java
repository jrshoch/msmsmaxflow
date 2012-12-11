package graph;


import java.util.List;

public interface Face <V extends Vertex, E extends Edge<V>>{
    
    public long getId();
    public String getName();
    
    public List<E> getEdgesInOrder();
    public List<V> getVerticesInOrder();
    
    public boolean isOnBoundaryOfFace(E edge);
    public boolean isOnBoundaryOfFace(V vertex);
    
    public boolean isAdjacentToFace(E edge);
    public boolean isAdjacentToFace(V vertex);

}
