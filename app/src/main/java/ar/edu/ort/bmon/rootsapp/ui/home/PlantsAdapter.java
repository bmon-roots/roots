package ar.edu.ort.bmon.rootsapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Planta;
import ar.edu.ort.bmon.rootsapp.ui.plant.DetailViewModel;


public class PlantsAdapter extends FirestoreRecyclerAdapter<Planta, PlantsAdapter.PlantHolder> {

    public OnTextClickListener onTextClickListener;
    public DocumentSnapshot document;
    public Planta plantaTest;
    public DocumentSnapshot getDocument() {
        return document;
    }

    public PlantsAdapter(@NonNull FirestoreRecyclerOptions<Planta> options, OnTextClickListener onTextClickListener) {
        super(options);
        this.onTextClickListener = onTextClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlantHolder holder, int position, @NonNull Planta model) {

        document = getSnapshots().getSnapshot(holder.getAdapterPosition());

        holder.textViewNombre.setText(document.getId());
        holder.textViewEdad.setText(model.getEdad());
        holder.textViewMaceta.setText(model.getContenedor());
        holder.textViewTareas.setText("Poda, Riego");
        crearPlantaDesdeModel(model);

    }

    private void crearPlantaDesdeModel(@NonNull Planta model) {
        plantaTest = new Planta();
        plantaTest.setAltura(model.getAltura());
        plantaTest.setAptoBonzai(model.isAptoBonzai());
        plantaTest.setAptoVenta(model.isAptoVenta());
        plantaTest.setContenedor(model.getContenedor());
        plantaTest.setEdad(model.getEdad());
        plantaTest.setOrigen(model.getOrigen());
        plantaTest.setFechaRegistro(model.getFechaRegistro());
    }

    @NonNull
    @Override
    public PlantHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final Context pContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        PlantHolder pHolder = new PlantHolder(view);
        pHolder.cardViewPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailViewModel model = onTextClickListener.onTextClick();
                model.select(plantaTest);
                Navigation.findNavController(view).navigate(R.id.nav_plant_detail);
            }
        });
        return pHolder;
    }

    class PlantHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre;
        TextView textViewEdad;
        TextView textViewMaceta;
        TextView textViewTareas;
        CardView cardViewPlanta;


        public PlantHolder(@NonNull View itemView) {
            super(itemView);
            cardViewPlanta = itemView.findViewById(R.id.card_planta);
            textViewNombre = itemView.findViewById(R.id.text_view_nombre);
            textViewEdad = itemView.findViewById(R.id.text_view_edad);
            textViewMaceta = itemView.findViewById(R.id.text_view_maceta);
            textViewTareas = itemView.findViewById(R.id.text_view_tareas);

        }
    }
}

