package slotin;

public interface GraphDistribution {

    public int getWidth();
    public int getHeight();
    public long getNumGraphsWanted();
    public void signalForNextGraph();
    
}
