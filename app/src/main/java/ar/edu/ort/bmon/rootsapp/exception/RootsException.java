package ar.edu.ort.bmon.rootsapp.exception;

public class RootsException extends Exception {
    String message;
    public RootsException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
