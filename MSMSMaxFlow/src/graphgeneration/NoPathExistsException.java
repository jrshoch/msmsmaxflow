package graphgeneration;

public class NoPathExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoPathExistsException(String s){
	super(s);
    }
}
