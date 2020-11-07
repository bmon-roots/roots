package ar.edu.ort.bmon.rootsapp.model;

public class Material {
    private TipoMaterial tipoMaterial;
    private int cantidad;
    private int contenido;

    private Material() {
    }

    public Material(TipoMaterial tipoMaterial, int cantidad, int contenido) {
        setTipoMaterial(tipoMaterial);
        setCantidad(cantidad);
        setContenido(contenido);
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
                "tipoMaterial=" + tipoMaterial +
                ", cantidad=" + cantidad +
                ", contenido=" + contenido +
                '}';
    }
}
