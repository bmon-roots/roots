package ar.edu.ort.bmon.rootsapp.model;

import java.util.ArrayList;
import java.util.Date;

import ar.edu.ort.bmon.rootsapp.exception.CreatePlantValidationException;
import ar.edu.ort.bmon.rootsapp.exception.CreateTaskValidationException;
import ar.edu.ort.bmon.rootsapp.util.Utils;

public class Plant {
    private String id;
    private String species;
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
    private ArrayList<Tarea> tareas = new ArrayList<Tarea>();;

    /*
    Constructors
     */
    public Plant() {
    }

    public Plant(String species, String name, String age, Date registrationDate, boolean isBonsaiAble, String origin, String height, String container, boolean isSaleable, String ph) throws CreatePlantValidationException {
        areFieldsValid(species,name,age,registrationDate,isBonsaiAble,origin,height,container,isSaleable,ph);
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

    public Plant(String species, String name, String age, Date registrationDate, boolean isBonsaiAble, String origin, String height, String container, boolean isSaleable, String ph, String imageUri) throws CreatePlantValidationException {
        areFieldsValid(species,name,age,registrationDate,isBonsaiAble,origin,height,container,isSaleable,ph);
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

    public Plant(String species, String name, String age, Date registrationDate, boolean isBonsaiAble, String origin, String height, String container, boolean isSaleable, String ph, String imageUri, ArrayList<Tarea> tareas) throws CreatePlantValidationException {
        areFieldsValid(species,name,age,registrationDate,isBonsaiAble,origin,height,container,isSaleable,ph);
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
        setTareas(tareas);
    }

    private void areFieldsValid(String species, String name, String age, Date registrationDate, boolean isBonsaiAble, String origin, String height, String container,
                                   boolean isSaleable, String ph) throws CreatePlantValidationException {
        String menssage="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(species)) {
            menssage=menssage.concat("Especie");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(name)) {
            menssage=menssage.concat("Nombre");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(age)) {
            menssage=menssage.concat("Edad");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(registrationDate)) {
            menssage=menssage.concat("Fecha de registro");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(isBonsaiAble)) {
            menssage=menssage.concat("Apto Bonzai");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(origin)) {
            menssage=menssage.concat("Origen");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(height)) {
            menssage=menssage.concat("Altura");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(container)) {
            menssage=menssage.concat("Contenedor");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(isSaleable)) {
            menssage=menssage.concat("Apto venta");
            throw new CreatePlantValidationException(menssage);
        }
        if (Utils.validateIsNullOrEmpty(ph)) {
            menssage=menssage.concat("PH");
            throw new CreatePlantValidationException(menssage);
        }
    }

    /*
    Getters
    */

    public String getId() {
        return id;
    }

    public String getSpecies() {
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

    public String getImageUri() {
        return imageUri;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    /*
    Setters
     */

    public void setId(String id) {
        this.id = id;
    }

    public void setSpecies(String species) {
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

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void addTask(Date registrationDate, String taskName, int periodicidadDias) throws CreateTaskValidationException {
        Tarea tarea = new Tarea(taskName, registrationDate, periodicidadDias);
        if (!tareas.contains(tarea)){
            tareas.add(tarea);
        }else {
            Tarea tareaExist = getTarea(taskName);
            if (periodicidadDias != tareaExist.getPeriodicidadDias()){
                tareaExist.setPeriodicidadDias(periodicidadDias);
                tareaExist.setFechaRealizada(registrationDate);
            }
        }
    }
    public Tarea getTarea(String taskName) throws CreateTaskValidationException {
        Tarea tareaExist = null;
        Tarea tarea = new Tarea(taskName);
        if (tareas.contains(tarea)){
            int index = tareas.indexOf(tarea);
            tareaExist = tareas.get(index);
        }
        return tareaExist;
    }
    public void removeTask(String taskName) throws CreateTaskValidationException {
        Tarea tarea = new Tarea(taskName);
        tareas.remove(tarea);
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
        sb.append(", tasks=").append(tareas);
        sb.append('}');
        return sb.toString();
    }
}
