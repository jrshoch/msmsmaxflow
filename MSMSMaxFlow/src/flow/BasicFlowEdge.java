package flow;

import graph.BasicEdge;
import graph.Vertex;

import java.math.BigInteger;

public class BasicFlowEdge  extends BasicEdge implements FlowEdge {

    private BigInteger flow;
    private final BigInteger capacity;
    
    protected BasicFlowEdge(Vertex head, Vertex tail, BigInteger capacity){
	super(head, tail);
	this.capacity = capacity;
    }
    
    public static BasicFlowEdge create(Vertex head, Vertex tail, BigInteger capacity){
	return new BasicFlowEdge(head, tail, capacity);
    }

    @Override
    public BigInteger getFlow() {
	return flow;
    }

    @Override
    public BigInteger getCapacity() {
	return capacity;
    }

    @Override
    public BigInteger getResidualFlow() {
	return capacity.subtract(flow);
    }

    @Override
    public void setFlow(BigInteger flowValue) {
	this.flow = flowValue;
    }

}
