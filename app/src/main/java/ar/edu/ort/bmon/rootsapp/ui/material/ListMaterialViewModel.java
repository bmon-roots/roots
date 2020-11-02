package ar.edu.ort.bmon.rootsapp.ui.material;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListMaterialViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ListMaterialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Materiales fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}