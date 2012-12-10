package dual;

import graph.BasicEdge;
import graph.Edge;

public class BasicFaceEdge <T extends FaceVertex> extends BasicEdge<T> implements FaceEdge<T> {
    
    private final Edge primalEdge;
    
    public BasicFaceEdge(T head, T tail, Edge primalEdge){
	super(head, tail);
	this.primalEdge = primalEdge;
    }

    @Override
    public boolean isAdjacentTo(T vertex) {
	return vertex.equals(super.getHead()) || vertex.equals(super.getTail());
    }

    @Override
    public Face getHeadFace() {
	return super.getHead().getFace();
    }

    @Override
    public Face getTailFace() {
	return super.getTail().getFace();
    }

    @Override
    public Edge getPrimalEdge() {
	return primalEdge;
    }

}
