package dual;

import graph.Edge;

public interface FaceEdge <T extends FaceVertex> extends Edge<T>{

    public Face getHeadFace();
    public Face getTailFace();
    
    public Edge getPrimalEdge();
}
