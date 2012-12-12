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
import java.util.TreeMap;

public class ConvertJungGraph {

    public Graph convertGraph(UndirectedSparseGraph<Pair<Float>,String> graph){
        Collection<Pair<Float>> vertices = graph.getVertices();
        Float xVal;
        Float yVal;
        
        Map<Integer,List<Integer>> adjacencyLists = 
                new HashMap<Integer,List<Integer>> (graph.getVertexCount());
        Map<Pair<Float>,Integer> cartesianPointToVertex = 
                new HashMap<Pair<Float>,Integer> (graph.getVertexCount());
        
        // Get all the vertices into a hash from a cartesian point to that vertex's number
        int counter = 0;
        for (Pair<Float> vertex : vertices){
            cartesianPointToVertex.put(vertex, counter);
            counter += 1;
        }
        
        // Iterate over all vertices
        double currentAngle;
        Integer currentVertex;
        Map<Integer,Double> vertexToAngle;
        List<Integer> adjList;
        for (Pair<Float> vertex : vertices){
            vertexToAngle = new HashMap<Integer,Double> ();
            for (Pair<Float> neighbor : graph.getNeighbors(vertex)){
                currentAngle = calculateAngleFromVertical(vertex, neighbor);
                currentVertex = cartesianPointToVertex.get(neighbor);
                vertexToAngle.put(currentVertex, currentAngle);
            }
            
            // Sort the hashmap by the angles
            adjList = getSortedAdjacencyList(vertexToAngle);
            adjacencyLists.put(cartesianPointToVertex.get(vertex), adjList);
        }
        
        List<List<Integer>> listOfAdjacencyLists = 
                getListOfAdjacencyLists(graph.getVertexCount(), adjacencyLists);
        return BasicGraph.create(NameFactory.getName(), listOfAdjacencyLists);
    }
    
    private List<List<Integer>> getListOfAdjacencyLists(int numVertices,
            Map<Integer,List<Integer>> adjLists){
        List<List<Integer>> output = new ArrayList<List<Integer>> ();
        for (int i=0; i<numVertices; i++){
            output.add(adjLists.get(i));
        }
        return output;
    }
    
    private List<Integer> getSortedAdjacencyList(Map<Integer,Double> vertexToAngle){
        ValueComparator valueCompare = new ValueComparator(vertexToAngle);
        TreeMap<Integer,Double> sortedMap = new TreeMap<Integer,Double> (valueCompare);
        List<Integer> output = new ArrayList<Integer> (vertexToAngle.size());
        for (Integer i : sortedMap.keySet()){
            output.add(i);
        }
        return output;
    }
    
    public double calculateAngleFromVertical(Pair<Float> vertex1, Pair<Float> vertex2){
        double x = vertex1.getFirst() - vertex2.getFirst();
        double y = vertex2.getSecond() - vertex1.getSecond();
        
        // If we are on the horizontal axis
        if (y == 0){
            if (x > 0){
                return Math.PI/2;
            }
            return -Math.PI/2;
        }
        
        double theta = Math.atan(x/y);
        if (y < 0){
            // If we're in the third or fourth quadrants
            return theta + Math.PI;
        } 
        return theta;
    }
}
