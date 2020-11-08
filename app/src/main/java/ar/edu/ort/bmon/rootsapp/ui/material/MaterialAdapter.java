package ar.edu.ort.bmon.rootsapp.ui.material;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Material;


public class MaterialAdapter extends FirestoreRecyclerAdapter<Material, MaterialAdapter.MaterialHolder> {

    public DocumentSnapshot document;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MaterialAdapter(@NonNull FirestoreRecyclerOptions<Material> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MaterialHolder holder, int position, @NonNull final Material model) {
        document = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final DocumentReference docRef = db.collection(Constants.MATERIAL_COLLECTION).document(document.getId());

        holder.material.setText(model.getTipoMaterial().name());
        holder.contenido.setText(String.valueOf(model.getContenido()) + "L");
        holder.cantidad.setText(String.valueOf(model.getCantidad()));

        holder.substractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRef.update(
                        "cantidad", model.getCantidad() - 1,
                        "contenido", model.getContenido(),
                        "tipoMaterial", model.getTipoMaterial().name()
                )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("SE RESTO UNO");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Error al restar uno");
                            }
                        });
            }
        });

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRef.update(
                        "cantidad", model.getCantidad() + 1,
                        "contenido", model.getContenido(),
                        "tipoMaterial", model.getTipoMaterial().name()
                )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("SE SUMO UNO");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Error al restar uno");
                            }
                        });
            }
        });

    }

    @NonNull
    @Override
    public MaterialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_item, parent, false);
        final MaterialHolder mHolder = new MaterialHolder(view);

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
