package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.exception.CreatePlantValidationException;
import ar.edu.ort.bmon.rootsapp.model.Plant;
import ar.edu.ort.bmon.rootsapp.model.Tarea;


public class PlantsAdapter extends FirestoreRecyclerAdapter<Plant, PlantsAdapter.PlantHolder> {

    public PlantOnTextClickListener plantOnTextClickListener;
    public DocumentSnapshot document;

    public PlantsAdapter(@NonNull FirestoreRecyclerOptions<Plant> options, PlantOnTextClickListener plantOnTextClickListener) {
        super(options);
        this.plantOnTextClickListener = plantOnTextClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlantHolder holder, int position, @NonNull Plant model) {

        document = getSnapshots().getSnapshot(holder.getAdapterPosition());

        holder.textViewNombre.setText(model.getSpecies());
        holder.textViewMaceta.setText(model.getContainer() + "L");
        try {
            holder.plantita = crearPlantaDesdeModel(model, document.getId());
        }catch (CreatePlantValidationException ex){
            Log.e(this.getClass().getCanonicalName(), "Error al crear planta" + ex.getMessage());
            Toast.makeText(holder.cardViewPlanta.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        holder.imageViewAptoBonzai.setVisibility(View.INVISIBLE);
        holder.imageViewAptoVenta.setVisibility(View.INVISIBLE);
        holder.imageViewPoda.setVisibility(View.INVISIBLE);
        holder.imageViewFumigate.setVisibility(View.INVISIBLE);
        holder.imageViewFertilize.setVisibility(View.INVISIBLE);

        if(model.isBonsaiAble()){
            holder.imageViewAptoBonzai.setVisibility(View.VISIBLE);
        }
        if(model.isSaleable()){
            holder.imageViewAptoVenta.setVisibility(View.VISIBLE);
        }
        for (Tarea tarea : model.getTareas()) {
            if(tarea.getTipo().equals(Constants.ADD_TASK_FERTILIZE)){
                holder.imageViewFertilize.setVisibility(View.VISIBLE);
            } else if(tarea.getTipo().equals(Constants.ADD_TASK_FUMIGATE)){
                holder.imageViewFumigate.setVisibility(View.VISIBLE);
            } else if(tarea.getTipo().equals(Constants.ADD_TASK_PRUNE)){
                holder.imageViewPoda.setVisibility(View.VISIBLE);
            }
        }
    }

    private Plant crearPlantaDesdeModel(@NonNull Plant model, String id) throws CreatePlantValidationException {
        Plant planta = new Plant(model.getSpecies(), model.getName(),model.getAge(),model.getRegistrationDate(),
                model.isBonsaiAble(),model.getOrigin(),model.getHeight(),model.getContainer(),model.isSaleable(),model.getPh(),
                model.getImageUri(),model.getTareas());
        planta.setId(id);
        return planta;
    }

    @NonNull
    @Override
    public PlantHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        final PlantHolder pHolder = new PlantHolder(view);
        pHolder.cardViewPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailViewModel model = plantOnTextClickListener.onTextClick();
                model.select(pHolder.plantita);
                Navigation.findNavController(view).navigate(R.id.nav_plant_detail);
            }
        });
        return pHolder;
    }

    class PlantHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre;
        TextView textViewMaceta;
        ImageView imageViewFumigate;
        ImageView imageViewPoda;
        ImageView imageViewFertilize;
        ImageView imageViewAptoBonzai;
        ImageView imageViewAptoVenta;
        CardView cardViewPlanta;
        Plant plantita;

        public PlantHolder(@NonNull View itemView) {
            super(itemView);
            cardViewPlanta = itemView.findViewById(R.id.card_planta);
            textViewNombre = itemView.findViewById(R.id.text_view_nombre);
            textViewMaceta = itemView.findViewById(R.id.text_view_maceta);
            imageViewFumigate = itemView.findViewById(R.id.image_view_fumigate);
            imageViewPoda = itemView.findViewById(R.id.image_view_poda);
            imageViewFertilize = itemView.findViewById(R.id.image_view_fertilize);
            imageViewAptoBonzai = itemView.findViewById(R.id.image_view_checked_bonzai);
            imageViewAptoVenta = itemView.findViewById(R.id.image_view_checked_venta);
        }
    }
}

