package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ar.edu.ort.bmon.rootsapp.model.Event;

public class EventDetailViewModel extends ViewModel {
    private MutableLiveData<Event> selected;
    private MutableLiveData<String> idSelected;

    public EventDetailViewModel() {
        selected = new MutableLiveData<Event>();
        idSelected = new MutableLiveData<String>();
    }

    public void select(Event item) {
        selected.setValue(item);
    }

    public LiveData<Event> getSelected() {
        return selected;
    }

    public void selectId(String id) {
        idSelected.setValue(id);
    }

    public LiveData<String> getIdSelected() {
        return idSelected;
    }
}