package ar.edu.ort.bmon.rootsapp.model;

import java.util.Date;

public class Event {

    private String tipo;
    private String especie;
    private int cantidadInicial;
    private int cantidadActivas;
    private Date fechaInicio;
    private Date fechaFinalizacion;
    private Date primerosBrotes;
    private Date brotoLaMitad;
    private double temperatura;
    private int humedad;
    private double ph;
    private TipoTarea tarea;
    private Date fechaEstratificacion;
    private boolean usoHormonas;

    public Event() {
    }

    public Event(String tipo, String especie, int cantidadInicial, int cantidadActivas, Date fechaInicio, Date fechaFinalizacion, Date primerosBrotes, Date brotoLaMitad, double temperatura, int humedad, double ph, TipoTarea tarea, Date fechaEstratificacion) {
        setTipo(tipo);
        setEspecie(especie);
        setCantidadInicial(cantidadInicial);
        setCantidadActivas(cantidadActivas);
        setFechaInicio(fechaInicio);
        setFechaFinalizacion(fechaFinalizacion);
        setPrimerosBrotes(primerosBrotes);
        setBrotoLaMitad(brotoLaMitad);
        setTemperatura(temperatura);
        setHumedad(humedad);
        setPh(ph);
        setTarea(tarea);
        setFechaEstratificacion(fechaEstratificacion);
    }

    public Event(String tipo, String especie, int cantidadInicial, int cantidadActivas, Date fechaInicio, Date fechaFinalizacion, Date primerosBrotes, Date brotoLaMitad, double temperatura, int humedad, double ph, TipoTarea tarea, boolean usoHormonas) {
        setTipo(tipo);
        setEspecie(especie);
        setCantidadInicial(cantidadInicial);
        setCantidadActivas(cantidadActivas);
        setFechaInicio(fechaInicio);
        setFechaFinalizacion(fechaFinalizacion);
        setPrimerosBrotes(primerosBrotes);
        setBrotoLaMitad(brotoLaMitad);
        setTemperatura(temperatura);
        setHumedad(humedad);
        setPh(ph);
        setTarea(tarea);
        setUsoHormonas(usoHormonas);
    }

    public String getTipo() {
        return tipo;
    }

    public String getEspecie() {
        return especie;
    }

    public int getCantidadInicial() {
        return cantidadInicial;
    }

    public int getPrivatecantidadActivas() {
        return cantidadActivas;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public Date getPrimerosBrotes() {
        return primerosBrotes;
    }

    public Date getBrotoLaMitad() {
        return brotoLaMitad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public int getHumedad() {
        return humedad;
    }

    public double getPh() {
        return ph;
    }

    public TipoTarea getTarea() {
        return tarea;
    }

    public Date getFechaEstratificacion() {
        return fechaEstratificacion;
    }

    public boolean isUsoHormonas() {
        return usoHormonas;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }

    private void setEspecie(String especie) {
        this.especie = especie;
    }

    private void setCantidadInicial(int cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    private void setCantidadActivas(int cantidadActivas) {
        this.cantidadActivas = cantidadActivas;
    }

    private void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    private void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    private void setPrimerosBrotes(Date primerosBrotes) {
        this.primerosBrotes = primerosBrotes;
    }

    private void setBrotoLaMitad(Date brotoLaMitad) {
        this.brotoLaMitad = brotoLaMitad;
    }

    private void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    private void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    private void setPh(double ph) {
        this.ph = ph;
    }

    private void setTarea(TipoTarea tarea) {
        this.tarea = tarea;
    }

    private void setFechaEstratificacion(Date fechaEstratificacion) {
        this.fechaEstratificacion = fechaEstratificacion;
    }

    private void setUsoHormonas(boolean usoHormonas) {
        this.usoHormonas = usoHormonas;
    }

    @Override
    public String toString() {
        return "Event{" +
                "tipo='" + tipo + '\'' +
                ", especie='" + especie + '\'' +
                ", cantidadInicial=" + cantidadInicial +
                ", cantidadActivas=" + cantidadActivas +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinalizacion=" + fechaFinalizacion +
                ", primerosBrotes=" + primerosBrotes +
                ", brotoLaMitad=" + brotoLaMitad +
                ", temperatura=" + temperatura +
                ", humedad=" + humedad +
                ", ph=" + ph +
                ", tarea=" + tarea +
                ", fechaEstratificacion=" + fechaEstratificacion +
                ", usoHormonas=" + usoHormonas +
                '}';
    }
}
