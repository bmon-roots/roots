package ar.edu.ort.bmon.rootsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Planta {

    private int edad;

    private Date fechaRegistro;

    private boolean aptoBonzai;

    private String origen;

    private String altura;

    private String contenedor;

    private boolean aptoVenta;

    private Especie especie;


    public Planta() {
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isAptoBonzai() {
        return aptoBonzai;
    }

    public void setAptoBonzai(boolean aptoBonzai) {
        this.aptoBonzai = aptoBonzai;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getContenedor() {
        return contenedor;
    }

    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }

    public boolean isAptoVenta() {
        return aptoVenta;
    }

    public void setAptoVenta(boolean aptoVenta) {
        this.aptoVenta = aptoVenta;
    }
}
