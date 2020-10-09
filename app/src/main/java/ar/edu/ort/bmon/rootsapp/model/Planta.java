package ar.edu.ort.bmon.rootsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Planta {

    @SerializedName("edad")
    @Expose
    private int edad;

    @SerializedName("fechaRegistro")
    @Expose
    private Date fechaRegistro;

    @SerializedName("aptoBonzai")
    @Expose
    private boolean aptoBonzai;

    @SerializedName("origen")
    @Expose
    private String origen;

    @SerializedName("altura")
    @Expose
    private int altura;

    @SerializedName("contenedor")
    @Expose
    private int contenedor;

    @SerializedName("aptoVenta")
    @Expose
    private boolean aptoVenta;

    @SerializedName("especie")
    @Expose
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

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getContenedor() {
        return contenedor;
    }

    public void setContenedor(int contenedor) {
        this.contenedor = contenedor;
    }

    public boolean isAptoVenta() {
        return aptoVenta;
    }

    public void setAptoVenta(boolean aptoVenta) {
        this.aptoVenta = aptoVenta;
    }
}
