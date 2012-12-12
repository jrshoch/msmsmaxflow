package graph;

public class BasicFace implements Face {

    private final static String NAME_BASE_STRING = "f";
    
    private final long id;
    private final String name;

    private BasicFace(String name) {
        this.id = IdFactory.getId();
        this.name = name;
    }
    
    public static BasicFace create(String name) {
        return new BasicFace(name);
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
        BasicFace other = (BasicFace) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
