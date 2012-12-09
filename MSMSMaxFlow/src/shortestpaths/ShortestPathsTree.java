package shortestpaths;

import graph.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestPathsTree implements SpanningTree {
    
    private Map<Vertex,Vertex> predecessors;
    private Map<Vertex,List<Vertex>> successors;
    private Vertex root;
    
    public ShortestPathsTree(Map<Vertex,Vertex> predecessors, Vertex root){
	this.predecessors = predecessors;
	this.successors = createSuccessors(predecessors);
	this.root = root;
    }
    
    @Override
    public Vertex getTail(Vertex vertex){
	return this.predecessors.get(vertex);
    }
    
    private Map<Vertex,List<Vertex>> createSuccessors(Map<Vertex,Vertex> pred){
	Map<Vertex,List<Vertex>> output = new HashMap<Vertex,List<Vertex>> ();
	for (Map.Entry<Vertex,Vertex> entry : pred.entrySet()){
	    Vertex vertex = entry.getValue();
	    if (output.containsKey(vertex)){
		output.get(vertex).add(entry.getKey());
	    } else {
		List<Vertex> newList = new ArrayList<Vertex> ();
		output.put(vertex, newList);
	    }
	}
	return output;
    }

    @Override
    public Collection<Vertex> getVertices() {
	return predecessors.values();
    }

    @Override
    public Vertex getRoot() {
	return root;
    }

    @Override
    public Collection<Vertex> getChildren(Vertex vertex) {
	return successors.get(vertex);
    }
}
