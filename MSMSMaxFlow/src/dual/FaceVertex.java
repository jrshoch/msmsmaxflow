package dual;

import graph.Edge;
import graph.Vertex;

import java.util.List;


public interface FaceVertex extends Vertex {

    public Face getFace();
    public List<Vertex> getFaceVerticesInOrder();
    public List<Edge> getFaceEdgesInOrder();

}
