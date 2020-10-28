package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ar.edu.ort.bmon.rootsapp.model.Plant;
import ar.edu.ort.bmon.rootsapp.model.Planta;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<Plant> selected;

    public DetailViewModel(){
        selected = new MutableLiveData<Plant>();
    }

    public void select(Plant item) {
        selected.setValue(item);
    }

    public LiveData<Plant> getSelected() {
        return selected;
    }

}