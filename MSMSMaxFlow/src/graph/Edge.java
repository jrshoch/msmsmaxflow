package graph;

public interface Edge extends Named {

    public long getId();
    
    public Vertex getTail();
    public Vertex getHead();
    
    public long getCapacity();
    public void setCapacity(long capacity);
}
