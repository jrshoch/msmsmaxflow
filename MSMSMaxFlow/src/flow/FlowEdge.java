package flow;

import graph.Edge;

public interface FlowEdge extends Edge {
    
    public long getFlow();
    public long getCapacity();
    
    public long getResidualFlow();
    
    public void setFlow(long flowValue);
}
