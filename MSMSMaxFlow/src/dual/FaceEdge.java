package dual;

import graph.Edge;
import graph.Vertex;

public interface FaceEdge <FV extends FaceVertex<V,E>, V extends Vertex, 
	E extends Edge<V>> extends Edge<V>{

    public Face<V,E> getHeadFace();
    public Face<V,E> getTailFace();
    
    public E getPrimalEdge();
}
