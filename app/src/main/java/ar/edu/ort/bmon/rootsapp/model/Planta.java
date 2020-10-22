package ar.edu.ort.bmon.rootsapp.model;

import java.util.Date;

public class Planta {

    private String id;
    private String edad;
    private String altura;
    private String origen;
    private String contenedor;
    private Date fechaRegistro;
    private boolean aptoBonzai;
    private boolean aptoVenta;

    public Planta() {
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getContenedor() {
        return contenedor;
    }

    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
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

    public boolean isAptoVenta() {
        return aptoVenta;
    }

    public void setAptoVenta(boolean aptoVenta) {
        this.aptoVenta = aptoVenta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Planta{" +
                "edad='" + edad + '\'' +
                ", altura='" + altura + '\'' +
                ", origen='" + origen + '\'' +
                ", contenedor='" + contenedor + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", aptoBonzai=" + aptoBonzai +
                ", aptoVenta=" + aptoVenta +
                '}';
    }
}
