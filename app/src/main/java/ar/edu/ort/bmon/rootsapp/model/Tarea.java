package ar.edu.ort.bmon.rootsapp.model;

import androidx.annotation.Nullable;

import java.util.Date;

public class Tarea {
    private String tipo;
    private Date fechaRealizada;

    public Tarea() {
    }

    public Tarea(String tipo) {
        this.tipo = tipo;
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
