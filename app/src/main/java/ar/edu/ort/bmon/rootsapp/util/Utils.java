package ar.edu.ort.bmon.rootsapp.util;

public class Utils {


    public static String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

}
