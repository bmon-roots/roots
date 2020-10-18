package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Plant;
import ar.edu.ort.bmon.rootsapp.model.Planta;
import ar.edu.ort.bmon.rootsapp.service.FirebaseService;

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
        DocumentReference docRef = db.collection("plantas").document("OaYKoR62sHciFQxxjUmY");
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            System.out.println("Document data: " + documentSnapshot.getData());
//                            planta.setAltura(documentSnapshot.get("altura").toString());
//                            planta.setContenedor(documentSnapshot.get("contenedor").toString());
                        } else {
                            System.out.println("No such document!");
                        }
                    }
                });


        final View root = inflater.inflate(R.layout.detail_fragment, container, false);
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
                eliminarPlanta();
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
                        System.out.println("Planta Modificada");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al modificar Planta");
                    }
                });
    }

    private void eliminarPlanta() {
        DocumentReference docRef = db.collection("plantas").document("OaYKoR62sHciFQxxjUmY");
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Documento eliminado");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
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
        root.findViewById(R.id.editTextAptoBonsai).setEnabled(true);
        root.findViewById(R.id.editTextAptoVenta).setEnabled(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel

    }

}