package ar.edu.ort.bmon.rootsapp.model;

public class Species {

    private String name;

    public Species(String name) {
        setName(name);
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Species{");
        sb.append("name='").append(getName()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
