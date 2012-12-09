package graph;

import java.util.List;

public interface FaceVertex extends Vertex {

    public Face getFace();
    public List<Vertex> getFaceVerticesInOrder();
    public List<Edge> getFaceEdgesInOrder();

}
