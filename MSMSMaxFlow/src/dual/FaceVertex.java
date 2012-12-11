package dual;

import graph.Edge;
import graph.Vertex;

import java.util.List;


public interface FaceVertex <V extends Vertex, E extends Edge> extends Vertex {

    public Face getFace();
    public List<V> getFaceVerticesInOrder();
    public List<E> getFaceEdgesInOrder();
    
    public boolean existsInFace(V vertex);
    public boolean existsInFace(E edge);

}
