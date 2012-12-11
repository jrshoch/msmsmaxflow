package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BasicAdjacencyList <V extends Vertex, E extends Edge<V>>  
	implements AdjacencyList<V,E> {

    private final V startingVertex;
    private List<V> verticesList = new ArrayList<V> ();
    private Map<V,E> vertexEdgeMap = new HashMap<V,E> ();
    private List<E> neighboringEdges;
    private boolean dirty = false;
    
    protected BasicAdjacencyList(V startingVertex){
	this.startingVertex = startingVertex;
    }
    
    public static <V extends Vertex, E extends Edge<V>> BasicAdjacencyList create(V startingVertex){
	return new BasicAdjacencyList<V, E>(startingVertex);
    }

    @Override
    public void addAdjacentVertexEdgePair(V vertex, E edge) {
	verticesList.add(vertex);
	vertexEdgeMap.put(vertex, edge);
	this.dirty = true;
    }    
    
    @Override
    public void addAdjacentVertexEdgePair(V vertex, E edge, int index) {
	verticesList.add(index, vertex);
	vertexEdgeMap.put(vertex, edge);
	this.dirty = true;
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
	int index = verticesList.indexOf(edge.getHead());
	return removeEdgeByIndex(index);
    }

    @Override
    public V removeVertexByIndex(int index) {
	if (indexOutOfBounds(index)){
	    return null;
	}
	this.dirty = true;
	V vertex = verticesList.remove(index);
	vertexEdgeMap.remove(vertex);
	return vertex;
    }

    @Override
    public E removeEdgeByIndex(int index) {
	if (indexOutOfBounds(index)){
	    return null;
	}
	this.dirty = true;
	V vertex = verticesList.remove(index);
	return vertexEdgeMap.remove(vertex);
    }
    
    private boolean indexOutOfBounds(int index){
	return (!(index < verticesList.size() && index >= 0));
    }

    @Override
    public List<E> getNeighboringEdges() {
	if (neighboringEdges != null && !dirty){
	    return neighboringEdges;
	}
	List<E> neighEdges = new LinkedList<E> ();
	for (Map.Entry<V, E> entrySet : vertexEdgeMap.entrySet()){
	    neighEdges.add(entrySet.getValue());
	}
	this.neighboringEdges = Collections.unmodifiableList(neighEdges);
	return neighboringEdges;
    }

    @Override
    public E getEdgeIfAdjacent(V vertex) {
	return vertexEdgeMap.get(vertex);
    }
}
