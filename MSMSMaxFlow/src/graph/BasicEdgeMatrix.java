package graph;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BasicEdgeMatrix implements EdgeMatrix {

    private Map<Vertex, Map<Vertex, Edge>> matrix;
    
    private BasicEdgeMatrix(Map<Vertex, Map<Vertex, Edge>> matrix) {
	this.matrix = matrix;
    }
    
    public static BasicEdgeMatrix create(boolean[][] booleanMatrix) {
	Map<Vertex, Map<Vertex, Edge>> matrix = Maps.newHashMap();
	BasicEdgeMatrix result = new BasicEdgeMatrix(matrix);
	int numberOfVertices = booleanMatrix.length;
	List<Vertex> vertices = Lists.newArrayList();
	while (vertices.size() < numberOfVertices) {
	    vertices.add(BasicVertex.create(String.valueOf(vertices.size()), result));
	}
	for (int i = 0; i < numberOfVertices; i++) {
	    Vertex fromVertex = vertices.get(i);
	    Map<Vertex, Edge> vertexMap = Maps.newHashMap();
	    for (int j = 0; j < numberOfVertices; j++) {
		if (booleanMatrix[i][j]) {
		    Vertex toVertex = vertices.get(j);
		    Edge edge = BasicEdge.create(fromVertex, toVertex);
		    vertexMap.put(toVertex, edge);
		}
	    }
	    matrix.put(fromVertex, vertexMap);
	}
	return result;
    }
    
    @Override
    public boolean areAdjacent(Vertex vertex1, Vertex vertex2) {
	return isDirectionallyAdjacent(vertex1, vertex2) ||
		isDirectionallyAdjacent(vertex2, vertex1);
    }

    @Override
    public boolean isDirectionallyAdjacent(Vertex fromVertex, Vertex toVertex) {
	return matrix.get(fromVertex).containsKey(toVertex);
    }

    @Override
    public Edge getEdge(Vertex fromVertex, Vertex toVertex) {
	return isDirectionallyAdjacent(fromVertex, toVertex) ?
		matrix.get(fromVertex).get(toVertex) : null;
    }

    @Override
    public Collection<Vertex> getNeighboringVertices(Vertex fromVertex) {
	return matrix.get(fromVertex).keySet();
    }

    @Override
    public Collection<Edge> getEdgesFrom(Vertex fromVertex) {
	return matrix.get(fromVertex).values();
    }

    @Override
    public Collection<Vertex> getVertices() {
	return matrix.keySet();
    }

}
