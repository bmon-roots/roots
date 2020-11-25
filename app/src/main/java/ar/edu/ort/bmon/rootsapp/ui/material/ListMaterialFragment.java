package ar.edu.ort.bmon.rootsapp.ui.material;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
import ar.edu.ort.bmon.rootsapp.model.Material;
import ar.edu.ort.bmon.rootsapp.model.TipoMaterial;

public class ListMaterialFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private View materialNewEntry;
    private EditText quantity;
    private EditText content;
    private CharSequence[] items;
    private MenuItem btnAddAction;

    private MaterialAdapter materialAdapter;

    public static ListMaterialFragment newInstance() {
        return new ListMaterialFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_materials_list, menu);
        btnAddAction = menu.findItem(R.id.open_material_list_dialog);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_material, container, false);
        materialNewEntry = getLayoutInflater().inflate(R.layout.create_material_quantities, container, false);

        items = new String[] {
                TipoMaterial.Fertilizante.toString(),
                TipoMaterial.Fungicida.toString(),
                TipoMaterial.Insecticida.toString(),
                TipoMaterial.Maceta.toString(),
                TipoMaterial.Sustrato.toString()
        };

        recyclerView = root.findViewById(R.id.recyclerMateriales);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection(Constants.MATERIAL_COLLECTION);

        FirestoreRecyclerOptions<Material> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Material>()
                        .setQuery(query, Material.class)
                        .build();

        materialAdapter = new MaterialAdapter(firestoreRecyclerOptions);

        recyclerView.setAdapter(materialAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectionId = item.getItemId();
        if (selectionId == R.id.open_material_list_dialog) {
            openCreateNewMaterialEntryDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCreateNewMaterialEntryDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle(R.string.createNewMaterialEntry);
        builder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));

        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                selectionList.setTag(Integer.valueOf(which));
            }
        });
        builder.setPositiveButton(Constants.NEXT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                Integer selectedItemId = (Integer)selectionList.getTag();
                //Toast.makeText(getContext(), items[selectedItemId], Toast.LENGTH_LONG).show();
                openInsertMaterialQuantities(selectedItemId, materialNewEntry);
            }
        });
        builder.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void openInsertMaterialQuantities(final Integer selectedItemId, final View materialNewEntry) {

        if(materialNewEntry.getParent() != null) {
            ((ViewGroup)materialNewEntry.getParent()).removeView(materialNewEntry);
        }
        quantity = (EditText) materialNewEntry.findViewById(R.id.materialQuantity);
        content = (EditText) materialNewEntry.findViewById(R.id.materialContent);

        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getActivity());
        alertDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
        alertDialog.setTitle(R.string.createNewMaterialEntryQuantity);
        alertDialog.setView(materialNewEntry);
        alertDialog.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer quantityValue = Integer.parseInt(quantity.getText().toString());
                Integer contentValue = Integer.parseInt(content.getText().toString());
                createNewMaterialEntry(selectedItemId, quantityValue, contentValue);
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create().show();
    }

    private void createNewMaterialEntry(Integer selectedItemId, int quantity, int content) {
        TipoMaterial tipoMaterial = TipoMaterial.valueOf(items[selectedItemId].toString());
        Material material = new Material(tipoMaterial, quantity, content);

        db.collection(Constants.MATERIAL_COLLECTION)
                .add(material)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), Constants.MATERIAL_CREATE_SUCCESS, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), Constants.MATERIAL_CREATE_ERROR, Toast.LENGTH_LONG).show();
                    }
                });
        System.out.println(material);
    }

    @Override
    public void onStart() {
        super.onStart();
        materialAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        materialAdapter.stopListening();
    }
}