package graph;

import java.util.List;

public interface AdjacencyList <V extends Vertex, E extends Edge<V>>{

    public void addAdjacentVertexEdgePair(V vertex, E edge);
    public void addAdjacentVertexEdgePair(V vertex, E edge, int index);
    
    public V removeVertex(V vertex);
    public E removeEdge(E edge);
    
    public V removeVertexByIndex(int index);
    public E removeEdgeByIndex(int index);
    
    public V getStartingVertex();
    
    /**
     * A list of the neighboring vertices in counter-clockwise order
     * @return
     */
    public List<V> getNeighboringVertices(); 
    public List<E> getNeighboringEdges();
    
}
