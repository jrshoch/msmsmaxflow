package graphgeneration;

public class NameFactory {
    
    private static long nameCounter = 0;

    public synchronized static String getName() {
	nameCounter += 1;
	return String.valueOf(nameCounter);
    }
}
