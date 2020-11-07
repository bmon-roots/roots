package ar.edu.ort.bmon.rootsapp.ui.material;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ar.edu.ort.bmon.rootsapp.R;

public class ListMaterialFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton btnAddAction;

    public static ListMaterialFragment newInstance() {
        return new ListMaterialFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_material, container, false);

        recyclerView = root.findViewById(R.id.recyclerMateriales);
        btnAddAction = root.findViewById(R.id.addMaterialBtn);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                builder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg, null));
                builder.setTitle("Ingrese un nuevo Material");
//                builder.setMessage("HOLI");
                View material = getLayoutInflater().inflate(R.layout.material_item, null);
                builder.setView(material);
                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
    }
}