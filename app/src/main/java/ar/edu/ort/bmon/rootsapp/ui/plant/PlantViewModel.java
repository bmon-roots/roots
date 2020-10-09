package ar.edu.ort.bmon.rootsapp.ui.plant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlantViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlantViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("ABM de plantas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}