package ar.edu.ort.bmon.rootsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.edu.ort.bmon.rootsapp.exception.CreateFamilyValidationException;
import ar.edu.ort.bmon.rootsapp.exception.CreateMaterialValidationException;
import ar.edu.ort.bmon.rootsapp.util.Utils;

public class Familia {

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("estratificable")
    @Expose
    private boolean estratificable;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    public Familia() {
    }

    public Familia(String nombre, boolean estratificable, String descripcion) throws CreateFamilyValidationException {
        areFieldsValid(nombre,estratificable,descripcion);
        this.nombre = nombre;
        this.estratificable = estratificable;
        this.descripcion = descripcion;
    }
    private void areFieldsValid(String nombre, boolean estratificable, String descripcion) throws CreateFamilyValidationException {
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(nombre)) {
            message.concat("Nombre");
            throw new CreateFamilyValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(estratificable)) {
            message.concat("Estratificable");
            throw new CreateFamilyValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(descripcion)) {
            message.concat("Descripcion");
            throw new CreateFamilyValidationException(message);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstratificable() {
        return estratificable;
    }

    public void setEstratificable(boolean estratificable) {
        this.estratificable = estratificable;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Familia{" +
                "nombre='" + nombre + '\'' +
                ", estratificable=" + estratificable +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
