package ar.edu.ort.bmon.rootsapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.ui.plant.DetailFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    DetailFragment detailFragment;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference plantas = db.collection("Plants");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton detailsButton = root.findViewById(R.id.detailButton);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //llamar a detail fragment
                showDetailFragment();
            }
        });
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void showDetailFragment() {


        detailFragment = (DetailFragment) getFragmentManager().findFragmentByTag(DetailFragment.TAG);

        if (detailFragment == null) {

            detailFragment = DetailFragment.newInstance();
        }
        detailFragment.show(getFragmentManager(),DetailFragment.TAG);

    }
}