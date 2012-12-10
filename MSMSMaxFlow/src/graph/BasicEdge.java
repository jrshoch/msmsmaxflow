package graph;

public class BasicEdge <T extends Vertex> implements Edge<T> {
    
    private final long id;
    private final String name;
    
    private final T head;
    private final T tail;
    
    protected BasicEdge(T head, T tail) {
	this.id = IdFactory.getId();
	this.name = head.getName() + " -> " + tail.getName();
	this.head = head;
	this.tail = tail;
    }
    
    public static <T extends Vertex> BasicEdge<T> create(T head, T tail) {
	return new BasicEdge<T>(head, tail);
    }
    
    @Override
    public T getHead() {
	return head;
    }

    @Override
    public T getTail() {
	return tail;
    }

    @Override
    public boolean isAdjacentTo(T vertex) {
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
