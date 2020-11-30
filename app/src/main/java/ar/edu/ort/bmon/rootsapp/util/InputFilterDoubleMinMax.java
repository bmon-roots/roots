package ar.edu.ort.bmon.rootsapp.util;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterDoubleMinMax implements InputFilter {

    private Double min, max;

    public InputFilterDoubleMinMax(Double min, Double max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterDoubleMinMax(String min, String max) {
        this.min = Double.parseDouble(min);
        this.max = Double.parseDouble(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            Double input = Double.parseDouble(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(Double a, Double b, Double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}