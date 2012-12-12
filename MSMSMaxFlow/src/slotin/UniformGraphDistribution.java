package slotin;

public class UniformGraphDistribution implements GraphDistribution {

    private long graphsProduced = 0;
    private final long numGraphsWanted;
    
    public UniformGraphDistribution(long numGraphsWanted){
	this.numGraphsWanted = numGraphsWanted;
    }

    @Override
    public int getWidth(){
	return (int) (1000*((graphsProduced+1)/numGraphsWanted));
    }
    
    @Override
    public int getHeight(){
	return (int) (1000*((graphsProduced+1)/numGraphsWanted));
    }
    
    @Override
    public long getNumGraphsWanted() {
	return numGraphsWanted;
    }
    
    @Override
    public void signalForNextGraph(){
	graphsProduced += 1;
    }
}
