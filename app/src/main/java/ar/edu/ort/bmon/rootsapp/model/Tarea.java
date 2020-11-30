package ar.edu.ort.bmon.rootsapp.model;

import androidx.annotation.Nullable;

import java.util.Date;

import ar.edu.ort.bmon.rootsapp.exception.CreatePlantValidationException;
import ar.edu.ort.bmon.rootsapp.exception.CreateTaskValidationException;
import ar.edu.ort.bmon.rootsapp.util.Utils;

public class Tarea {
    private String tipo;
    private Date fechaRealizada;
    private int periodicidadDias;

    public Tarea() {
    }

    public Tarea(String tipo) throws CreateTaskValidationException {
        areFieldsValid(tipo);
        this.tipo = tipo;
    }

    public Tarea(String tipo, Date fechaRealizada) throws CreateTaskValidationException {
        areFieldsValid(tipo, fechaRealizada);
        this.tipo = tipo;
        this.fechaRealizada = fechaRealizada;
    }

    public Tarea(String tipo, Date fechaRealizada, int periodicidadDias) throws CreateTaskValidationException {
        areFieldsValid(tipo, fechaRealizada, periodicidadDias);
        this.tipo = tipo;
        this.fechaRealizada = fechaRealizada;
        this.periodicidadDias = periodicidadDias;
    }
    private void areFieldsValid(String tipo) throws CreateTaskValidationException {
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(tipo)) {
            message.concat("Tipo");
            throw new CreateTaskValidationException(message);
        }
    }
    private void areFieldsValid(String tipo, Date fechaRealizada) throws CreateTaskValidationException {
        areFieldsValid(tipo);
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(fechaRealizada)) {
            message.concat("Fecha de realizacion");
            throw new CreateTaskValidationException(message);
        }
    }

    private void areFieldsValid(String tipo, Date fechaRealizada, int periodicidadDias) throws CreateTaskValidationException {
        areFieldsValid(tipo,fechaRealizada);
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(periodicidadDias)) {
            message.concat("Periodicidad");
            throw new CreateTaskValidationException(message);
        }
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

    public int getPeriodicidadDias() {
        return periodicidadDias;
    }

    public void setPeriodicidadDias(int periodicidadDias) {
        this.periodicidadDias = periodicidadDias;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tarea other = (Tarea) obj;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        return true;
    }
}
