package graph;

public interface Edge {

    public long getId();
    public String getName();
    
    public Vertex getHead();
    public Vertex getTail();
    
    public Long getCapacity();
}
