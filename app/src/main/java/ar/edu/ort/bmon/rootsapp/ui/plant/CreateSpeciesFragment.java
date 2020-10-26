package ar.edu.ort.bmon.rootsapp.ui.plant;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.ort.bmon.rootsapp.R;

public class CreateSpeciesFragment extends Fragment {

    private CreateSpeciesViewModel mViewModel;

    public static CreateSpeciesFragment newInstance() {
        return new CreateSpeciesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_species_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateSpeciesViewModel.class);
        // TODO: Use the ViewModel
    }

}