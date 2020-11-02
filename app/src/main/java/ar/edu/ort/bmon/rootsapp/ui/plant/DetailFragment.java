package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Plant;

public class DetailFragment extends DialogFragment {

    private DetailViewModel detailViewModel;
    private FirebaseFirestore db;
    private Plant planta;
    private View viewReference;
    private MenuItem editMenuItem;
    private MenuItem saveChangesMenuItem;
    private MenuItem deleteMenuItem;
    public static final String TAG = DetailFragment.class.getSimpleName();


    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewReference = inflater.inflate(R.layout.fragment_detail, container, false);
        db = FirebaseFirestore.getInstance();
        DetailViewModel model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        planta = model.getSelected().getValue();
        loadDetailValue(viewReference);
        return viewReference;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_plant, menu);
        editMenuItem = menu.findItem(R.id.menu_edit_plant_button);
        saveChangesMenuItem = menu.findItem(R.id.menu_save_changes_button);
        deleteMenuItem = menu.findItem(R.id.menu_delete_plant_button);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectionId = item.getItemId();

        switch (selectionId) {
            case R.id.menu_edit_plant_button:
                habilitarEdicion(viewReference);
                break;
            case R.id.menu_delete_plant_button:
                crearDialogoEliminar();
                break;
            case R.id.menu_save_changes_button:
                crearDialogoGuardar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void savePlanta(View root) {
        DocumentReference docRef = db.collection(Constants.PLANT_COLLECTION).document(planta.getId());

        //Bind viewElements
        EditText especie = root.findViewById(R.id.editTextEspecie);
        EditText nombre = (EditText) root.findViewById(R.id.editTextNombre);
        EditText altura = root.findViewById(R.id.editTextAltura);
        EditText contenedor = (EditText)root.findViewById(R.id.editTextContenedor);
        EditText origen = (EditText)root.findViewById(R.id.editTextOrigen);
        EditText edad = (EditText)root.findViewById(R.id.editTextEdad);
        EditText fechaRegistro = (EditText)root.findViewById(R.id.editTextFechaRegistro);
        EditText ph = (EditText) root.findViewById(R.id.editTextPH);
        Switch aptoBonsai = (Switch) root.findViewById(R.id.switchAptoBonsai);
        Switch aptoVenta = (Switch) root.findViewById(R.id.switchAptoVenta);

        //Bind new values to store
        String newPlantName = nombre.getText().toString();
        String newPlantHeight = altura.getText().toString();
        String newPlantContainer = contenedor.getText().toString();
        String newPlantOrigin = origen.getText().toString();
        String newPlantAge = edad.getText().toString();
        String newPlantPH = ph.getText().toString();
        Boolean newPlantBonsaiStatus = aptoBonsai.isChecked();
        Boolean newPlantSaleableStatus = aptoVenta.isChecked();


        docRef.update(
                "age", newPlantAge,
                "bonsaiAble", newPlantBonsaiStatus,
                "container", newPlantContainer,
                "height", newPlantHeight,
                "name", newPlantName,
                "origin", newPlantOrigin,
                "ph", newPlantPH,
                "saleable", newPlantSaleableStatus
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Planta Modificada");
                        Toast.makeText(getContext(), R.string.msj_modificacion_ok, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al modificar Planta" + " " + e.getCause());
                        Toast.makeText(getContext(), R.string.msj_modificacion_error, Toast.LENGTH_LONG);

                    }
                });
        Navigation.findNavController(root).navigate(R.id.nav_plant);
    }

    private void eliminarPlanta(final View root, Plant planta) {
        DocumentReference docRef = db.collection(Constants.PLANT_COLLECTION).document(planta.getId());
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Documento eliminado");
                        Toast.makeText(getContext(), R.string.msj_eliminar_ok, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al intentar eliminar");
                        Toast.makeText(getContext(), R.string.msj_eliminar_error, Toast.LENGTH_LONG).show();
                    }
                });
        Navigation.findNavController(root).navigate(R.id.nav_plant);



    }

    private void habilitarEdicion(View root) {
        editMenuItem.setVisible(false);
        saveChangesMenuItem.setVisible(true);
        //root.findViewById(R.id.editTextEspecie).setEnabled(true);
        root.findViewById(R.id.editTextNombre).setEnabled(true);
        root.findViewById(R.id.editTextAltura).setEnabled(true);
        root.findViewById(R.id.editTextContenedor).setEnabled(true);
        root.findViewById(R.id.editTextOrigen).setEnabled(true);
        root.findViewById(R.id.editTextEdad).setEnabled(true);
        //root.findViewById(R.id.editTextFechaRegistro).setEnabled(true);
        root.findViewById(R.id.editTextPH).setEnabled(true);
        root.findViewById(R.id.switchAptoBonsai).setEnabled(true);
        root.findViewById(R.id.switchAptoVenta).setEnabled(true);
    }

    private void crearDialogoEliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eliminarPlanta(viewReference, planta);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                TAG.
                        System.out.println("Canceló eliminar");
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void crearDialogoGuardar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_edit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        savePlanta(viewReference);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                TAG.
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadDetailValue(View root){
        EditText nombre = (EditText) root.findViewById(R.id.editTextNombre);
        nombre.setText(planta.getName());

        EditText especie = (EditText)root.findViewById(R.id.editTextEspecie);
        especie.setText(planta.getSpecies());

        EditText altura = (EditText)root.findViewById(R.id.editTextAltura);
        altura.setText(planta.getHeight());

        EditText contenedor = (EditText)root.findViewById(R.id.editTextContenedor);
        contenedor.setText(planta.getContainer());

        EditText origen = (EditText)root.findViewById(R.id.editTextOrigen);
        origen.setText(planta.getOrigin());

        EditText edad = (EditText)root.findViewById(R.id.editTextEdad);
        edad.setText(planta.getAge());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String registrationDate = dateFormat.format(planta.getRegistrationDate());

        EditText fechaRegistro = (EditText)root.findViewById(R.id.editTextFechaRegistro);
        fechaRegistro.setText(registrationDate);

        EditText ph = (EditText) root.findViewById(R.id.editTextPH);
        ph.setText(planta.getPh());

        Switch aptoBonsai = (Switch) root.findViewById(R.id.switchAptoBonsai);
        aptoBonsai.setChecked(planta.isBonsaiAble());

        Switch aptoVenta = (Switch) root.findViewById(R.id.switchAptoVenta);
        aptoVenta.setChecked(planta.isSaleable());

        ImageView imageViewPlant = root.findViewById(R.id.imageViewPlant);

        if (planta.getImageUri() != null) {
            Picasso.get().load(planta.getImageUri()).into(imageViewPlant);
        }

    }
}