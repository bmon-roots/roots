package ar.edu.ort.bmon.rootsapp.ui.event;

import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;


public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventHolder> {


    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model) {
        if(model.getTipo().equals(Constants.GERMINATION)){
            holder.eventTypeImage.setBackgroundResource(R.drawable.ic_germination);
        } else {
            holder.eventTypeImage.setBackgroundResource(R.drawable.ic_sprouts);

        }

    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        return new EventAdapter.EventHolder(view);
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        TextView eventGroup;
        ImageView eventTypeImage;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventGroup = itemView.findViewById(R.id.text_view_grupo_especie);
            eventTypeImage = itemView.findViewById(R.id.image_view_grupo);
        }
    }
}
