package graph;

import dual.FaceEdge;
import dual.FaceVertex;

public interface PrimalGraph<FV extends FaceVertex<V,E>, FE extends FaceEdge<FV>,
	V extends Vertex, E extends Edge<V>> extends Graph<V,E> {
    
    public DualGraph<FV,FE,V,E> getDual();
    public FV getDual(V vertex);
    public FE getDual(E edge);

}
