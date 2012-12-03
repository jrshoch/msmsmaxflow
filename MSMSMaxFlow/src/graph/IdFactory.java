package graph;

public class IdFactory {

    private static long idCounter = 0;
    
    public synchronized static long getId() {
	return idCounter++;
    }
}
