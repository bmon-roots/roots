package ar.edu.ort.bmon.rootsapp.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class Plant {
    private DocumentSnapshot species;
    private String name;
    private String age;
    private Date registrationDate;
    private boolean isBonsaiAble;
    private String origin;
    private String height;
    private String container;
    private boolean isSaleable;
    private String ph;
    private String imageUri;

    /*
    Constructors
     */
    public Plant() {
    }

    public Plant(DocumentSnapshot species, String name, String age, Date registrationDate, boolean isBonsaiAble, String origin, String height, String container, boolean isSaleable, String ph) {
        setSpecies(species);
        setName(name);
        setAge(age);
        setRegistrationDate(registrationDate);
        setBonsaiAble(isBonsaiAble);
        setOrigin(origin);
        setHeight(height);
        setContainer(container);
        setSaleable(isSaleable);
        setPh(ph);
    }
    public Plant(DocumentSnapshot species, String name, String age, Date registrationDate, boolean isBonsaiAble, String origin, String height, String container, boolean isSaleable, String ph, String imageUri) {
        setSpecies(species);
        setName(name);
        setAge(age);
        setRegistrationDate(registrationDate);
        setBonsaiAble(isBonsaiAble);
        setOrigin(origin);
        setHeight(height);
        setContainer(container);
        setSaleable(isSaleable);
        setPh(ph);
        setImageUri(imageUri);
    }

    /*
    Getters
    */
    public DocumentSnapshot getSpecies() {
        return species;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
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

    public String getHeight() {
        return height;
    }

    public String getContainer() {
        return container;
    }

    public boolean isSaleable() {
        return isSaleable;
    }

    public String getPh() {
        return ph;
    }

    public String getImageUri() { return imageUri; }

    /*
    Setters
     */

    public void setSpecies(DocumentSnapshot species) {
        this.species = species;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
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

    public void setHeight(String height) {
        this.height = height;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public void setSaleable(boolean saleable) {
        isSaleable = saleable;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

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
