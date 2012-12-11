package graph;

import dual.Face;
import dual.FaceEdge;
import dual.FaceVertex;

public interface DualGraph<FV extends FaceVertex<V,E>, FE extends FaceEdge<FV,V,E>,
	V extends Vertex, E extends Edge<V>> extends Graph<FV,FE> {
    
    public Graph<V,E> getPrimal();
    public Face<V,E> getPrimal(FV vertex);
    public E getPrimal(FE edge);
}
