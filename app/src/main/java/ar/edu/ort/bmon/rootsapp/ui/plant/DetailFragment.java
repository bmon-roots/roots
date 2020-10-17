package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Plant;
import ar.edu.ort.bmon.rootsapp.model.Planta;
import ar.edu.ort.bmon.rootsapp.service.FirebaseService;

public class DetailFragment extends DialogFragment {

    private DetailViewModel mViewModel;
    private FirebaseFirestore db;
    private Planta planta;
    public static final String TAG = DetailFragment.class.getSimpleName();

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        db = FirebaseService.getFirebaseInstance().get
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("plantas").document("OaYKoR62sHciFQxxjUmY");
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            System.out.println("Document data: " + documentSnapshot.getData());
                            planta.setAltura(documentSnapshot.get("altura").toString());
                            planta.setContenedor(documentSnapshot.get("contenedor").toString());
                        } else {
                            System.out.println("No such document!");
                        }
                    }
                });


        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel

    }

}