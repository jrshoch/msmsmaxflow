package graph;

public class BasicFaceEdge implements FaceEdge {

    private final long id;
    private final String name;
    
    private final FaceVertex head;
    private final FaceVertex tail;
    
    public BasicFaceEdge(FaceVertex head, FaceVertex tail){
	this.head = head;
	this.tail = tail;
	this.id = IdFactory.getId();
	this.name = tail.getName() + " -> " + head.getName();
    }
    
    @Override
    public long getId() {
	return id;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public Vertex getHead() {
	return head;
    }

    @Override
    public Vertex getTail() {
	return tail;
    }

    @Override
    public boolean isAdjacentTo(Vertex vertex) {
	if (!(vertex instanceof FaceVertex)){
	    return false;
	}
	return vertex.equals(head) || vertex.equals(tail);
    }

    @Override
    public Face getHeadFace() {
	return head.getFace();
    }

    @Override
    public Face getTailFace() {
	return tail.getFace();
    }

}
