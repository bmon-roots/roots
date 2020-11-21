package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;
import ar.edu.ort.bmon.rootsapp.model.Material;
import ar.edu.ort.bmon.rootsapp.model.TipoTarea;
import ar.edu.ort.bmon.rootsapp.ui.plant.DetailViewModel;
import ar.edu.ort.bmon.rootsapp.ui.plant.ReminderBroadcast;

import static android.content.Context.ALARM_SERVICE;

public class EventDetailFragment extends DialogFragment {

    private EventDetailViewModel eventDetailViewModel;
    private View viewReference;
    private ImageView eventImage;
    private MenuItem editMenuItem;
    private MenuItem deleteMenuItem;
    private MenuItem saveChangesMenuItem;
    private FirebaseFirestore db;
    private Event event;
    private String eventId;


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
        createNotificationChannel();
        viewReference = inflater.inflate(R.layout.event_detail_fragment, container, false);
        EventDetailViewModel model = new ViewModelProvider(requireActivity()).get(EventDetailViewModel.class);
        eventImage = viewReference.findViewById(R.id.eventDetailImageView);

        db = FirebaseFirestore.getInstance();
        event = model.getSelected().getValue();
        eventId = model.getIdSelected().getValue();
//        event = new Event(Constants.CUTTING, "especie prueba", 0, 40, new Date(), new Date(), new Date(), new Date(), 33, 85, 7, TipoTarea.Bajar_Humedad, new Date());
        loadDetailValue(viewReference);
        setEventImage(event.getTipo());
        return viewReference;
    }

    private void loadDetailValue(View root) {

        ImageView imageViewPlant = root.findViewById(R.id.eventDetailImageView);

//        if (event.getImageUri() != null && !event.getImageUri().equals("")) {
//            Picasso.get().load(event.getImageUri()).into(imageViewPlant);
//        }

        TextView tipoEventoTV = (TextView) root.findViewById(R.id.textViewSelectedEventDetailType);
        tipoEventoTV.setText(event.getTipo());

        TextView especieTV = (TextView) root.findViewById(R.id.textViewSelectedSpecies);
        especieTV.setText(event.getEspecie());

        EditText cantidadET = (EditText) root.findViewById(R.id.editTextCantidadEventoDetail);
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
        eventDetailViewModel = ViewModelProviders.of(this).get(EventDetailViewModel.class);
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
            case R.id.menu_edit_detail_add_task:
                addTaskDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addTaskDialog() {
        final String[] options = Constants.EVENT_OPTIONS;
        final boolean[] selectedOptions = new boolean[] {false, false, false};
        MaterialAlertDialogBuilder addTaskDialogBuilder = new MaterialAlertDialogBuilder(getContext());
        addTaskDialogBuilder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
        addTaskDialogBuilder.setTitle(Constants.ADD_NEW_TASK_TITLE);
        addTaskDialogBuilder.setMultiChoiceItems(options, selectedOptions, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selectedOptions[which] = isChecked;
            }
        });
        addTaskDialogBuilder.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        addTaskDialogBuilder.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int index = 0;
                int selectedPosition = -1;
                while (index < selectedOptions.length && selectedPosition == -1) {
                    if (selectedOptions[index]) {
                        selectedPosition = index;
                    }
                    index ++;
                };
                
                if (selectedPosition != -1) {
                    createAlarmForTaskInEvent(Constants.DEFAULT_REMAINDER_DURATION, options[index]);
                    Toast.makeText(getContext(), Constants.ADD_TASK_TO_EVENT_SUCCESS, Toast.LENGTH_LONG).show();
                }
            }
        });

        Dialog addTaskDialog = addTaskDialogBuilder.create();
        addTaskDialog.setCanceledOnTouchOutside(false);
        addTaskDialog.show();
    }

    private void deleteEventDialog() {
        MaterialAlertDialogBuilder deleteEventDialog = new MaterialAlertDialogBuilder(getContext());
        deleteEventDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
        deleteEventDialog.setMessage(R.string.dialog_delete)
                .setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteEvent(viewReference, event);
                    }
                })
                .setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                TAG.
                        System.out.println("Canceló eliminar");
                    }
                });
        deleteEventDialog.create().show();
    }

    private void deleteEvent(View viewReference, Event event) {
        DocumentReference docRef = db.collection(Constants.EVENTS_COLLECTION).document(eventId);
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Documento eliminado");
                        Toast.makeText(getContext(), Constants.DELETE_EVENT_SUCCESS, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al intentar eliminar");
                        Toast.makeText(getContext(), Constants.DELETE_EVENT_ERROR, Toast.LENGTH_LONG).show();
                    }
                });
        Navigation.findNavController(viewReference).navigate(R.id.nav_event);
    }

    private void saveEventDialog() {
        MaterialAlertDialogBuilder saveEventDialogBuilder = new MaterialAlertDialogBuilder(getContext());
        saveEventDialogBuilder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
        saveEventDialogBuilder.setMessage(R.string.dialog_edit)
                .setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveEvent(viewReference);
                    }
                })
                .setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                TAG.
                        // User cancelled the dialog
                    }
                });
        saveEventDialogBuilder.create().show();
    }

    private void saveEvent(View root) {
        DocumentReference docRef = db.collection(Constants.EVENTS_COLLECTION).document(eventId);

        EditText cantidadET = (EditText) root.findViewById(R.id.editTextCantidadEventoDetail);
        EditText rangoTemperaturaET = (EditText)root.findViewById(R.id.editTextRangoTemperaturas);
        EditText rangoHumedadET = (EditText)root.findViewById(R.id.editTextRangoHumedad);
        EditText rangoPhET = (EditText)root.findViewById(R.id.editTextRangoPH);
        EditText fechaNuevosBrotesET = (EditText)root.findViewById(R.id.editTextFechaNuevosBrotes);

        Integer cantidad = Integer.valueOf(cantidadET.getText().toString());
        Double temperatura = Double.valueOf(rangoTemperaturaET.getText().toString());
        Double humedad = Double.valueOf(rangoHumedadET.getText().toString());
        Double rangoPh = Double.valueOf(rangoPhET.getText().toString());
        docRef.update(
                "temperatura", temperatura,
                "humedad", humedad,
                "ph", rangoPh,
                "brotoLaMitad", new Date(),
                "primerosBrotes", new Date(),
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
        root.findViewById(R.id.editTextFechaMitadBrotes).setEnabled(true);
    }

    private void createAlarmForTaskInEvent(int expiryDate, String task){
        Intent intent = new Intent(getContext(), ReminderBroadcast.class);
        intent.putExtra("tarea", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

        Instant now =  Instant.now();
        Instant expiration = now.plus(expiryDate, ChronoUnit.DAYS);

        long expirationTime = expiration.toEpochMilli();

        alarmManager.set(AlarmManager.RTC_WAKEUP,expirationTime, pendingIntent);
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ORTReminderChannel";
            String description = "Channel para remiender de ORT";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyORT", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}