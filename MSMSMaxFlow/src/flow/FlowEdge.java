package flow;

import graph.Edge;

import java.math.BigInteger;

public interface FlowEdge extends Edge {
    
    public BigInteger getFlow();
    public BigInteger getCapacity();
    
    public BigInteger getResidualFlow();
    
    public void setFlow(BigInteger flowValue);
}
