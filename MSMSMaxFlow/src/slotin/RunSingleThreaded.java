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
	long numberOfTimesToRun = 1000;
	for (int i = 0; i < numberOfTimesToRun; i++) {
	    while (graphsProduced < distribution.getNumGraphsWanted()) {
		try {
		    maxFlowProblem = GeneratePlanarGraph
			    .generateMaxFlowProblem(distribution.getWidth(),
				    distribution.getHeight(),
				    maxCapacityOnRandomWalk, maxCapacityOffCut,
				    numPaths);
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

		    MaxFlowProblemResult edmondsKarpProblemResult = new MaxFlowProblemResult(
			    "EdmondsKarp", graph.getVertices().size(), 0,
			    endTime - startTime, maxFlowProblem.getMaxFlow(),
			    maxFlow);
		    String edmondsKarpString = "Algorithm: "
			    + edmondsKarpProblemResult.getAlgorithmName()
			    + " "
			    + ((edmondsKarpProblemResult.getComputedMaxFlow() == edmondsKarpProblemResult
				    .getCorrectMaxFlow()) ? "CORRECT" : "WRONG")
			    + ", "
			    + "COMPUTED: "
			    + edmondsKarpProblemResult.getComputedMaxFlow()
			    + ", \"CORRECT\": "
			    + edmondsKarpProblemResult.getCorrectMaxFlow()
			    + ", "
			    + String.valueOf(edmondsKarpProblemResult
				    .getNanoTime()) + " nanoseconds";
		    long edmondsKarpResult = edmondsKarpProblemResult
			    .getComputedMaxFlow();
		    maxFlow = EricksonMaxFlow.getMaxFlow(graph, s, t);
		    endTime = System.nanoTime();

		    MaxFlowProblemResult ericksonsProblemResult = new MaxFlowProblemResult(
			    "Erickson", graph.getVertices().size(), 0, endTime
				    - startTime, maxFlowProblem.getMaxFlow(),
			    maxFlow);
		    String ericksonsString = "Algorithm: "
			    + ericksonsProblemResult.getAlgorithmName()
			    + " "
			    + ((ericksonsProblemResult.getComputedMaxFlow() == ericksonsProblemResult
				    .getCorrectMaxFlow()) ? "CORRECT" : "WRONG")
			    + ", "
			    + "COMPUTED: "
			    + ericksonsProblemResult.getComputedMaxFlow()
			    + ", \"CORRECT\": "
			    + ericksonsProblemResult.getCorrectMaxFlow()
			    + ", "
			    + String.valueOf(ericksonsProblemResult
				    .getNanoTime()) + " nanoseconds";
		    long ericksonsResult = ericksonsProblemResult
			    .getComputedMaxFlow();
		    if (ericksonsResult == edmondsKarpResult) {
			System.out.println(graph.getVertices().size() + ","
				+ edmondsKarpProblemResult.getNanoTime() + ","
				+ ericksonsProblemResult.getNanoTime());
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

}
