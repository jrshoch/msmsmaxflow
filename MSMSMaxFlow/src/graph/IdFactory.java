package graph;

public class IdFactory {

    private static long idCounter = 0;
    
    public static long getId() {
	return idCounter++;
    }
}
