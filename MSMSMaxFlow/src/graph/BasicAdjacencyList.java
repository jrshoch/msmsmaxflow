package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicAdjacencyList <V extends Vertex, E extends Edge<V>>  
	implements AdjacencyList<V,E> {

    private final V startingVertex;
    private List<V> verticesList = new ArrayList<V> ();
    private List<E> edgesList = new ArrayList<E> ();
    
    protected BasicAdjacencyList(V startingVertex){
	this.startingVertex = startingVertex;
    }
    
    public static <V extends Vertex, E extends Edge<V>> BasicAdjacencyList create(V startingVertex){
	return new BasicAdjacencyList<V, E>(startingVertex);
    }

    @Override
    public void addAdjacentVertexEdgePair(V vertex, E edge) {
	verticesList.add(vertex);
	edgesList.add(edge);	
    }    
    
    @Override
    public void addAdjacentVertexEdgePair(V vertex, E edge, int index) {
	verticesList.add(index, vertex);
	edgesList.add(index, edge);	
	
    }

    @Override
    public V getStartingVertex() {
	return startingVertex;
    }

    @Override
    public List<V> getNeighboringVertices() {
	return Collections.unmodifiableList(verticesList);
    }

    @Override
    public V removeVertex(V vertex) {
	int index = verticesList.indexOf(vertex);
	return removeVertexByIndex(index);
    }

    @Override
    public E removeEdge(E edge) {
	int index = edgesList.indexOf(edge);
	return removeEdgeByIndex(index);
    }

    @Override
    public V removeVertexByIndex(int index) {
	if (indexOutOfBounds(index)){
	    return null;
	}
	edgesList.remove(index);
	return verticesList.remove(index);
    }

    @Override
    public E removeEdgeByIndex(int index) {
	if (indexOutOfBounds(index)){
	    return null;
	}
	verticesList.remove(index);
	return edgesList.remove(index);
    }
    
    private boolean indexOutOfBounds(int index){
	return (!(index < verticesList.size() && index >= 0));
    }

    @Override
    public List<E> getNeighboringEdges() {
	return Collections.unmodifiableList(edgesList);
    }
}
