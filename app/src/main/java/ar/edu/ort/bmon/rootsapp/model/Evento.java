package ar.edu.ort.bmon.rootsapp.model;

import java.util.Date;

public class Evento {

    private String tipo;
    private String especie;
    private int cantidadInicial;
    private int privatecantidadActivas;
    private Date fechaInicio;
    private Date fechaFinalizacion;
    private Date primerosBrotes;
    private Date brotoLaMitad;
    private double temperatura;
    private int humedad;
    private double ph;
    private TipoTarea tarea;

    public Evento() {
    }

    public Evento(String tipo, String especie, int cantidadInicial, int privatecantidadActivas, Date fechaInicio, Date fechaFinalizacion, Date primerosBrotes, Date brotoLaMitad, double temperatura, int humedad, double ph, TipoTarea tarea) {
        this.tipo = tipo;
        this.especie = especie;
        this.cantidadInicial = cantidadInicial;
        this.privatecantidadActivas = privatecantidadActivas;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.primerosBrotes = primerosBrotes;
        this.brotoLaMitad = brotoLaMitad;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.ph = ph;
        this.tarea = tarea;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(int cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public int getPrivatecantidadActivas() {
        return privatecantidadActivas;
    }

    public void setPrivatecantidadActivas(int privatecantidadActivas) {
        this.privatecantidadActivas = privatecantidadActivas;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Date getPrimerosBrotes() {
        return primerosBrotes;
    }

    public void setPrimerosBrotes(Date primerosBrotes) {
        this.primerosBrotes = primerosBrotes;
    }

    public Date getBrotoLaMitad() {
        return brotoLaMitad;
    }

    public void setBrotoLaMitad(Date brotoLaMitad) {
        this.brotoLaMitad = brotoLaMitad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public TipoTarea getTarea() {
        return tarea;
    }

    public void setTarea(TipoTarea tarea) {
        this.tarea = tarea;
    }
}
