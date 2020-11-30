package ar.edu.ort.bmon.rootsapp.model;

import ar.edu.ort.bmon.rootsapp.exception.CreatePlantValidationException;
import ar.edu.ort.bmon.rootsapp.exception.CreateSpeciesValidationException;
import ar.edu.ort.bmon.rootsapp.util.Utils;

public class Species {

    private String name;

    public Species() {
    }

    public Species(String name) throws CreateSpeciesValidationException {
        areFieldsValid(name);
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

    private void areFieldsValid(String name) throws CreateSpeciesValidationException {
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(name)) {
            message=message.concat("Nombre");
            throw new CreateSpeciesValidationException(message);
        }
    }
}

