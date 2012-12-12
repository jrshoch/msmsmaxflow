package slotin;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import maxflow.MaxFlowProblem;

public class RunSlotin {

    public static final long graphsWanted = 500;
    public static final long maxCapacityOnRandomWalk = 30;
    public static final long maxCapacityOffCut = 100;
    public static final long numPaths = 7;
    
    public static void main(String args[]){
    
        EdmondsKarpMaxFlowAlgorithm edmondsKarp = new EdmondsKarpMaxFlowAlgorithm();
        GraphConsumer<EdmondsKarpMaxFlowAlgorithm> edmondsKarpConsumerThread = 
    	    new GraphConsumer<EdmondsKarpMaxFlowAlgorithm> (edmondsKarp);
        
        EricksonMaxFlowAlgorithm erickson = new EricksonMaxFlowAlgorithm();
        GraphConsumer<EricksonMaxFlowAlgorithm> ericksonConsumerThread =
    	    new GraphConsumer<EricksonMaxFlowAlgorithm> (erickson);
        
        Collection<ConcurrentLinkedQueue<MaxFlowProblem>> consumerQueues = 
    	    new LinkedList<ConcurrentLinkedQueue<MaxFlowProblem>> (); 
        consumerQueues.add(edmondsKarpConsumerThread.getConsumerQueue());
        consumerQueues.add(ericksonConsumerThread.getConsumerQueue());
        
        GraphDistribution dist = new UniformGraphDistribution(graphsWanted);
        
        GraphGenerator generator = new GraphGenerator(dist, 
        	maxCapacityOnRandomWalk, maxCapacityOffCut, numPaths);
        new QueueProcessor(generator.getProducerQueue(),
        	consumerQueues);
        
        Collection<List<MaxFlowProblemResult>> problemResults = 
        	new LinkedList<List<MaxFlowProblemResult>> ();
        problemResults.add(edmondsKarpConsumerThread.getResults());
        problemResults.add(ericksonConsumerThread.getResults());
        
        WriteResultsToFile.WriteResultsToFile("maxFlowResults.csv", consumerQueues);
        
    }
}
