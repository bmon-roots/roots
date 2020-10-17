package ar.edu.ort.bmon.rootsapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Planta;


public class PlantsAdapter extends FirestoreRecyclerAdapter<Planta, PlantsAdapter.PlantHolder> {


    public PlantsAdapter(@NonNull FirestoreRecyclerOptions<Planta> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PlantHolder holder, int position, @NonNull Planta model) {
            holder.textViewNombre.setText(model.getEspecie().getNombre());
            holder.textViewEdad.setText(String.valueOf(model.getEdad()));
            holder.textViewMaceta.setText(String.valueOf(model.getContenedor()));
    }

    @NonNull
    @Override
    public PlantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        return new PlantHolder(view);
    }

    class PlantHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre;
        TextView textViewEdad;
        TextView textViewMaceta;


        public PlantHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.text_view_nombre);
            textViewEdad = itemView.findViewById(R.id.text_view_edad);
            textViewMaceta = itemView.findViewById(R.id.text_view_maceta);

        }
    }
}
