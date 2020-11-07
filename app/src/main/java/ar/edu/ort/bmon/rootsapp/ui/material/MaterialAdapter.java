package ar.edu.ort.bmon.rootsapp.ui.material;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Material;


public class MaterialAdapter extends FirestoreRecyclerAdapter<Material, MaterialAdapter.MaterialHolder> {

    public MaterialAdapter(@NonNull FirestoreRecyclerOptions<Material> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MaterialHolder holder, int position, @NonNull Material model) {
        holder.material.setText(model.getTipoMaterial().name());
        holder.contenido.setText(String.valueOf(model.getContenido()) + "L");
        holder.cantidad.setText(String.valueOf(model.getCantidad()) + " unidades");
    }

    @NonNull
    @Override
    public MaterialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_item, parent, false);
        final MaterialHolder mHolder = new MaterialHolder(view);
        mHolder.substractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return mHolder;
    }

    public class MaterialHolder extends RecyclerView.ViewHolder {

        TextView material;
        TextView contenido;
        TextView cantidad;

        FloatingActionButton substractButton;
        FloatingActionButton addButton;

        public MaterialHolder(@NonNull View itemView) {
            super(itemView);
            substractButton = itemView.findViewById(R.id.substract_button);
            addButton = itemView.findViewById(R.id.add_button);
            material = itemView.findViewById(R.id.text_view_material);
            contenido = itemView.findViewById(R.id.text_view_contenido);
            cantidad = itemView.findViewById(R.id.text_view_cantidad);
        }
    }
}
