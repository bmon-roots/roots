package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;
import ar.edu.ort.bmon.rootsapp.ui.plant.DetailViewModel;

public class EventDetailFragment extends DialogFragment {

    private EventDetailViewModel mViewModel;
    private View viewReference;
    private MenuItem editMenuItem;
    private MenuItem deleteMenuItem;
    private MenuItem saveChangesMenuItem;
    private FirebaseFirestore db;
    private Event event;


    public static EventDetailFragment newInstance() {
        return new EventDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewReference = inflater.inflate(R.layout.event_detail_fragment, container, false);
        EventDetailViewModel model = new ViewModelProvider(requireActivity()).get(EventDetailViewModel.class);
        db = FirebaseFirestore.getInstance();
//        event = model.getSelected().getValue();
        event = new Event();
//        event.setEspecie("Prueba ESP");
//        event.setCantidadActivas(45);
//        event.setTemperatura(32);
//        event.setHumedad(85);
//        event.setPh(7);
//        event.setPrimerosBrotes(new Date());
        loadDetailValue(viewReference);
        return viewReference;
    }

    private void loadDetailValue(View root) {

        ImageView imageViewPlant = root.findViewById(R.id.eventDetailImageView);

//        if (event.getImageUri() != null && !event.getImageUri().equals("")) {
//            Picasso.get().load(event.getImageUri()).into(imageViewPlant);
//        }

        TextView tipoEventoTV = (TextView) root.findViewById(R.id.textViewSelectedEventDetailType);
        tipoEventoTV.setText(event.getEspecie());

        TextView especieTV = (TextView) root.findViewById(R.id.textViewSelectedSpecies);
        especieTV.setText(event.getEspecie());

        EditText cantidadET = (EditText) root.findViewById(R.id.editTextCantidadEventoDetail);
        cantidadET.setText(String.valueOf(event.getPrivatecantidadActivas()));

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

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_event_detail, menu);
        editMenuItem = menu.findItem(R.id.menu_edit_detail_event_button);
        saveChangesMenuItem = menu.findItem(R.id.menu_save_detail_event_button);
        deleteMenuItem = menu.findItem(R.id.menu_delete_detail_event_button);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectionId = item.getItemId();

        switch (selectionId) {
            case R.id.menu_edit_detail_event_button:
                editionEnabled(viewReference);
                break;
            case R.id.menu_delete_detail_event_button:
                deleteEventDialog();
                break;
            case R.id.menu_save_detail_event_button:
                saveEventDialog();
                break;
            case R.id.menu_add_task_button:
                addTaskDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addTaskDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        AlertDialog alert;
        alertDialog.setTitle(Constants.ADD_NEW_TASK_TITLE);
        alertDialog.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

    }

    private void deleteEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteEvent(viewReference, event);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                TAG.
                        System.out.println("Canceló eliminar");
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteEvent(View viewReference, Event event) {
        DocumentReference docRef = db.collection(Constants.EVENTS_COLLECTION).document(event.getTipo());
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Documento eliminado");
                        Toast.makeText(getContext(), R.string.msj_eliminar_ok, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al intentar eliminar");
                        Toast.makeText(getContext(), R.string.msj_eliminar_error, Toast.LENGTH_LONG).show();
                    }
                });
        Navigation.findNavController(viewReference).navigate(R.id.nav_event);
    }

    private void saveEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_edit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveEvent(viewReference);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                TAG.
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveEvent(View root) {
        DocumentReference docRef = db.collection(Constants.EVENTS_COLLECTION).document(event.getTipo());

        EditText cantidadET = (EditText) root.findViewById(R.id.editTextCantidadEventoDetail);
        EditText rangoTemperaturaET = (EditText)root.findViewById(R.id.editTextRangoTemperaturas);
        EditText rangoHumedadET = (EditText)root.findViewById(R.id.editTextRangoHumedad);
        EditText rangoPhET = (EditText)root.findViewById(R.id.editTextRangoPH);
        EditText fechaNuevosBrotesET = (EditText)root.findViewById(R.id.editTextFechaNuevosBrotes);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String nuevosBrotesDate = dateFormat.format(fechaNuevosBrotesET.getText());

        String cantidad = cantidadET.getText().toString();
        String temperatura = rangoTemperaturaET.getText().toString();
        String humedad = rangoHumedadET.getText().toString();
        String rangoPh = rangoPhET.getText().toString();
        String fechaBrotes = nuevosBrotesDate;

        docRef.update(
                "temperatura", temperatura,
                "humedad", humedad,
                "ph", rangoPh,
                "brotoLaMitad", fechaBrotes,
                "cantidadActivas", cantidad
        )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Evento Modificado");
                        Toast.makeText(getContext(), R.string.msj_modificacion_ok, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al modificar Evento" + " " + e.getCause());
                        Toast.makeText(getContext(), R.string.msj_modificacion_error, Toast.LENGTH_LONG);

                    }
                });
        Navigation.findNavController(viewReference).navigate(R.id.nav_event);
    }

    private void editionEnabled(View root) {
        editMenuItem.setVisible(false);
        saveChangesMenuItem.setVisible(true);
        root.findViewById(R.id.editTextCantidadEventoDetail).setEnabled(true);
        root.findViewById(R.id.editTextRangoTemperaturas).setEnabled(true);
        root.findViewById(R.id.editTextRangoHumedad).setEnabled(true);
        root.findViewById(R.id.editTextRangoPH).setEnabled(true);
        root.findViewById(R.id.editTextFechaNuevosBrotes).setEnabled(true);
    }

}