package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    public static final String TAG = DetailFragment.class.getSimpleName();


    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_detail, container, false);
        db = FirebaseFirestore.getInstance();
        DetailViewModel model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        planta = model.getSelected().getValue();
        //se utiliza por error en el listar que no envía el objeto seleccionado
//        DocumentReference docRef = db.collection(Constants.PLANT_COLLECTION).document("4bLhrNsHB40dLNSxWpV5");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                planta = documentSnapshot.toObject(Plant.class);
//                loadDetailValue(root);
//            }
//        });

        Button editarBtn = root.findViewById(R.id.buttonEditarPlanta);
        Button eliminarBtn = root.findViewById(R.id.buttonEliminarPlanta);
        Button modificarBtn = root.findViewById(R.id.buttonSavePlanta);;

        editarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habilitarEdicion(root);
            }
        });
        eliminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.dialog_delete)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                eliminarPlanta(root, planta);
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
        });
        modificarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.dialog_edit)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                savePlanta(root);
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
        });
        loadDetailValue(root);
        return root;
    }

    private void savePlanta(View root) {
        DocumentReference docRef = db.collection(Constants.PLANT_COLLECTION).document(planta.getId());

        EditText especie = root.findViewById(R.id.editTextEspecie);
        planta.setSpecies(especie.getText().toString());

        EditText nombre = (EditText) root.findViewById(R.id.editTextNombre);
        nombre.setText(planta.getName());

        EditText altura = root.findViewById(R.id.editTextAltura);
        planta.setHeight(altura.getText().toString());

        EditText contenedor = (EditText)root.findViewById(R.id.editTextContenedor);
        planta.setContainer(contenedor.getText().toString());

        EditText origen = (EditText)root.findViewById(R.id.editTextOrigen);
        planta.setOrigin(origen.getText().toString());

        EditText edad = (EditText)root.findViewById(R.id.editTextEdad);
        planta.setAge(edad.getText().toString());

        EditText fechaRegistro = (EditText)root.findViewById(R.id.editTextFechaRegistro);
        planta.setRegistrationDate(new Date());

        Switch aptoBonsai = (Switch) root.findViewById(R.id.switchAptoBonsai);
        planta.setBonsaiAble(aptoBonsai.isChecked());

        Switch aptoVenta = (Switch) root.findViewById(R.id.switchAptoVenta);
        planta.setSaleable(aptoVenta.isChecked());


        docRef.set(planta)
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
        root.findViewById(R.id.editTextEspecie).setEnabled(true);
        root.findViewById(R.id.editTextNombre).setEnabled(true);
        root.findViewById(R.id.editTextAltura).setEnabled(true);
        root.findViewById(R.id.editTextContenedor).setEnabled(true);
        root.findViewById(R.id.editTextOrigen).setEnabled(true);
        root.findViewById(R.id.editTextEdad).setEnabled(true);
        root.findViewById(R.id.editTextFechaRegistro).setEnabled(true);
        root.findViewById(R.id.switchAptoBonsai).setEnabled(true);
        root.findViewById(R.id.switchAptoVenta).setEnabled(true);
        root.findViewById(R.id.buttonSavePlanta).setVisibility(View.VISIBLE);
        root.findViewById(R.id.buttonEliminarPlanta).setVisibility(View.VISIBLE);
        root.findViewById(R.id.buttonEditarPlanta).setVisibility(View.INVISIBLE);
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

        Switch aptoBonsai = (Switch) root.findViewById(R.id.switchAptoBonsai);
        aptoBonsai.setChecked(planta.isBonsaiAble());

        Switch aptoVenta = (Switch) root.findViewById(R.id.switchAptoVenta);
        aptoVenta.setChecked(planta.isSaleable());

        ImageView imageViewPlant = root.findViewById(R.id.imageViewPlant);
        Picasso.get().load(planta.getImageUri()).into(imageViewPlant);

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
}