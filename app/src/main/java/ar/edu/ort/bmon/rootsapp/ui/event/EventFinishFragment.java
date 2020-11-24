package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;

public class EventFinishFragment extends Fragment {

    private EventFinishViewModel mViewModel;
    private EventDetailViewModel eventDetailViewModel;
    private ImageView eventImage;
    private View viewReference;
    private Event event;
    private FirebaseFirestore db;
    private String eventId;

    public static EventFinishFragment newInstance() {
        return new EventFinishFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewReference = inflater.inflate(R.layout.event_finish_fragment, container, false);
        EventDetailViewModel model = new ViewModelProvider(requireActivity()).get(EventDetailViewModel.class);
        eventImage = viewReference.findViewById(R.id.eventFinishImageView);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        event = model.getSelected().getValue();
        eventId = model.getIdSelected().getValue();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(Constants.EVENTS_COLLECTION).document(eventId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    event = (task.getResult().toObject(Event.class));
                    loadFinishValue(viewReference);
                    setEventImage(event.getTipo());
                }
            }
        });


        CardView cardViewReturn = viewReference.findViewById(R.id.cardViewReturn);
        cardViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(viewReference).navigate(R.id.nav_event);

            }
        });
        return viewReference;
    }

    private void loadFinishValue(View root) {

        ImageView imageViewPlant = root.findViewById(R.id.eventFinishImageView);

        TextView tipoEventoTV = (TextView) root.findViewById(R.id.textViewSelectedEventFinishType);
        tipoEventoTV.setText(event.getTipo());

        TextView especieTV = (TextView) root.findViewById(R.id.textViewSelectedSpecies);
        especieTV.setText(event.getEspecie());

        Double porcentaje = (new Double(event.getCantidadActivas()) * 100) / event.getCantidadInicial();

        EditText porcentajeEfectividad = (EditText) root.findViewById(R.id.editTextPorcentajeEfectividad);
        porcentajeEfectividad.setText(String.valueOf(porcentaje).concat(" %"));

//        EditText cantidadET = (EditText) root.findViewById(R.id.editTextCantidadEventoFinish);
//        cantidadET.setText(String.valueOf(event.getCantidadActivas()));
//

//        EditText rangoHumedad = (EditText)root.findViewById(R.id.editTextRangoHumedad);
//        rangoHumedad.setText(String.valueOf(event.getHumedad()));
//
//        EditText rangoPH = (EditText)root.findViewById(R.id.editTextRangoPH);
//        rangoPH.setText(String.valueOf(event.getPh()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String fechaInicio = (null != event.getFechaInicio()) ? dateFormat.format(event.getFechaInicio()) : "";
        EditText fechaInicialEvento = (EditText)root.findViewById(R.id.editTextInitialDateFinish);
        fechaInicialEvento.setText(fechaInicio);

        String fechaFin = (null != event.getFechaFinalizacion()) ? dateFormat.format(event.getFechaFinalizacion()) : "";
        EditText fechaFinalEvento = (EditText)root.findViewById(R.id.editTextFechaFin);
        fechaFinalEvento.setText(fechaFin);

        int milisecondsByDay = 86400000;
        int diferencia = (!fechaInicio.equals("") && !fechaFin.equals("")) ? (int)  (event.getFechaFinalizacion().getTime() - event.getFechaInicio().getTime()) / milisecondsByDay : -1;

        EditText diasDuracion = (EditText)root.findViewById(R.id.editTextCantidadDias);
        if ((diferencia >= 0)) {
            diasDuracion.setText(Integer.toString(diferencia));
        }

//        String nuevosBrotesDate = dateFormat.format(event.getPrimerosBrotes());
//
//        EditText fechaNuevosBrotes = (EditText)root.findViewById(R.id.editTextFechaNuevosBrotes);
//        fechaNuevosBrotes.setText(nuevosBrotesDate);
//
//        String mediosBrotesDate = dateFormat.format(event.getBrotoLaMitad());
//
//        EditText fechaMediosBrotes = (EditText)root.findViewById(R.id.editTextFechaMitadBrotes);
//        fechaMediosBrotes.setText(mediosBrotesDate);

    }

    private void setEventImage(String eventName) {
        Uri eventImageUri = getImageForEventType(eventName);
        eventImage.setImageURI(eventImageUri);
    }
    private Uri getImageForEventType(String eventName) {
        String imageForEvent = eventName.equals(Constants.CUTTING)  ? "ic_sprouts" : "ic_germination";
        return Uri.parse("android.resource://ar.edu.ort.bmon.rootsapp/drawable/" + imageForEvent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventFinishViewModel.class);
        // TODO: Use the ViewModel
    }

}