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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	BasicVertex other = (BasicVertex) obj;
	if (id != other.id)
	    return false;
	return true;
    }
    
}
