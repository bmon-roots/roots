package ar.edu.ort.bmon.rootsapp.ui.event;

public class InsertPlantFromEventException extends Exception {
    String mensaje;
    public InsertPlantFromEventException(String s) {
        this.mensaje = s;
    }
}
