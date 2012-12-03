package operations;

import graph.Graph;
import graph.Vertex;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class BreadthFirstSearch {

    private final Graph graph;

    public BreadthFirstSearch(Graph graph){
	this.graph = graph;
    }

    public Collection<Vertex> getNextLevel(Collection<Vertex> startingVertices, Set<Vertex> alreadyVisited){
	Collection<Vertex> nextLevel = new LinkedList<Vertex> ();
	Collection<Vertex> neighbors;
	for (Vertex vertex : startingVertices){
	    neighbors = vertex.getNeighboringVertices();
	    for (Vertex neighbor : neighbors){
		if (!alreadyVisited.contains(neighbor)){
		    alreadyVisited.add(neighbor);
		    nextLevel.add(neighbor);
		}
	    }
	}
	return nextLevel;
    }

    public LinkedList<Collection<Vertex>> doBFS(Vertex startingVertex){
	LinkedList<Collection<Vertex>>levelsBFS = new LinkedList<Collection<Vertex>> ();
	Set<Vertex> alreadyVisited = new HashSet<Vertex> ();

	Collection<Vertex> currentLevel = new LinkedList<Vertex> ();
	currentLevel.add(startingVertex);
	while (alreadyVisited.size() < this.graph.getVertices().size()){
	    currentLevel = getNextLevel(currentLevel, alreadyVisited);
	    levelsBFS.add(currentLevel);
	}

	return levelsBFS;
    }
}
