package slotin;

import graph.Graph;
import graph.Vertex;
import graphgeneration.GeneratePlanarGraph;
import graphgeneration.NoPathExistsException;
import maxflow.EdmondsKarp;
import maxflow.EricksonMaxFlow;
import maxflow.MaxFlowProblem;

public class RunSingleThreaded {

    public static final long graphsWanted = 500;
    public static final long maxCapacityOnRandomWalk = 30;
    public static final long maxCapacityOffCut = 100;
    public static final long numPaths = 7;

    public static void main(String[] args) {

	GraphDistribution distribution = new UniformGraphDistribution(
		graphsWanted);
	MaxFlowProblem maxFlowProblem;
	long graphsProduced = 0;
	while (graphsProduced < distribution.getNumGraphsWanted()) {
	    try {
		maxFlowProblem = GeneratePlanarGraph.generateMaxFlowProblem(
			distribution.getWidth(), distribution.getHeight(),
			maxCapacityOnRandomWalk, maxCapacityOffCut, numPaths);
		distribution.signalForNextGraph();

		if (maxFlowProblem == null) {
		    continue;
		}
		graphsProduced++;

		Graph graph = maxFlowProblem.getGraph();
		Vertex s = maxFlowProblem.getS();
		Vertex t = maxFlowProblem.getT();
		long startTime = System.nanoTime();
		long maxFlow = EdmondsKarp.getMaxFlow(graph, s, t);
		long endTime = System.nanoTime();

		MaxFlowProblemResult problemResult = new MaxFlowProblemResult(
			"EdmondsKarp", graph.getVertices().size(), 0, endTime
				- startTime, maxFlowProblem.getMaxFlow(),
			maxFlow);
		String edmondsKarpString = "Algorithm: "
			+ problemResult.getAlgorithmName()
			+ " "
			+ ((problemResult.getComputedMaxFlow() == problemResult
				.getCorrectMaxFlow()) ? "CORRECT" : "WRONG")
			+ ", " + "COMPUTED: "
			+ problemResult.getComputedMaxFlow()
			+ ", \"CORRECT\": " + problemResult.getCorrectMaxFlow()
			+ ", " + String.valueOf(problemResult.getNanoTime())
			+ " nanoseconds";
		long edmondsKarpResult = problemResult.getComputedMaxFlow();
		maxFlow = EricksonMaxFlow.getMaxFlow(graph, s, t);
		endTime = System.nanoTime();

		problemResult = new MaxFlowProblemResult("Erickson", graph
			.getVertices().size(), 0, endTime - startTime,
			maxFlowProblem.getMaxFlow(), maxFlow);
		String ericksonsString = "Algorithm: "
			+ problemResult.getAlgorithmName()
			+ " "
			+ ((problemResult.getComputedMaxFlow() == problemResult
				.getCorrectMaxFlow()) ? "CORRECT" : "WRONG")
			+ ", " + "COMPUTED: "
			+ problemResult.getComputedMaxFlow()
			+ ", \"CORRECT\": " + problemResult.getCorrectMaxFlow()
			+ ", " + String.valueOf(problemResult.getNanoTime())
			+ " nanoseconds";
		long ericksonsResult = problemResult.getComputedMaxFlow();
		if (ericksonsResult == edmondsKarpResult) {
		    System.out.println("YIPPEE! " + graph.getVertices().size());
		    System.out.println("  " + edmondsKarpString);
		    System.out.println("  " + ericksonsString);
		}
	    } catch (NoPathExistsException e) {
		e.printStackTrace();
	    } catch (NullPointerException e) {
		continue;
	    } catch (ArrayIndexOutOfBoundsException e) {
		break;
	    }
	}
    }

}
