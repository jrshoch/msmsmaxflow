package graphgeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import maxflow.MaxFlowProblem;
import src.pl.edu.agh.planargraphgenerator.VDriver;

import com.google.common.collect.Lists;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

public class GeneratePlanarGraph {

    public static MaxFlowProblem generateMaxFlowProblem(int width, int height,
	    long maxCapacityOnRandomWalk, long maxCapacityOffCut, long numPaths)
	    throws NoPathExistsException {
	System.out.println("Generating Voronoi graph with width "
		+ String.valueOf(width) + " and height "
		+ String.valueOf(height));
	VDriver vd = new VDriver(width, height);
	UndirectedSparseGraph<Pair<Float>, String> g = vd.planarGraph;
	if (g.getVertexCount() == 0)
	    return null;
	System.out.println("Got JUNG graph.");
	Graph graph = ConvertJungGraph.convertGraph(g);
	System.out.println("Converted JUNG graph.");

	// Add flow and capacities to our graph.
	List<Vertex> vertices = Lists.newArrayList(graph.getVertices());
	Vertex s = pickRandomElement(vertices);
	Vertex t = null;
	while (t == null || t.equals(s)) {
	    t = pickRandomElement(vertices);
	}
	long maxFlow;
	maxFlow = addCapacitiesToGraph(graph, s, t, maxCapacityOnRandomWalk,
		maxCapacityOffCut, numPaths);
	System.out.println("added capacities to graph.");
	return MaxFlowProblem.create(graph, s, t, maxFlow);
    }

    protected static <T> T pickRandomElement(List<T> elements) {
	double probThreshold = 1.0 / elements.size();

	Random rand = new Random();
	double randomVal = rand.nextDouble();

	double unroundedIndex = (randomVal / probThreshold);
	int index = (int) unroundedIndex;

	return elements.get(index);
    }

    protected static Map<Vertex, Vertex> randomWalkFromSourceToSink(
	    Graph graph, Vertex s, Vertex t) {
	Map<Vertex, Vertex> predecessors = new HashMap<Vertex, Vertex>();
	predecessors.put(s, null);

	Random rand = new Random();
	Vertex vertex = null;
	Vertex randomNeighbor;
	int availableNeighbors;
	while (vertex == null || !vertex.equals(s)) {
	    if (vertex == null) {
		vertex = s;
	    }
	    randomNeighbor = null;
	    availableNeighbors = 0;
	    for (Vertex neighbor : graph.getNeighboringVertices(vertex)) {
		if (neighbor.equals(t)) {
		    predecessors.put(t, vertex);
		    return predecessors;
		}
		if (!predecessors.containsKey(neighbor)) {
		    availableNeighbors += 1;
		    if ((rand.nextDouble() <= 1.0 / availableNeighbors)
			    || availableNeighbors == 1) {
			randomNeighbor = neighbor;
		    }
		}
	    }

	    // Check if we've run out of options, if so, we must backtrack
	    if (randomNeighbor == null) {
		vertex = predecessors.get(vertex);
	    } else {
		predecessors.put(randomNeighbor, vertex);
		vertex = randomNeighbor;
	    }
	}
	return predecessors;
    }

    protected static void addRandomCapacityOnRandomWalk(Graph graph, Vertex s,
	    Vertex t, long maxCapacity) throws NoPathExistsException {
	Map<Vertex, Vertex> predecessors = randomWalkFromSourceToSink(graph, s,
		t);
	if (!predecessors.containsKey(t)) {
	    throw new NoPathExistsException("No path from s to t exists");
	}

	Random rand = new Random();
	long capacityToAdd = (long) (rand.nextFloat() * maxCapacity);

	Vertex vertexTail;
	Vertex vertexHead = t;
	Edge currentEdge;
	while (!(vertexHead.equals(s))) {
	    vertexTail = predecessors.get(vertexHead);
	    currentEdge = graph.getEdgeFromTailHead(vertexTail, vertexHead);
	    currentEdge.setCapacity(currentEdge.getCapacity() + capacityToAdd);
	    vertexTail = vertexHead;
	}
    }

    /**
     * Adds the capacities to the graph given the parameters from the input.
     * Returns the max-flow value of the graph that was generated.
     * 
     * @param graph
     * @param s
     * @param t
     * @param maxCapacityOnRandomWalk
     * @param maxCapacityOffCut
     * @param numPaths
     * @return
     * @throws NoPathExistsException
     */
    public static long addCapacitiesToGraph(Graph graph, Vertex s, Vertex t,
	    long maxCapacityOnRandomWalk, long maxCapacityOffCut, long numPaths)
	    throws NoPathExistsException {
	addCapacitiesFromRandomWalks(graph, s, t, maxCapacityOnRandomWalk,
		numPaths);
	return addCapacityToEdgesOffCut(graph, s, t, maxCapacityOffCut);

    }

    protected static void addCapacitiesFromRandomWalks(Graph graph, Vertex s,
	    Vertex t, long maxCapacity, long numPaths)
	    throws NoPathExistsException {
	for (long i = 0; i < numPaths; i++) {
	    addRandomCapacityOnRandomWalk(graph, s, t, maxCapacity);
	}
    }

    protected static long addCapacityToEdgesOffCut(Graph graph, Vertex s,
	    Vertex t, long maxCapacity) {
	Set<Vertex> sourceHalf = new HashSet<Vertex>(graph.getVertices().size());
	Set<Vertex> sinkHalf = new HashSet<Vertex>(graph.getVertices().size());
	Set<Edge> edgesInCut = new HashSet<Edge>();

	List<Vertex> sourceFrontier = new ArrayList<Vertex>();
	sourceFrontier.add(s);
	List<Vertex> sinkFrontier = new ArrayList<Vertex>();
	sinkFrontier.add(t);
	while (!(sourceFrontier.isEmpty() && sinkFrontier.isEmpty())) {
	    sourceFrontier = getNextFrontier(graph, maxCapacity,
		    sourceFrontier, sourceHalf, sinkHalf, edgesInCut);
	    sinkFrontier = getNextFrontier(graph, maxCapacity, sinkFrontier,
		    sinkHalf, sourceHalf, edgesInCut);
	}

	// Get the max flow in the cut
	long maxFlow = 0;
	for (Edge edge : edgesInCut) {
	    if (sinkHalf.contains(edge.getHead())) {
		maxFlow += edge.getCapacity();
	    }
	}
	return maxFlow;
    }

    protected static List<Vertex> getNextFrontier(Graph graph,
	    long maxCapacity, List<Vertex> currentFrontier,
	    Set<Vertex> homeHalf, Set<Vertex> awayHalf, Set<Edge> edgesInCut) {
	Edge edge;
	long newCapacity;
	Random rand = new Random();
	List<Vertex> newFrontier = new ArrayList<Vertex>();
	for (Vertex tailVertex : currentFrontier) {
	    homeHalf.add(tailVertex);
	    for (Vertex headVertex : graph.getNeighboringVertices(tailVertex)) {
		// We only care if we haven't already seen this edge
		if (!homeHalf.contains(headVertex)) {
		    edge = graph.getEdgeFromTailHead(tailVertex, headVertex);
		    if (awayHalf.contains(headVertex)) {
			// The headVertex is in the other half, which means
			// we've reached
			// one part of the cut.
			edgesInCut.add(edge);
		    } else {
			// Continue moving forward and add some capacity to the
			// edge
			newCapacity = edge.getCapacity()
				+ (long) (rand.nextDouble() * maxCapacity);
			edge.setCapacity(newCapacity);
			newFrontier.add(headVertex);
			homeHalf.add(headVertex);
		    }
		}
	    }
	}
	return newFrontier;
    }

}
