package ar.edu.ort.bmon.rootsapp.ui.event;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.List;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;
import ar.edu.ort.bmon.rootsapp.model.Material;
import ar.edu.ort.bmon.rootsapp.model.TipoMaterial;

public class EventFinishFragment extends Fragment {

    private EventFinishViewModel mViewModel;
    private EventDetailViewModel eventDetailViewModel;
    private ImageView eventImage;
    private View viewReference;
    private Event event;
    private FirebaseFirestore db;
    private String eventId;
    private int cantidaMacetasNecesaria = 0, cantidaActivas = 0, cantidaSustratoNecesaria = 0, cantidadMacetasAcumuladas = 0, cantidadSustratoAcumulado = 0;


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
        eventId = model.getIdSelected().getValue();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(Constants.EVENTS_COLLECTION).document(eventId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    event = (task.getResult().toObject(Event.class));
                    cantidaActivas = event.getCantidadActivas();
                    cantidaSustratoNecesaria = event.getCantidadActivas();
                    cantidaMacetasNecesaria = event.getCantidadActivas();
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String fechaInicio = (null != event.getFechaInicio()) ? dateFormat.format(event.getFechaInicio()) : "";
        EditText fechaInicialEvento = (EditText)root.findViewById(R.id.editTextInitialDateFinish);
        fechaInicialEvento.setText(fechaInicio);

        String fechaFin = (null != event.getFechaFinalizacion()) ? dateFormat.format(event.getFechaFinalizacion()) : "";
        EditText fechaFinalEvento = (EditText)root.findViewById(R.id.editTextFechaFin);
        fechaFinalEvento.setText(fechaFin);

        int milisecondsByDay = 86400000;
        int diferenciaDias = (!fechaInicio.equals("") && !fechaFin.equals("")) ? (int)  (event.getFechaFinalizacion().getTime() - event.getFechaInicio().getTime()) / milisecondsByDay : -1;

        EditText diasDuracion = (EditText)root.findViewById(R.id.editTextCantidadDias);
        if ((diferenciaDias >= 0)) {
            diasDuracion.setText(Integer.toString(diferenciaDias));
        }
        Query macetasQuery = db.collection(Constants.MATERIAL_COLLECTION).whereEqualTo("tipoMaterial",
                TipoMaterial.Maceta).whereEqualTo("contenido", 1);
        Query sustratoQuery = db.collection(Constants.MATERIAL_COLLECTION).whereEqualTo("tipoMaterial",
                TipoMaterial.Sustrato);

        macetasQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Material material = document.toObject(Material.class);
                        int cantidad = material.getCantidad();
                        while (cantidad > 0 && cantidaMacetasNecesaria > cantidadMacetasAcumuladas){
                            cantidadMacetasAcumuladas++;
                            cantidad--;
                        }
                        DocumentReference dr = db.collection(Constants.MATERIAL_COLLECTION).document(document.getId());
                        dr.update("cantidad", cantidad);
                    }
                    loadInfoMacetas();
                }
            }
        });

        sustratoQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document: task.getResult()){
                        Material material = document.toObject(Material.class);
                        int cantidad = material.getCantidad();
                        while(cantidad > 0 && cantidaSustratoNecesaria > cantidadSustratoAcumulado) {
                            cantidadSustratoAcumulado++;
                            cantidad--;
                        }
                        DocumentReference dr = db.collection(Constants.MATERIAL_COLLECTION).document(document.getId());
                        dr.update("cantidad", cantidad);
                    }
                }
                loadInfoSustrato();
            }
        });

    }

    private void loadInfoMacetas(){
        EditText macetasET = viewReference.findViewById(R.id.editTextMacetas);
        int diferenciaMateriales = cantidadMacetasAcumuladas - cantidaMacetasNecesaria;
        if(diferenciaMateriales < 0){
            macetasET.setText("Necesitas " + Math.abs(diferenciaMateriales));
        }else{
            macetasET.setText("Tenés suficientes");
        }

    }

    private void loadInfoSustrato(){
        EditText macetasET = viewReference.findViewById(R.id.editTextSustrato);
        int diferenciaMateriales = cantidadSustratoAcumulado - cantidaSustratoNecesaria;
        if(diferenciaMateriales < 0){
            macetasET.setText("Necesitas " + Math.abs(diferenciaMateriales));
        }else{
            macetasET.setText("Tenés suficientes");
        }
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

    }

}