package shortestpaths;

import java.util.Comparator;

public class DistanceVertexPairComparator implements Comparator<DistanceVertexPair> {

    @Override
    public int compare(DistanceVertexPair arg0, DistanceVertexPair arg1) {
        return (new Long(arg0.getDistance())).compareTo(new Long(arg1.getDistance()));
    }

}
