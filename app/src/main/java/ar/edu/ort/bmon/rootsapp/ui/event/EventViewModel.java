package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ar.edu.ort.bmon.rootsapp.model.Event;

public class EventViewModel extends ViewModel {


    private MutableLiveData<Event> selected;

    public EventViewModel() {
        selected = new MutableLiveData<Event>();
    }

    public void select(Event item) {
        selected.setValue(item);
    }

    public LiveData<Event> getSelected() {
        return selected;
    }
}