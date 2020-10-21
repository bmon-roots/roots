package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Planta;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<Planta> selected;
    private EditText altura;

    public DetailViewModel(){
        selected = new MutableLiveData<Planta>();
    }

    public void select(Planta item) {
        selected.setValue(item);
    }

    public LiveData<Planta> getSelected() {
        return selected;
    }

    public EditText getAltura() {
        return altura;
    }

}