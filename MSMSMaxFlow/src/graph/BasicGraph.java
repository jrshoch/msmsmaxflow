package graph;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import operations.DualFactory;
import operations.DualFactoryResult;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BasicGraph implements Graph {

    private final long id;
    private final String name;

    private final List<Vertex> vertices;
    private final List<Face> faces;
    private final Map<Vertex, AdjacencyList> adjacencyLists;
    private final Map<Face, List<Face>> faceToAdjacentFaces;
    private final Map<Face, Map<Face, Edge>> leftToRightToEdge;

    private Graph dual;
    private Map<Edge, Edge> primalEdgeToDualEdge;
    private Map<Vertex, Face> primalVertexToDualFace;
    private Map<Face, Vertex> primalFaceToDualVertex;

    private BasicGraph(String name, Map<Vertex, AdjacencyList> vertexToAdjacencyList,
            Map<Face, List<Face>> faceToAdjacentFaces, Map<Face, Map<Face, Edge>> leftToRightToEdge) {
        this.name = name;
        this.id = IdFactory.getId();
        this.vertices = Lists.newArrayList(vertexToAdjacencyList.keySet());
        this.faces = Lists.newArrayList(faceToAdjacentFaces.keySet());
        this.adjacencyLists = vertexToAdjacencyList;
        this.faceToAdjacentFaces = faceToAdjacentFaces;
        this.leftToRightToEdge = leftToRightToEdge;
    }

    private BasicGraph(String name, List<List<Integer>> neighborIndexLists) {
        this.name = name;
        this.id = IdFactory.getId();
        int numberOfVertices = neighborIndexLists.size();
        List<Vertex> mutableVertices = Lists.newArrayListWithCapacity(numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) {
            mutableVertices.add(BasicVertex.create(BasicVertex.createName(i)));
        }
        this.vertices = ImmutableList.<Vertex> copyOf(mutableVertices);
        this.adjacencyLists = Maps.newHashMap();
        for (int i = 0; i < numberOfVertices; i++) {
            Vertex startingVertex = vertices.get(i);
            List<Integer> neighborIndices = neighborIndexLists.get(i);
            int numberOfNeighbors = neighborIndices.size();
            List<Vertex> neighbors = Lists.newArrayListWithCapacity(numberOfVertices);
            List<Edge> neighborEdges = Lists.newArrayListWithCapacity(numberOfVertices);
            for (int j = 0; j < numberOfNeighbors; j++) {
                int neighborIndex = neighborIndices.get(j).intValue();
                Vertex neighbor = vertices.get(neighborIndex);
                Edge neighborEdge = BasicEdge.create(startingVertex, neighbor);
                neighbors.add(neighbor);
                neighborEdges.add(neighborEdge);
            }
            adjacencyLists.put(startingVertex, BasicAdjacencyList.create(neighbors, neighborEdges));
        }

        Map<Face, List<Edge>> faceToFaceEdges = Maps.newHashMap();
        Map<Edge, Face> edgeToContainingFace = Maps.newHashMap();
        List<Face> mutableFaces = Lists.newLinkedList();
        for (Vertex startVertex : vertices) {
            for (Edge neighborEdge : getNeighboringEdges(startVertex)) {
                Vertex currentVertex = neighborEdge.getHead();
                if (edgeToContainingFace.get(neighborEdge) != null) {
                    continue;
                }
                Edge currentEdge = neighborEdge;
                Face face = BasicFace.create(BasicFace.createName(mutableFaces.size()));
                mutableFaces.add(face);
                List<Edge> faceEdges = Lists.newLinkedList();
                while (currentVertex != startVertex) {
                    faceEdges.add(currentEdge);
                    edgeToContainingFace.put(currentEdge, face);
                    AdjacencyList adjacencyList = adjacencyLists.get(currentVertex);
                    currentVertex = adjacencyList.getPreviousClockwise(currentEdge.getTail());
                    currentEdge = adjacencyList.getEdgeIfAdjacent(currentVertex);
                }
                edgeToContainingFace.put(currentEdge, face);
                faceToFaceEdges.put(face, faceEdges);
            }
        }
        this.faces = ImmutableList.<Face> copyOf(mutableFaces);
        faceToAdjacentFaces = Maps.newHashMap();
        leftToRightToEdge = Maps.newHashMap();
        for (Face face : faces) {
            List<Edge> edges = faceToFaceEdges.get(face);
            List<Face> adjacentFaces = Lists.newArrayListWithCapacity(edges.size());
            Map<Face, Edge> rightToEdge = Maps.newHashMap();
            for (Edge edge : edges) {
                Edge reverseEdge = getReverseEdge(edge);
                Face containingFace = edgeToContainingFace.get(reverseEdge);
                adjacentFaces.add(containingFace);
                rightToEdge.put(containingFace, reverseEdge);
            }
            faceToAdjacentFaces.put(face, ImmutableList.<Face> copyOf(adjacentFaces));
            leftToRightToEdge.put(face, rightToEdge);
        }
    }

    public static BasicGraph create(String name, List<List<Integer>> neighborIndexLists) {
        BasicGraph primal = new BasicGraph(name, neighborIndexLists);
        primal.setDualInfo(DualFactory.getDual(primal));
        return primal;
    }

    public static BasicGraph create(String name, Map<Vertex, AdjacencyList> vertexToAdjacencyList,
            Map<Face, List<Face>> faceToAdjacentFaces, Map<Face, Map<Face, Edge>> leftToRightToEdge) {
        return new BasicGraph(name, vertexToAdjacencyList, faceToAdjacentFaces, leftToRightToEdge);
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
    public Collection<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public Collection<Face> getFaces() {
        return faces;
    }

    @Override
    public Edge getReverseEdge(Edge edge) {
        Vertex head = edge.getHead();
        Vertex tail = edge.getTail();
        return adjacencyLists.get(head).getEdgeIfAdjacent(tail);
    }

    @Override
    public Edge getEdgeFromTailHead(Vertex tail, Vertex head) {
        return adjacencyLists.get(tail).getEdgeIfAdjacent(head);
    }

    @Override
    public Edge getEdgeFromLeftRight(Face left, Face right) {
        return leftToRightToEdge.get(left).get(right);
    }

    public void setDualInfo(DualFactoryResult dualInfo) {
        this.dual = dualInfo.getDual();
        this.primalEdgeToDualEdge = dualInfo.getPrimalEdgeToDualEdge();
        this.primalFaceToDualVertex = dualInfo.getPrimalFaceToDualVertex();
        this.primalVertexToDualFace = dualInfo.getPrimalVertexToDualFace();
    }

    @Override
    public Graph getDual() {
        return dual;
    }

    @Override
    public Edge getDualOf(Edge edge) {
        return primalEdgeToDualEdge.get(edge);
    }

    @Override
    public Face getDualOf(Vertex vertex) {
        return primalVertexToDualFace.get(vertex);
    }

    @Override
    public Vertex getDualOf(Face face) {
        return primalFaceToDualVertex.get(face);
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
        return faceToAdjacentFaces.get(face);
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
