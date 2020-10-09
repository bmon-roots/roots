package ar.edu.ort.bmon.rootsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
