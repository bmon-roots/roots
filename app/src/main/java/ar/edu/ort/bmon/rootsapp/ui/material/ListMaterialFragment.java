package ar.edu.ort.bmon.rootsapp.ui.material;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.ui.report.ReportViewModel;

public class ListMaterialFragment extends Fragment {

    private ListMaterialViewModel listMaterialViewModel;

    public static ListMaterialFragment newInstance() {
        return new ListMaterialFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        listMaterialViewModel =
                ViewModelProviders.of(this).get(ListMaterialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list_material, container, false);
        final TextView textView = root.findViewById(R.id.textView_material);
        listMaterialViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listMaterialViewModel = ViewModelProviders.of(this).get(ListMaterialViewModel.class);
        // TODO: Use the ViewModel
    }

}