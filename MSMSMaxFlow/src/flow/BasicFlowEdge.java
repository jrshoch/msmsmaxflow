package flow;

import graph.BasicEdge;
import graph.Vertex;


public class BasicFlowEdge extends BasicEdge implements FlowEdge {

    private long flow;
    private final long capacity;
    
    protected BasicFlowEdge(Vertex head, Vertex tail, long capacity){
	super(head, tail);
	this.capacity = capacity;
    }
    
    public static BasicFlowEdge create(Vertex head, Vertex tail, long capacity){
	return new BasicFlowEdge(head, tail, capacity);
    }

    @Override
    public long getFlow() {
	return flow;
    }

    @Override
    public long getCapacity() {
	return capacity;
    }

    @Override
    public long getResidualFlow() {
	return (this.capacity - this.flow);
    }

    @Override
    public void setFlow(long flowValue) {
	this.flow = flowValue;
    }

}
