package ar.edu.ort.bmon.rootsapp.model;

import java.util.Date;

public class Tarea {
    private String tipo;
    private Date fechaRealizada;

    public Tarea() {
    }

    public Tarea(String tipo, Date fechaRealizada) {
        this.tipo = tipo;
        this.fechaRealizada = fechaRealizada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }


}
