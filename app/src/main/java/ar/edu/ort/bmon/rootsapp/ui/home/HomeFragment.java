package ar.edu.ort.bmon.rootsapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Planta;
import ar.edu.ort.bmon.rootsapp.service.FirebaseService;

public class HomeFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    View plantsListView;
    RecyclerView recyclerView;
    PlantsAdapter plantsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        plantsListView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = plantsListView.findViewById(R.id.recyclerPlantas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection("plantas");

        ArrayList<String> ids = new ArrayList<String>();

        FirestoreRecyclerOptions<Planta> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Planta>()
                        .setQuery(query, Planta.class)
                        .build();

        plantsAdapter = new PlantsAdapter(firestoreRecyclerOptions, new OnTextClickListener() {
            @Override
            public void onTextClick(String data) {
                Toast.makeText(getActivity(), data , Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(plantsAdapter);

        return plantsListView;
    }



    @Override
    public void onStart() {
        super.onStart();
        plantsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        plantsAdapter.stopListening();
    }

}