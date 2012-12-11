package graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import operations.DualFactory;
import dual.FaceEdge;
import dual.FaceVertex;

public class AdjacencyListGraph <V extends Vertex, E extends Edge<V>> 
	implements Graph<V,E> {

    private final long id;
    private final String name;
    private final Map<V,AdjacencyList<V,E>> adjacencyLists;
    private final Map<Long, Vertex> vertexMap;
    private Graph<FaceVertex<V,E>, FaceEdge<FaceVertex<V,E>>> dual;
    
    protected AdjacencyListGraph(String name, Map<V,AdjacencyList<V,E>> adjacencyLists){
	this.name = name;
	this.id = IdFactory.getId();
	this.adjacencyLists = Collections.unmodifiableMap(adjacencyLists);
	this.vertexMap = createVertexMap(adjacencyLists);
    }
    
    private Map<Long, Vertex> createVertexMap(Map<V,AdjacencyList<V,E>> adjacencyLists){
	Map<Long, Vertex> outputMap = new HashMap<Long, Vertex> ();
	for (V vertex : adjacencyLists.keySet()){
	    outputMap.put(vertex.getId(), vertex);
	}
	return outputMap;
    }
    
    public static <V extends Vertex, E extends Edge<V>>AdjacencyListGraph 
    		create(String name, Map<V,AdjacencyList<V,E>> adjacencyLists){
	return new AdjacencyListGraph(name, adjacencyLists);
    }
    
    @Override
    public long getId() {
	return id;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public Collection<V> getVertices() {
	return adjacencyLists.keySet();
    }

    @Override
    public Collection<E> getEdges() {
	Set<E> allEdges = new HashSet<E> ();
	for (AdjacencyList<V,E> adjList : adjacencyLists){
	    for (E edge : adjList.getNeighboringEdges()){
		allEdges.add(edge);
	    }
	}
	return allEdges;
    }

    @Override
    public Vertex getVertex(Long id) {
	return vertexMap.get(id);
    }

    @Override
    public E getEdgeWithEndpoints(V tail, V head) {
	AdjacencyList<V, E> adjList = adjacencyLists.get(tail);
	return adjList.getEdgeIfAdjacent(head);
    }

    @Override
    public boolean areAdjacent(V vertex1, V vertex2){
	return (areDirectionallyAdjacent(vertex1, vertex2) || 
		areDirectionallyAdjacent(vertex2, vertex1));
    }
    
    @Override
    public boolean areDirectionallyAdjacent(V fromVertex, V toVertex) {
	return (getEdgeWithEndpoints(fromVertex, toVertex) != null);
    }

    @Override
    public Graph getDual() {
	if (this.dual == null){
	    this.dual = DualFactory.getDual(this);
	}
	return this.dual;
    }

    @Override
    public FaceVertex getDual(Vertex vertex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public FaceEdge getDual(Edge edge) {
	// TODO Auto-generated method stub
	return null;
    }

}
