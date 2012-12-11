package dual;

import graph.AdjacencyList;
import graph.DualGraph;
import graph.Edge;
import graph.Graph;
import graph.IdFactory;
import graph.Vertex;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BasicDualGraph<FV extends FaceVertex<V,E>, FE extends FaceEdge<FV,V,E>,
	V extends Vertex, E extends Edge<V>> implements DualGraph<FV,FE,V,E> {

    private final String name;
    private final long id;
    
    private Graph<V,E> primalGraph;
    private Map<V,FV> vertexDuals = new HashMap<V,FV> ();
    private final Map<FV,AdjacencyList<FV,FE>> adjacencyLists;
    private Map<Long,FV> vertexMap;
    
    protected BasicDualGraph(String name, Graph<V,E> primalGraph, 
	    Map<FV,AdjacencyList<FV,FE>> adjacencyLists){
	this.primalGraph = primalGraph;
	this.adjacencyLists = Collections.unmodifiableMap(adjacencyLists);
	this.name = name;
	this.id = IdFactory.getId();
    }
    
    private Map<Long, FV> createVertexMap(Map<FV,AdjacencyList<FV,FE>> adjacencyLists){
	Map<Long, FV> outputMap = new HashMap<Long, FV> ();
	for (FV vertex : adjacencyLists.keySet()){
	    outputMap.put(vertex.getId(), vertex);
	}
	return outputMap;
    }
    
    public BasicDualGraph<FV,FE,V,E> create(Graph<V,E> primalGraph, 
	    Map<FV,AdjacencyList<FV,FE>> adjacencyLists){
	String dualName = "Dual(" + primalGraph.getName() + ")";
	return new BasicDualGraph<FV,FE,V,E>(dualName, primalGraph, adjacencyLists);
    }
   

    public FV getFaceVertexDual(V vertex){
	if (vertexDuals.containsKey(vertex)){
	    return vertexDuals.get(vertex);
	}
	for (FV faceVertex : adjacencyLists.keySet()){
	    if (faceVertex.existsInFace(vertex)){
		// Only slightly aggressive caching because this method probably won't
		// be called very often.
		vertexDuals.put(vertex, faceVertex);
		return faceVertex;
	    }
	}
	return null;
    }
    
    @Override
    public Collection<FV> getVertices() {
	return adjacencyLists.keySet();
    }

    @Override
    public Collection<FE> getEdges() {
	Set<FE> allEdges = new HashSet<FE> ();
	for (AdjacencyList<FV,FE> adjList : adjacencyLists.values()){
	    for (FE edge : adjList.getNeighboringEdges()){
		allEdges.add(edge);
	    }
	}
	return allEdges;
    }

    @Override
    public FV getVertex(Long id) {
	if (vertexMap == null){
	    vertexMap = createVertexMap(adjacencyLists);
	}
	return vertexMap.get(id);
    }

    @Override
    public FE getEdgeWithEndpoints(FV tail, FV head) {
	AdjacencyList<FV,FE> adjList = adjacencyLists.get(tail);
	return adjList.getEdgeIfAdjacent(head);
    }

    @Override
    public boolean areAdjacent(FV vertex1, FV vertex2) {
	return (areDirectionallyAdjacent(vertex1, vertex2) || 
		areDirectionallyAdjacent(vertex2, vertex1));
    }

    @Override
    public boolean areDirectionallyAdjacent(FV tail, FV head) {
	return (getEdgeWithEndpoints(tail, head) != null);
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
    public Graph<V, E> getPrimal() {
	return this.primalGraph;
    }

    @Override
    public Face<V,E> getPrimal(FV vertex) {
	if (adjacencyLists.containsKey(vertex)){
	    return vertex.getFace();
	}
	return null;
    }

    @Override
    public E getPrimal(FE edge) {
	FE edgeFromGraph = getEdgeWithEndpoints(edge.getTail(), edge.getHead());
	if (edgeFromGraph == null){
	    return null;
	}
	return edgeFromGraph.getPrimalEdge();
    }
    

}
