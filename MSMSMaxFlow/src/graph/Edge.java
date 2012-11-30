package graph;

public interface Edge {

    public Vertex getHead();
    public Vertex getTail();
    
    public boolean isAdjacentTo(Vertex vertex);
}
