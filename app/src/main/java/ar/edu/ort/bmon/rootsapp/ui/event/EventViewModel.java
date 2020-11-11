package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public EventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragment de Eventos");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}