package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Plant;
import ar.edu.ort.bmon.rootsapp.model.Species;

public class ListPlantFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    View plantsListView;
    RecyclerView recyclerView;
    PlantsAdapter plantsAdapter;
    private DetailViewModel model;
    private FloatingActionButton btnAddAction;
    private AlertDialog.Builder dialog;
    private View newSpeciesCustomDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        plantsListView = inflater.inflate(R.layout.fragment_list_plant, container, false);
        newSpeciesCustomDialog = getLayoutInflater().inflate(R.layout.create_species_fragment, null);
        btnAddAction = plantsListView.findViewById(R.id.fab_AddActions);
        recyclerView = plantsListView.findViewById(R.id.recyclerPlantas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection(Constants.PLANT_COLLECTION);

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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new AlertDialog.Builder(getActivity());
        String[] alertDialogOptions = new String[] {Constants.ADD_NEW_PLANT, Constants.ADD_NEW_SPECIES};
        dialog.setTitle(Constants.ADD_NEW_ENTRY_TITLE);
        dialog.setSingleChoiceItems(alertDialogOptions, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                selectionList.setTag(Integer.valueOf(which));
            }
        });
        dialog.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                Integer selectedItemId = (Integer)selectionList.getTag();
                if (selectedItemId == 0) {
                    Navigation.findNavController(plantsListView).navigate(R.id.createPlantFragment);
                } else {
                    showCreateNewSpeciesDialog(newSpeciesCustomDialog);
                }
            }
        });
        btnAddAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.create().show();
            }
        });
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

    private void showCreateNewSpeciesDialog(final View speciesName) {
        AlertDialog.Builder newSpeciesDialog = new AlertDialog.Builder(getActivity());
        newSpeciesDialog.setTitle(R.string.createSpeciesFragmentTitle);

        //Configuracion del newSpeciesDialog

        newSpeciesDialog.setIcon(R.drawable.ic_baseline_edit_24);
        newSpeciesDialog.setView(speciesName);

        newSpeciesDialog.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editTextSpeciesName = speciesName.findViewById(R.id.editTextSpeciesName);
                String newSpeciesName = editTextSpeciesName.getText().toString();
                insertNewSpeciesName(newSpeciesName);
            }
        });

        newSpeciesDialog.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //newSpeciesDialog.setNegativeButtonIcon(getActivity().getDrawable(R.drawable.ic_baseline_close_24));
        //newSpeciesDialog.setPositiveButtonIcon(getActivity().getDrawable(R.drawable.ic_baseline_check_24));

        newSpeciesDialog.create().show();
    }

    private void insertNewSpeciesName(String newSpeciesName) {
        db.collection(Constants.SPECIES_COLLECTION).add(new Species(newSpeciesName))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), R.string.createSpeciesSuccessful, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Constants.FIREBASE_ERROR, e.toString());
                        Toast.makeText(getContext(), R.string.createSpeciesError, Toast.LENGTH_LONG).show();
                    }
                });
    }

}