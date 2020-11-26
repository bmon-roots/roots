package ar.edu.ort.bmon.rootsapp.model;

import ar.edu.ort.bmon.rootsapp.exception.CreateMaterialValidationException;
import ar.edu.ort.bmon.rootsapp.util.Utils;

public class Material {
    private TipoMaterial tipoMaterial;
    private int cantidad;
    private int contenido;

    private Material() {
    }

    public Material(TipoMaterial tipoMaterial, int cantidad, int contenido) throws CreateMaterialValidationException {
        areFieldsValid(tipoMaterial, cantidad, contenido);
        setTipoMaterial(tipoMaterial);
        setCantidad(cantidad);
        setContenido(contenido);
    }
    private void areFieldsValid(TipoMaterial tipoMaterial, int cantidad, int contenido) throws CreateMaterialValidationException {
        String message="Falta dato obligatorio: ";
        if (Utils.validateIsNullOrEmpty(tipoMaterial)) {
            message.concat("Tipo material");
            throw new CreateMaterialValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(cantidad)) {
            message.concat("Cantidad");
            throw new CreateMaterialValidationException(message);
        }
        if (Utils.validateIsNullOrEmpty(contenido)) {
            message.concat("Contenido");
            throw new CreateMaterialValidationException(message);
        }
    }

    public TipoMaterial getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(TipoMaterial tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getContenido() {
        return contenido;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Material{" +
                "tipoMaterial=" + tipoMaterial.ordinal() +
                ", cantidad=" + cantidad +
                ", contenido=" + contenido +
                '}';
    }
}
