package graph;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BasicGraph implements Graph {

    private final long id;
    private final String name;

    private final Vertex source;
    private final Vertex sink;

    private final List<Vertex> vertices;
    private final Map<Vertex, AdjacencyList> adjacencyLists;

    private BasicGraph(String name, int numberOfVertices, int source, int sink,
            List<VertexNeighborInfo> neighborInfos) {
        this.name = name;
        this.id = IdFactory.getId();
        List<Vertex> mutableVertices = Lists.newArrayListWithCapacity(numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) {
            mutableVertices.add(BasicVertex.create(String.valueOf(i)));
        }
        this.vertices = ImmutableList.<Vertex> copyOf(mutableVertices);
        this.source = vertices.get(source);
        this.sink = vertices.get(sink);
        this.adjacencyLists = Maps.newHashMap();
        for (int i = 0; i < numberOfVertices; i++) {
            Vertex startingVertex = vertices.get(i);
            VertexNeighborInfo neighborInfo = neighborInfos.get(i);
            List<Integer> neighborIndices = neighborInfo.getNeighbors();
            List<Long> capacities = neighborInfo.getCapacities();
            int numberOfNeighbors = neighborIndices.size();
            List<Vertex> neighbors = Lists.newArrayListWithCapacity(numberOfVertices);
            List<Edge> neighborEdges = Lists.newArrayListWithCapacity(numberOfVertices);
            for (int j = 0; j < numberOfNeighbors; j++) {
                int neighborIndex = neighborIndices.get(j).intValue();
                Long capacity = capacities.get(j);
                Vertex neighbor = vertices.get(neighborIndex);
                Edge neighborEdge = BasicEdge.create(startingVertex, neighbor, capacity);
                neighbors.add(neighbor);
                neighborEdges.add(neighborEdge);
            }
            adjacencyLists.put(startingVertex, BasicAdjacencyList.create(neighbors, neighborEdges));
        }
        Map<Face, List<Edge>> faceEdges = Maps.newHashMap();
        Map<Edge, Face> containingFaces = Maps.newHashMap();
        for (Vertex startVertex : vertices) {
            for (Edge neighborEdge : getNeighboringEdges(startVertex)) {
                Vertex currentVertex = neighborEdge.getHead();
                while (currentVertex != startVertex) {
                    // in progress
                }
            }
        }
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
    public Vertex getSource() {
        return source;
    }

    @Override
    public Vertex getSink() {
        return sink;
    }

    @Override
    public Collection<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public Collection<Face> getFaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Edge getEdgeFromTailHead(Vertex tail, Vertex head) {
        return adjacencyLists.get(tail).getEdgeIfAdjacent(head);
    }

    @Override
    public Edge getEdgeFromLeftRight(Face left, Face right) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Graph getDual() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Edge getDualOf(Edge edge) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Face getDualOf(Vertex vertex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vertex getDualOf(Face face) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Vertex> getNeighboringVertices(Vertex vertex) {
        return adjacencyLists.get(vertex).getNeighboringVertices();
    }

    @Override
    public List<Edge> getNeighboringEdges(Vertex vertex) {
        return adjacencyLists.get(vertex).getNeighboringEdges();
    }

    @Override
    public List<Face> getAdjacentFaces(Face face) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicGraph other = (BasicGraph) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
