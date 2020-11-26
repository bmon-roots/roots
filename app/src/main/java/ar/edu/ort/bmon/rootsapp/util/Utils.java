package ar.edu.ort.bmon.rootsapp.util;

import java.util.Date;

import ar.edu.ort.bmon.rootsapp.model.TipoMaterial;

public class Utils {


    public static String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    public static boolean validateIsNullOrEmpty(String value){
        return (null == value || value.isEmpty());
    }
    public static boolean validateIsNullOrEmpty(Date value){
        return (null == value);
    }
    public static boolean validateIsNullOrEmpty(boolean value){
        return (null == Boolean.valueOf(value));
    }
    public static boolean validateIsNullOrEmpty(TipoMaterial value){
        return (null == value);
    }
    public static boolean validateIsNullOrEmpty(int periodicidadDias) {
        return (null == Integer.valueOf(periodicidadDias) || periodicidadDias <= 0);
    }

    public static boolean validateIsNullOrEmpty(double temperatura) {
        return (null == Double.valueOf(temperatura) || temperatura < 0);
    }
}
