package graph;

public interface Edge <T extends Vertex>{

    public long getId();
    public String getName();
    
    public T getHead();
    public T getTail();
    
    public boolean isAdjacentTo(T vertex);
}
