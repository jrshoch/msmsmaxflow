package graph;

public class BasicVertex implements Vertex {

    private static final String NAME_BASE_STRING = "v";
    
    private final long id;
    private final String name;

    private BasicVertex(String name) {
        this.name = name;
        this.id = IdFactory.getId();
    }

    public static BasicVertex create(String name) {
        return new BasicVertex(name);
    }
    
    public static String createName(int i) {
        return NAME_BASE_STRING + String.valueOf(i);
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
    public String toString() {
        return getName();
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
