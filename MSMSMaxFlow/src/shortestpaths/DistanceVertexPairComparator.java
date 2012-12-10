package shortestpaths;

import java.util.Comparator;

public class DistanceVertexPairComparator implements Comparator<DistanceVertexPair> {

    @Override
    public int compare(DistanceVertexPair arg0, DistanceVertexPair arg1) {
	return Long.compare(arg0.getDistance(), arg1.getDistance());
    }
    
}
