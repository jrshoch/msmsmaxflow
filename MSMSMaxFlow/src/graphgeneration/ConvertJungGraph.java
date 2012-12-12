package graphgeneration;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.BasicGraph;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ConvertJungGraph {

    public static Graph convertGraph(
	    UndirectedSparseGraph<Pair<Float>, String> graph) {
	Collection<Pair<Float>> vertices = graph.getVertices();
	System.out.println("JUNG graph has "
		+ String.valueOf(graph.getVertexCount()) + " vertices.");
	Float xVal;
	Float yVal;

	Map<Integer, List<Integer>> adjacencyLists = new HashMap<Integer, List<Integer>>(
		graph.getVertexCount());
	Map<Pair<Float>, Integer> cartesianPointToVertex = new HashMap<Pair<Float>, Integer>(
		graph.getVertexCount());

	// Get all the vertices into a hash from a cartesian point to that
	// vertex's number
	int counter = 0;
	for (Pair<Float> vertex : vertices) {
	    cartesianPointToVertex.put(vertex, counter);
	    counter += 1;
	}

	// Iterate over all vertices
	double currentAngle;
	Integer currentVertex;
	Map<Integer, Double> vertexToAngle;
	List<Integer> adjList;
	for (Pair<Float> vertex : vertices) {
	    vertexToAngle = new HashMap<Integer, Double>();
	    for (Pair<Float> neighbor : graph.getNeighbors(vertex)) {
		currentAngle = calculateAngleFromVertical(vertex, neighbor);
		currentVertex = cartesianPointToVertex.get(neighbor);
		vertexToAngle.put(currentVertex, currentAngle);
	    }
	    // Sort the hashmap by the angles
	    adjList = getSortedAdjacencyList(vertexToAngle);
	    adjacencyLists.put(cartesianPointToVertex.get(vertex), adjList);
	}

	List<List<Integer>> listOfAdjacencyLists = getListOfAdjacencyLists(
		graph.getVertexCount(), adjacencyLists);
	listOfAdjacencyLists = strip(listOfAdjacencyLists);
	if (listOfAdjacencyLists.size() == 0) return null;
	return BasicGraph.create(NameFactory.getName(), listOfAdjacencyLists);
    }

    private static List<List<Integer>> strip (List<List<Integer>> listOfAdjacencyLists) {
	Queue<Integer> verticesToCheck = Lists.newLinkedList();
	for (int i = 0; i < listOfAdjacencyLists.size(); i++) {
	    verticesToCheck.add(new Integer(i));
	}
	Set<Integer> verticesToRemove = Sets.newHashSet();
	while (!verticesToCheck.isEmpty()) {
	    Integer vertex = verticesToCheck.poll();
	    List<Integer> neighbors = listOfAdjacencyLists.get(vertex);
	    if (neighbors.size() == 0) {
		verticesToRemove.add(vertex);
	    } else if (neighbors.size() == 1) {
		Integer neighbor = neighbors.get(0);
		listOfAdjacencyLists.get(neighbor).remove(vertex);
		verticesToRemove.add(vertex);
		if (!verticesToCheck.contains(neighbor)) {
		    verticesToCheck.add(neighbor);
		}
	    } else if (neighbors.size() == 2) {
		Integer neighbor0 = neighbors.get(0);
		Integer neighbor1 = neighbors.get(1);
		verticesToRemove.add(vertex);
		listOfAdjacencyLists.get(neighbor0).remove(vertex);
		listOfAdjacencyLists.get(neighbor0).add(neighbor1);
		listOfAdjacencyLists.get(neighbor1).remove(vertex);
		listOfAdjacencyLists.get(neighbor1).add(neighbor0);
	    }
	}
	Map<Integer, Integer> oldToNew = Maps.newHashMap();
	Integer counter = 0;
	for (int i = 0; i < listOfAdjacencyLists.size(); i++) {
	    Integer bigI = new Integer(i);
	    if (!verticesToRemove.contains(bigI)) {
		oldToNew.put(bigI, counter++);
	    }
	}
	List<List<Integer>> newListOfAdjacencyLists = Lists.newArrayListWithCapacity(listOfAdjacencyLists.size() - verticesToRemove.size());
	for (int smallI = 0; smallI < listOfAdjacencyLists.size(); smallI++) {
	    Integer i = new Integer(smallI);
	    if (verticesToRemove.contains(i)) {
		continue;
	    }
	    List<Integer> neighbors = listOfAdjacencyLists.get(smallI);
	    List<Integer> newNeighbors = Lists.newArrayList();
	    for (Integer oldNeighbor : neighbors) {
		newNeighbors.add(oldToNew.get(oldNeighbor));
	    }
	    newListOfAdjacencyLists.add(newNeighbors);
	}
	return newListOfAdjacencyLists;
    }

    private static List<List<Integer>> getListOfAdjacencyLists(int numVertices,
	    Map<Integer, List<Integer>> adjLists) {
	List<List<Integer>> output = new ArrayList<List<Integer>>();
	for (int i = 0; i < numVertices; i++) {
	    output.add(adjLists.get(i));
	}
	return output;
    }

    private static List<Integer> getSortedAdjacencyList(
	    Map<Integer, Double> vertexToAngle) {
	ValueComparator valueCompare = new ValueComparator(vertexToAngle);
	TreeMap<Integer, Double> sortedMap = new TreeMap<Integer, Double>(
		valueCompare);
	for (Integer key : vertexToAngle.keySet()) {
	    sortedMap.put(key, vertexToAngle.get(key));
	}
	return Lists.newArrayList(sortedMap.keySet());
    }

    public static double calculateAngleFromVertical(Pair<Float> vertex1,
	    Pair<Float> vertex2) {
	double x = vertex1.getFirst() - vertex2.getFirst();
	double y = vertex2.getSecond() - vertex1.getSecond();

	// If we are on the horizontal axis
	if (y == 0) {
	    if (x > 0) {
		return Math.PI / 2;
	    }
	    return -Math.PI / 2;
	}

	double theta = Math.atan(x / y);
	if (y < 0) {
	    // If we're in the third or fourth quadrants
	    return theta + Math.PI;
	}
	return theta;
    }
}
