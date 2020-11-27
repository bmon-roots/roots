package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.exception.CreateEventValidationException;
import ar.edu.ort.bmon.rootsapp.exception.CreateSpeciesValidationException;
import ar.edu.ort.bmon.rootsapp.model.Plant;
import ar.edu.ort.bmon.rootsapp.model.Species;

public class ListPlantFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    View plantsListView;
    RecyclerView recyclerView;
    PlantsAdapter plantsAdapter;
    private DetailViewModel model;
    private MaterialAlertDialogBuilder dialog;
    private View newSpeciesCustomDialog;
    private MenuItem btnAddAction;
    private static final String TAG = "ListPlantFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plant_list, menu);
        btnAddAction = menu.findItem(R.id.open_plant_list_dialog);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        plantsListView = inflater.inflate(R.layout.fragment_list_plant, container, false);
        newSpeciesCustomDialog = getLayoutInflater().inflate(R.layout.create_species_fragment, null);
        recyclerView = plantsListView.findViewById(R.id.recyclerPlantas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection(Constants.PLANT_COLLECTION).orderBy("species");

        FirestoreRecyclerOptions<Plant> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Plant>()
                        .setQuery(query, Plant.class)
                        .build();

        model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);

        plantsAdapter = new PlantsAdapter(firestoreRecyclerOptions, new PlantOnTextClickListener() {
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectionId = item.getItemId();
        if (selectionId == R.id.open_plant_list_dialog) {
            createAddNewElementDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createAddNewElementDialog() {
        dialog = new MaterialAlertDialogBuilder(getActivity());
        dialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
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
        dialog.create().show();
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
        MaterialAlertDialogBuilder newSpeciesDialog = new MaterialAlertDialogBuilder(getActivity());
        newSpeciesDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
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

        newSpeciesDialog.create().show();
    }

    private void insertNewSpeciesName(String newSpeciesName) {
        try {
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
        } catch (CreateSpeciesValidationException e) {
            Log.e(TAG, "insertNewSpeciesName - newSpeciesName: " + newSpeciesName);
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}