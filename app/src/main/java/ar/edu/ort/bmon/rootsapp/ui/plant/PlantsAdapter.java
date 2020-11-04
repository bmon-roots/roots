package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.model.Plant;


public class PlantsAdapter extends FirestoreRecyclerAdapter<Plant, PlantsAdapter.PlantHolder> {

    public OnTextClickListener onTextClickListener;
    public DocumentSnapshot document;
    public DocumentSnapshot getDocument() {
        return document;
    }

    public PlantsAdapter(@NonNull FirestoreRecyclerOptions<Plant> options, OnTextClickListener onTextClickListener) {
        super(options);
        this.onTextClickListener = onTextClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlantHolder holder, int position, @NonNull Plant model) {

        document = getSnapshots().getSnapshot(holder.getAdapterPosition());

        holder.textViewNombre.setText(model.getSpecies());
        holder.textViewEdad.setText(model.getAge());
        holder.textViewMaceta.setText(model.getContainer());
        holder.plantita = crearPlantaDesdeModel(model, document.getId());
        holder.imageViewAptoBonzai.setVisibility(View.INVISIBLE);
        holder.imageViewAptoVenta.setVisibility(View.INVISIBLE);
        if(model.isBonsaiAble()){
            holder.imageViewAptoBonzai.setVisibility(View.VISIBLE);
        }
        if(model.isSaleable()){
            holder.imageViewAptoVenta.setVisibility(View.VISIBLE);
        }

    }

    private Plant crearPlantaDesdeModel(@NonNull Plant model, String id) {
        Plant planta = new Plant();
        planta.setId(id);
        planta.setName(model.getName());
        planta.setSpecies(model.getSpecies());
        planta.setImageUri(model.getImageUri());
        planta.setPh(model.getPh());
        planta.setHeight(model.getHeight());
        planta.setBonsaiAble(model.isBonsaiAble());
        planta.setSaleable(model.isSaleable());
        planta.setContainer(model.getContainer());
        planta.setAge(model.getAge());
        planta.setOrigin(model.getOrigin());
        planta.setRegistrationDate(model.getRegistrationDate());
        return planta;
    }

    @NonNull
    @Override
    public PlantHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final Context pContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        final PlantHolder pHolder = new PlantHolder(view);
        pHolder.cardViewPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailViewModel model = onTextClickListener.onTextClick();
                model.select(pHolder.plantita);
                Navigation.findNavController(view).navigate(R.id.nav_plant_detail);
            }
        });
        return pHolder;
    }

    class PlantHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre;
        TextView textViewEdad;
        TextView textViewMaceta;
        ImageView imageViewPoda;
        ImageView imageViewRiego;
        ImageView imageViewAptoBonzai;
        ImageView imageViewAptoVenta;
        CardView cardViewPlanta;
        Plant plantita;

        public PlantHolder(@NonNull View itemView) {
            super(itemView);
            cardViewPlanta = itemView.findViewById(R.id.card_planta);
            textViewNombre = itemView.findViewById(R.id.text_view_nombre);
            textViewEdad = itemView.findViewById(R.id.text_view_edad);
            textViewMaceta = itemView.findViewById(R.id.text_view_maceta);
            imageViewPoda = itemView.findViewById(R.id.image_view_poda);
            imageViewRiego = itemView.findViewById(R.id.image_view_riego);
            imageViewAptoBonzai = itemView.findViewById(R.id.image_view_checked_bonzai);
            imageViewAptoVenta = itemView.findViewById(R.id.image_view_checked_venta);
        }
    }
}

