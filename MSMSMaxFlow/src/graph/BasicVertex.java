package graph;


public class BasicVertex implements Vertex {

    private final long id;
    private final String name;

    private BasicVertex(String name){
	this.name = name;
	this.id = IdFactory.getId();
    }

    public static BasicVertex create(String name) {
	return new BasicVertex(name);
    }

    @Override
    public long getId() {
	return id;
    }

    @Override
    public String getName() {
	return name;
    }
}
