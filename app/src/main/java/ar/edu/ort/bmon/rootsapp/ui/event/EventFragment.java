package ar.edu.ort.bmon.rootsapp.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;

public class EventFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerViewGermination;
    private RecyclerView recyclerViewCutting;
    private EventAdapter eventAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.event_fragment, container, false);

        recyclerViewGermination = root.findViewById(R.id.recyclerGerminacion);
        recyclerViewCutting = root.findViewById(R.id.recyclerCutting);
        recyclerViewGermination.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCutting.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection(Constants.EVENTS_COLLECTION);

        FirestoreRecyclerOptions<Event> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Event>()
                        .setQuery(query, Event.class)
                        .build();

        eventAdapter = new EventAdapter(firestoreRecyclerOptions);

        recyclerViewGermination.setAdapter(eventAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}