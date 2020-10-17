package ar.edu.ort.bmon.rootsapp.ui.plant;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.service.FirebaseService;

public class DetailFragment extends DialogFragment {

    private DetailViewModel mViewModel;
    private FirebaseService service;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbRef;
    public static final String TAG = DetailFragment.class.getSimpleName();

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        service = FirebaseService.getFirebaseInstance();
//        mDatabase = service.get
//        mDbRef = mDatabase.getReference().child("plantas");
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel
    }

}