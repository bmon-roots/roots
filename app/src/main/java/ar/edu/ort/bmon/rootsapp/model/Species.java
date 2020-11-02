package ar.edu.ort.bmon.rootsapp.model;

public class Species {

    private String name;

    public Species() {
    }

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
        return getName();
    }
}

