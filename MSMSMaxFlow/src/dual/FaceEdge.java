package dual;

import graph.Edge;

public interface FaceEdge <V extends FaceVertex> extends Edge<V>{

    public Face getHeadFace();
    public Face getTailFace();
    
    public Edge getPrimalEdge();
}
