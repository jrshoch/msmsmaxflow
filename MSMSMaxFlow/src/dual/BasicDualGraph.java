package dual;

import graph.AdjacencyList;
import graph.AdjacencyListGraph;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Collection;
import java.util.Map;

public class BasicDualGraph<FV extends FaceVertex<V,E>, FE extends FaceEdge<FV>,
	V extends Vertex, E extends Edge<V>> extends AdjacencyListGraph<V,E>
	implements Graph<V,E> {

    private Graph<V,E> primalGraph;
    private final Map<FV,AdjacencyList<FV,FE>> adjacencyLists;
    
    protected BasicDualGraph(String name, Graph<V,E> primalGraph, 
	    Map<FV,AdjacencyList<FV,FE>> adjacencyLists){
	super(name, adjacencyLists);
	this.primalGraph = primalGraph;
    }
    
    public BasicDualGraph<FV,FE,V,E> create(Graph<V,E> primalGraph, 
	    Map<FV,AdjacencyList<FV,FE>> adjacencyLists){
	String dualName = "Dual(" + primalGraph.getName() + ")";
	return new BasicDualGraph<FV,FE,V,E>(dualName, primalGraph, adjacencyLists);
    }
   

    @Override
    public Collection<V> getVertices() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<E> getEdges() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Vertex getVertex(Long id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public E getEdgeWithEndpoints(V tail, V head) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean areAdjacent(V vertex1, V vertex2) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean areDirectionallyAdjacent(V tail, V head) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Graph<FaceVertex<V, E>, FaceEdge<FaceVertex<V, E>>> getDual() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public FaceVertex<V, E> getDual(V vertex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public FaceEdge<FaceVertex<V, E>> getDual(E edge) {
	// TODO Auto-generated method stub
	return null;
    }
    

}
