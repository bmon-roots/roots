package ar.edu.ort.bmon.rootsapp.ui.plant;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ar.edu.ort.bmon.rootsapp.model.Plant;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<Plant> selected;

    public DetailViewModel() {
        selected = new MutableLiveData<Plant>();
    }

    public void select(Plant item) {
        selected.setValue(item);
    }

    public LiveData<Plant> getSelected() {
        return selected;
    }

}