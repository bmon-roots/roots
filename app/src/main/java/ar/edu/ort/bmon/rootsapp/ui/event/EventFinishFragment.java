package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;

public class EventFinishFragment extends Fragment {

    private EventFinishViewModel mViewModel;
    private EventDetailViewModel eventDetailViewModel;
    private ImageView eventImage;
    private View viewReference;
    private Event event;
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
        loadFinishValue(viewReference);
        setEventImage(event.getTipo());

        return viewReference;
    }

    private void loadFinishValue(View root) {

        ImageView imageViewPlant = root.findViewById(R.id.eventFinishImageView);

        TextView tipoEventoTV = (TextView) root.findViewById(R.id.textViewSelectedEventFinishType);
        tipoEventoTV.setText(event.getTipo());

        TextView especieTV = (TextView) root.findViewById(R.id.textViewSelectedSpecies);
        especieTV.setText(event.getEspecie());

        EditText cantidadInicial = (EditText) root.findViewById(R.id.editTextCantidadEventoFinish2);
        cantidadInicial.setText(String.valueOf(event.getCantidadInicial()));

        EditText cantidadET = (EditText) root.findViewById(R.id.editTextCantidadEventoFinish);
        cantidadET.setText(String.valueOf(event.getCantidadActivas()));

        EditText rangoTemperatura = (EditText)root.findViewById(R.id.editTextRangoTemperaturas);
        rangoTemperatura.setText(String.valueOf(event.getTemperatura()));

        EditText rangoHumedad = (EditText)root.findViewById(R.id.editTextRangoHumedad);
        rangoHumedad.setText(String.valueOf(event.getHumedad()));

        EditText rangoPH = (EditText)root.findViewById(R.id.editTextRangoPH);
        rangoPH.setText(String.valueOf(event.getPh()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String nuevosBrotesDate = dateFormat.format(event.getPrimerosBrotes());

        EditText fechaNuevosBrotes = (EditText)root.findViewById(R.id.editTextFechaNuevosBrotes);
        fechaNuevosBrotes.setText(nuevosBrotesDate);

        String mediosBrotesDate = dateFormat.format(event.getBrotoLaMitad());

        EditText fechaMediosBrotes = (EditText)root.findViewById(R.id.editTextFechaMitadBrotes);
        fechaMediosBrotes.setText(mediosBrotesDate);

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