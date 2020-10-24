package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.logging.Logger;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Planta;

public class DetailFragment extends DialogFragment {

    private DetailViewModel detailViewModel;
    private FirebaseFirestore db;
    private Planta planta;
    public static final String TAG = DetailFragment.class.getSimpleName();


    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        DetailViewModel model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        planta = model.getSelected().getValue();

        final View root = inflater.inflate(R.layout.fragment_detail, container, false);
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
        DocumentReference docRef = db.collection("plantas").document(planta.getId());

        EditText altura = root.findViewById(R.id.editTextAltura);
        planta.setAltura(altura.getText().toString());

        EditText contenedor = (EditText)root.findViewById(R.id.editTextContenedor);
        planta.setContenedor(contenedor.getText().toString());

        EditText origen = (EditText)root.findViewById(R.id.editTextOrigen);
        planta.setOrigen(origen.getText().toString());

        EditText edad = (EditText)root.findViewById(R.id.editTextEdad);
        planta.setEdad(edad.getText().toString());

        EditText fechaRegistro = (EditText)root.findViewById(R.id.editTextFechaRegistro);
        planta.setFechaRegistro(new Date());

        Switch aptoBonsai = (Switch) root.findViewById(R.id.switchAptoBonsai);
        planta.setAptoBonzai(aptoBonsai.isChecked());

        Switch aptoVenta = (Switch) root.findViewById(R.id.switchAptoVenta);
        planta.setAptoVenta(aptoVenta.isChecked());


        docRef.set(planta)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Planta Modificada");
                        Toast.makeText(getContext(), R.string.msj_modificacion_ok, 3500).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al modificar Planta" + " " + e.getCause());
                        Toast.makeText(getContext(), R.string.msj_modificacion_error, 3500);

                    }
                });
        Navigation.findNavController(root).navigate(R.id.nav_home);
    }

    private void eliminarPlanta(final View root, Planta planta) {
        DocumentReference docRef = db.collection("plantas").document(planta.getId());
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Documento eliminado");
                        Toast.makeText(getContext(), R.string.msj_eliminar_ok, 3500).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al intentar eliminar");
                        Toast.makeText(getContext(), R.string.msj_eliminar_error, 3500).show();
                    }
                });
        Navigation.findNavController(root).navigate(R.id.nav_home);



    }

    private void habilitarEdicion(View root) {
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
         EditText altura = (EditText)root.findViewById(R.id.editTextAltura);
         altura.setText(planta.getAltura());

        EditText contenedor = (EditText)root.findViewById(R.id.editTextContenedor);
        contenedor.setText(planta.getContenedor());

        EditText origen = (EditText)root.findViewById(R.id.editTextOrigen);
        origen.setText(planta.getOrigen());

        EditText edad = (EditText)root.findViewById(R.id.editTextEdad);
        edad.setText(planta.getEdad().toString());

        EditText fechaRegistro = (EditText)root.findViewById(R.id.editTextFechaRegistro);
        fechaRegistro.setText(planta.getFechaRegistro().toString());

        Switch aptoBonsai = (Switch) root.findViewById(R.id.switchAptoBonsai);
        aptoBonsai.setChecked(planta.isAptoBonzai());

        Switch aptoVenta = (Switch) root.findViewById(R.id.switchAptoVenta);
        aptoVenta.setChecked(planta.isAptoVenta());
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