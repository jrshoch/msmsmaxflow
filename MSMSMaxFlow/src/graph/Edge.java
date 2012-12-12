package graph;

public interface Edge {

    public long getId();
    public String getName();
    
    public Vertex getTail();
    public Vertex getHead();
    
    public Long getCapacity();
}
