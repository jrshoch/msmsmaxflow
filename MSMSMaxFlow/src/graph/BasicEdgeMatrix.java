package graph;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BasicEdgeMatrix <V extends Vertex, E extends Edge> implements EdgeMatrix<V,E> {

    private Map<V, Map<V, E>> matrix;
    
    private BasicEdgeMatrix(Map<V, Map<V, E>> matrix) {
	this.matrix = matrix;
    }
    
    public static <V extends Vertex,E extends Edge> BasicEdgeMatrix create(boolean[][] booleanMatrix) {
	Map<V, Map<V, E>> matrix = Maps.newHashMap();
	BasicEdgeMatrix result = new BasicEdgeMatrix(matrix);
	int numberOfVertices = booleanMatrix.length;
	List<V> vertices = Lists.newArrayList();
	while (vertices.size() < numberOfVertices) {
	    vertices.add(BasicVertex.create(String.valueOf(vertices.size()), result));
	}
	for (int i = 0; i < numberOfVertices; i++) {
	    V fromVertex = vertices.get(i);
	    Map<V, E> vertexMap = Maps.newHashMap();
	    for (int j = 0; j < numberOfVertices; j++) {
		if (booleanMatrix[i][j]) {
		    V toVertex = vertices.get(j);
		    E edge = BasicEdge.create(fromVertex, toVertex);
		    vertexMap.put(toVertex, edge);
		}
	    }
	    matrix.put(fromVertex, vertexMap);
	}
	return result;
    }
    
    @Override
    public boolean areAdjacent(V vertex1, V vertex2) {
	return isDirectionallyAdjacent(vertex1, vertex2) ||
		isDirectionallyAdjacent(vertex2, vertex1);
    }

    @Override
    public boolean isDirectionallyAdjacent(V fromVertex, V toVertex) {
	return matrix.get(fromVertex).containsKey(toVertex);
    }

    @Override
    public E getEdge(V fromVertex, V toVertex) {
	return isDirectionallyAdjacent(fromVertex, toVertex) ?
		matrix.get(fromVertex).get(toVertex) : null;
    }

    @Override
    public Collection<V> getNeighboringVertices(V fromVertex) {
	return matrix.get(fromVertex).keySet();
    }

    @Override
    public Collection<E> getEdgesFrom(V fromVertex) {
	return matrix.get(fromVertex).values();
    }

    @Override
    public Collection<V> getVertices() {
	return matrix.keySet();
    }

}
