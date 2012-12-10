package dual;

import graph.BasicGraph;
import graph.Graph;

public class DualGraph extends BasicGraph implements Graph {
    
    protected DualGraph(String name, boolean[][] booleanMatrix){
	super(name, booleanMatrix);

    }
    
    public static DualGraph create(String name, boolean[][] booleanMatrix){
	return new DualGraph(name, booleanMatrix);
    }

}
