package ar.edu.ort.bmon.rootsapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Plant;
import ar.edu.ort.bmon.rootsapp.ui.plant.DetailFragment;
import ar.edu.ort.bmon.rootsapp.model.Planta;
import ar.edu.ort.bmon.rootsapp.ui.plant.DetailViewModel;

public class HomeFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    View plantsListView;
    RecyclerView recyclerView;
    PlantsAdapter plantsAdapter;
    private DetailViewModel model;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        plantsListView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = plantsListView.findViewById(R.id.recyclerPlantas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection(Constants.PLANT_COLLECTION);

        ArrayList<String> ids = new ArrayList<String>();

        FirestoreRecyclerOptions<Plant> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Plant>()
                        .setQuery(query, Plant.class)
                        .build();

        model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);

        plantsAdapter = new PlantsAdapter(firestoreRecyclerOptions, new OnTextClickListener() {
            @Override
            public DetailViewModel onTextClick() {
                return model;
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