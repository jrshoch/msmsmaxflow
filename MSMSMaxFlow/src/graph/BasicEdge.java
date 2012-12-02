package graph;

public class BasicEdge implements Edge {
    
    private final long id;
    private final String name;
    
    private final Vertex head;
    private final Vertex tail;
    
    private BasicEdge(Vertex head, Vertex tail) {
	this.id = IdFactory.getId();
	this.name = head.getName() + " -> " + tail.getName();
	this.head = head;
	this.tail = tail;
    }
    
    public static BasicEdge create(Vertex head, Vertex tail) {
	return new BasicEdge(head, tail);
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
	return vertex.equals(head) || vertex.equals(tail);
    }

    @Override
    public long getId() {
	return id;
    }

    @Override
    public String getName() {
	return name;
    }

}
