package graph;

import java.util.Collection;

public interface FaceVertex extends Vertex {

    public Face getFace();
    public Collection<Vertex> getFaceVerticesInOrder();
    public Collection<Edge> getFaceEdgesInOrder();

}
