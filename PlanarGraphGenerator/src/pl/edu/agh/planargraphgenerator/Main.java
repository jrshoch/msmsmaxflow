package src.pl.edu.agh.planargraphgenerator;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
public class Main  {
	
	
	public static void main(String[] argS) {

		
	    long startTime = System.currentTimeMillis();
		VDriver vd = new VDriver(1000, 1000);
		
		UndirectedSparseGraph<Pair<Float>, String> g = vd.planarGraph;

		System.out.println("Planar graph generation completed. |V| = "+g.getVertexCount()+", |E| = "+g.getEdgeCount());
		long timeTaken = System.currentTimeMillis() - startTime;
		System.out.println("Time taken: " + timeTaken);
		
	}
}
