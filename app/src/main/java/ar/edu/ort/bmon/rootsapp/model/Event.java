package ar.edu.ort.bmon.rootsapp.model;

import java.util.Date;

import ar.edu.ort.bmon.rootsapp.exception.CreateEventValidationException;
import ar.edu.ort.bmon.rootsapp.exception.CreateTaskValidationException;
import ar.edu.ort.bmon.rootsapp.util.Utils;

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

    public Event(String tipo, String especie, int cantidadInicial, Date fechaInicio, Date primerosBrotes, double temperatura, int humedad, double ph, Date fechaEstratificacion) throws CreateEventValidationException {
        areFieldsValid(tipo,especie,cantidadInicial,fechaInicio,temperatura,humedad,ph,fechaEstratificacion);
        this.tipo = tipo;
        this.especie = especie;
        this.cantidadInicial = cantidadInicial;
        this.fechaInicio = fechaInicio;
        this.primerosBrotes = primerosBrotes;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.ph = ph;
        this.fechaEstratificacion = fechaEstratificacion;
    }

    public Event(String tipo, String especie, int cantidadInicial, Date fechaInicio, Date primerosBrotes, double temperatura, int humedad, double ph, boolean usoHormonas) throws CreateEventValidationException {
        areFieldsValid(tipo,especie,cantidadInicial,fechaInicio,temperatura,humedad,ph,usoHormonas);
        this.tipo = tipo;
        this.especie = especie;
        this.cantidadInicial = cantidadInicial;
        this.fechaInicio = fechaInicio;
        this.primerosBrotes = primerosBrotes;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.ph = ph;
        this.usoHormonas = usoHormonas;
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
    private void areFieldsValid(String tipo, String especie, int cantidadInicial, Date fechaInicio, double temperatura, int humedad, double ph) throws CreateEventValidationException {
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(tipo)) {
            message.concat("Tipo");
            throw new CreateEventValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(especie)) {
            message.concat("Especie");
            throw new CreateEventValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(cantidadInicial)) {
            message.concat("Cantidad Inicial");
            throw new CreateEventValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(fechaInicio)) {
            message.concat("Fecha Inicio");
            throw new CreateEventValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(temperatura)) {
            message.concat("temperatura");
            throw new CreateEventValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(humedad)) {
            message.concat("Humedad");
            throw new CreateEventValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(ph)) {
            message.concat("PH");
            throw new CreateEventValidationException(message);
        }
    }
    private void areFieldsValid(String tipo, String especie, int cantidadInicial, Date fechaInicio, double temperatura, int humedad, double ph, Date fechaEstratificacion) throws CreateEventValidationException {
        areFieldsValid(tipo,especie,cantidadInicial,fechaInicio,temperatura,humedad,ph);
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(fechaEstratificacion)) {
            message.concat("Fecha estratificación");
            throw new CreateEventValidationException(message);
        }
    }

    private void areFieldsValid(String tipo, String especie, int cantidadInicial, Date fechaInicio, double temperatura, int humedad, double ph, boolean usoHormonas) throws CreateEventValidationException {
        areFieldsValid(tipo,especie,cantidadInicial,fechaInicio,temperatura,humedad,ph);
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(usoHormonas)) {
            message.concat("Uso de Hormonas");
            throw new CreateEventValidationException(message);
        }
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

    public int getCantidadActivas() {
        return cantidadActivas;
    }

    public void setCantidadActivas(int cantidadActivas) {
        this.cantidadActivas = cantidadActivas;
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

    public Date getFechaEstratificacion() {
        return fechaEstratificacion;
    }

    public void setFechaEstratificacion(Date fechaEstratificacion) {
        this.fechaEstratificacion = fechaEstratificacion;
    }

    public boolean isUsoHormonas() {
        return usoHormonas;
    }

    public void setUsoHormonas(boolean usoHormonas) {
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
