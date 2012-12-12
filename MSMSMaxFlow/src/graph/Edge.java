package graph;

public interface Edge extends Named {

    public long getId();
    
    public Vertex getTail();
    public Vertex getHead();
    
    public Long getCapacity();
    public void setCapacity(Long capacity);
}
