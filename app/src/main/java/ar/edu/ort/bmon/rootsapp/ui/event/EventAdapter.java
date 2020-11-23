package ar.edu.ort.bmon.rootsapp.ui.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.sql.SQLOutput;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;


public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventHolder> {

    public EventOnTextClickListener eventOnTextClickListener;
    public View view;
    public EventHolder eventHolder;

    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options, EventOnTextClickListener eventOnTextClickListener) {
        super(options);
        this.eventOnTextClickListener = eventOnTextClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model) {
        this.eventHolder = holder;
        final Event eventoSeleccionado = crearEventoDesdeModel(model);
        final DocumentSnapshot document = getSnapshots().getSnapshot(holder.getAdapterPosition());
        holder.eventCard.setVisibility(View.GONE);
        holder.eventCard.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        if(model.getTipo().equals(Constants.GERMINATION)){
            holder.eventCard.setVisibility(View.VISIBLE);
            holder.eventCard.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.eventGroup.setText(model.getEspecie());
            holder.eventTypeImage.setBackgroundResource(R.drawable.ic_germination);
        }

        this.eventHolder.eventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventDetailViewModel model = eventOnTextClickListener.onTextClick();
                model.select(eventoSeleccionado);
                model.selectId(document.getId());
                Navigation.findNavController(view).navigate(R.id.nav_event_detail);
            }
        });
    }

    private Event crearEventoDesdeModel(Event model) {
        Event evento = new Event();
        evento.setTipo(model.getTipo());
        evento.setEspecie(model.getEspecie());
        evento.setCantidadInicial(model.getCantidadInicial());
        evento.setCantidadActivas(model.getCantidadActivas());
        evento.setFechaInicio(model.getFechaInicio());
        evento.setFechaEstratificacion(model.getFechaEstratificacion());
        evento.setFechaFinalizacion(model.getFechaFinalizacion());
        evento.setPrimerosBrotes(model.getPrimerosBrotes());
        evento.setBrotoLaMitad(model.getBrotoLaMitad());
        evento.setHumedad(model.getHumedad());
        evento.setTemperatura(model.getTemperatura());
        evento.setPh(model.getPh());
        evento.setHumedad(model.getHumedad());
        evento.setUsoHormonas(model.isUsoHormonas());
        evento.setTarea(model.getTarea());
        return evento;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        return new EventAdapter.EventHolder(view);
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        CardView eventCard;
        TextView eventGroup;
        ImageView eventTypeImage;
        Event evento;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventGroup = itemView.findViewById(R.id.text_view_grupo_especie);
            eventTypeImage = itemView.findViewById(R.id.image_view_grupo);
            eventCard = itemView.findViewById(R.id.card_event_group);
        }
    }
}
