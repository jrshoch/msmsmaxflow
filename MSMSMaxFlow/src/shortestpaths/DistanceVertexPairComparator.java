package shortestpaths;

import java.util.Comparator;

public class DistanceVertexPairComparator implements Comparator<DistanceVertexPair> {

    @Override
    public int compare(DistanceVertexPair arg0, DistanceVertexPair arg1) {
	return arg0.compareDistance(arg1);
    }
    
}
