package ar.edu.ort.bmon.rootsapp.model;

import java.util.Date;

public class Plant {
    private Species species;
    private int age;
    private Date registrationDate;
    private boolean isBonsaiAble;
    private String origin;
    private int height;
    private int container;
    private boolean isSaleable;
    private String ph;

    /*
    Constructors
     */
    public Plant() {
    }

    public Plant(Species species, int age, Date registrationDate, boolean isBonsaiAble, String origin, int height, int container, boolean isSaleable, String ph) {
        setSpecies(species);
        setAge(age);
        setRegistrationDate(registrationDate);
        setBonsaiAble(isBonsaiAble);
        setOrigin(origin);
        setHeight(height);
        setContainer(container);
        setSaleable(isSaleable);
        setPh(ph);
    }

    /*
    Getters
    */
    public Species getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public boolean isBonsaiAble() {
        return isBonsaiAble;
    }

    public String getOrigin() {
        return origin;
    }

    public int getHeight() {
        return height;
    }

    public int getContainer() {
        return container;
    }

    public boolean isSaleable() {
        return isSaleable;
    }

    public String getPh() {
        return ph;
    }

    /*
    Setters
     */

    public void setSpecies(Species species) {
        this.species = species;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setBonsaiAble(boolean bonsaiAble) {
        isBonsaiAble = bonsaiAble;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setContainer(int container) {
        this.container = container;
    }

    public void setSaleable(boolean saleable) {
        isSaleable = saleable;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Plant{");
        sb.append("species=").append(species);
        sb.append(", age=").append(age);
        sb.append(", registrationDate=").append(registrationDate);
        sb.append(", isBonsaiAble=").append(isBonsaiAble);
        sb.append(", origin='").append(origin).append('\'');
        sb.append(", height=").append(height);
        sb.append(", container=").append(container);
        sb.append(", isSaleable=").append(isSaleable);
        sb.append(", ph='").append(ph).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
