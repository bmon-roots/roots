package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
//        DocumentReference docRef = db.collection("plantas").document("OaYKoR62sHciFQxxjUmY");
//        docRef.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            System.out.println("Document data: " + documentSnapshot.getData());
////                            planta.setAltura(documentSnapshot.get("altura").toString());
////                            planta.setContenedor(documentSnapshot.get("contenedor").toString());
//                        } else {
//                            System.out.println("No such document!");
//                        }
//                    }
//                });


        final View root = inflater.inflate(R.layout.fragment_detail, container, false);
        DetailViewModel model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        planta = model.getSelected().getValue();
        loadDetailValue(root);
        Button editarBtn = root.findViewById(R.id.buttonEditarPlanta);
        Button eliminarBtn = root.findViewById(R.id.buttonEliminarPlanta);
        Button modificarBtn = root.findViewById(R.id.buttonModificarPlanta);;

        editarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habilitarEdicion(root);
            }
        });
        eliminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPlanta(planta);
            }
        });
        modificarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarPlanta(root);
            }
        });
        return root;
    }

    private void modificarPlanta(View root) {
        DocumentReference docRef = db.collection("plantas").document("c3TMhIdzjxkg1vJAyzLR");
        Planta planta = new Planta();
        EditText editTextAltura = root.findViewById(R.id.editTextAltura);
        editTextAltura.getText();
        planta.setAltura(editTextAltura.getText().toString());
        docRef.set(planta)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //TODO mostrar msj OK y volver al listado
                        System.out.println("Planta Modificada");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO mostrar msj de error en popup y luego volver al listado de plantas
                        System.out.println("Error al modificar Planta" + " " + e.getCause());
                    }
                });
    }

    private void eliminarPlanta(Planta planta) {
        DocumentReference docRef = db.collection("plantas").document(planta.getId());
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //TODO mostrar msj OK y volver al listado
                        System.out.println("Documento eliminado");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO mostrar msj de error en popup y luego volver al listado de plantas
                        System.out.println("Error al intentar eliminar");
                    }
                });


    }

    private void habilitarEdicion(View root) {
        root.findViewById(R.id.editTextAltura).setEnabled(true);
        root.findViewById(R.id.editTextContenedor).setEnabled(true);
        root.findViewById(R.id.editTextOrigen).setEnabled(true);
        root.findViewById(R.id.editTextEdad).setEnabled(true);
        root.findViewById(R.id.editTextFechaRegistro).setEnabled(true);
        root.findViewById(R.id.switchAptoBonsai).setEnabled(true);
        root.findViewById(R.id.switchAptoVenta).setEnabled(true);
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
//        DetailViewModel model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
//        planta = model.getSelected().getValue();

    }
}