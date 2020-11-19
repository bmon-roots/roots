package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.Date;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Plant;

import static android.content.Context.ALARM_SERVICE;

public class DetailFragment extends DialogFragment {

    private DetailViewModel detailViewModel;
    private FirebaseFirestore db;
    private Plant planta;
    private View viewReference;
    private View viewAddTaskCustomDialog;
    private MenuItem editMenuItem;
    private MenuItem saveChangesMenuItem;
    private MenuItem deleteMenuItem;
    private MenuItem addTaskMenuItem;
    private AlertDialog.Builder dialog;


    public static final String TAG = DetailFragment.class.getSimpleName();


    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        createNotificationChannel(); // Para las notificaciones de tareas
        viewReference = inflater.inflate(R.layout.fragment_detail, container, false);
        viewAddTaskCustomDialog = getLayoutInflater().inflate(R.layout.add_task_fragment, null);
        db = FirebaseFirestore.getInstance();
        DetailViewModel model = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        planta = model.getSelected().getValue();
        loadDetailValue(viewReference);
        return viewReference;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckBox checkFumigate = (CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_fumigate);
        CheckBox checkPrune = (CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_prune);
        CheckBox checkFertilize = (CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_fertilize);

        checkFumigate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    ((EditText)viewAddTaskCustomDialog.findViewById(R.id.editTextFumigatePeriodicity)).getText().clear();
                }
            }
        });
        checkPrune.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    ((EditText)viewAddTaskCustomDialog.findViewById(R.id.editTextPrunePeriodicity)).getText().clear();
                }
            }
        });
        checkFertilize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    ((EditText)viewAddTaskCustomDialog.findViewById(R.id.editTextFertilizePeriodicity)).getText().clear();
                }
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_plant, menu);
        editMenuItem = menu.findItem(R.id.menu_edit_plant_button);
        saveChangesMenuItem = menu.findItem(R.id.menu_save_changes_button);
        deleteMenuItem = menu.findItem(R.id.menu_delete_plant_button);
        addTaskMenuItem = menu.findItem(R.id.menu_add_task_button);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectionId = item.getItemId();

        switch (selectionId) {
            case R.id.menu_edit_plant_button:
                habilitarEdicion(viewReference);
                break;
            case R.id.menu_delete_plant_button:
                crearDialogoEliminar();
                break;
            case R.id.menu_save_changes_button:
                crearDialogoGuardar();
                break;
            case R.id.menu_add_task_button:
                agregarTareaDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void agregarTareaDialog() {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getActivity());
        alertDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));

        AlertDialog alert;
        alertDialog.setTitle(Constants.ADD_NEW_TASK_TITLE);
        if(null != planta.getTareas()) {
            for (int i = 0; i < planta.getTareas().size(); i++) {
                if (planta.getTareas().get(i).getTipo().equals(Constants.ADD_TASK_FUMIGATE)) {
                    ((CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_fumigate)).setChecked(true);
                    EditText fumigatePeriodicity = (EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextFumigatePeriodicity);
                    fumigatePeriodicity.setText(String.valueOf(planta.getTareas().get(i).getPeriodicidadDias()));
                } else if (planta.getTareas().get(i).getTipo().equals(Constants.ADD_TASK_PRUNE)) {
                    ((CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_prune)).setChecked(true);
                    ((EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextPrunePeriodicity)).setText(String.valueOf(planta.getTareas().get(i).getPeriodicidadDias()));
                } else if (planta.getTareas().get(i).getTipo().equals(Constants.ADD_TASK_FERTILIZE)) {
                    ((CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_fertilize)).setChecked(true);
                    ((EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextFertilizePeriodicity)).setText(String.valueOf(planta.getTareas().get(i).getPeriodicidadDias()));
                }
            }
        }
        alertDialog.setView(viewAddTaskCustomDialog);
        alertDialog.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup)viewAddTaskCustomDialog.getParent()).removeView(viewAddTaskCustomDialog);
            }
        });
        alertDialog.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (((CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_fumigate)).isChecked()) {
                    EditText fumigatePeriodicity = (EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextFumigatePeriodicity);
                    planta.addTask(new Date(), Constants.ADD_TASK_FUMIGATE, Integer.valueOf(fumigatePeriodicity.getText().toString()));
                    createAlarmForTask(fumigatePeriodicity.getText().toString(), Constants.ADD_TASK_FUMIGATE);
                } else {
                    ((EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextFumigatePeriodicity)).getText().clear();
                    planta.removeTask(Constants.ADD_TASK_FUMIGATE);
                }
                if (((CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_prune)).isChecked()) {
                    EditText prunePeriodicity = (EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextPrunePeriodicity);
                    planta.addTask(new Date(), Constants.ADD_TASK_PRUNE, Integer.valueOf(prunePeriodicity.getText().toString()));
                    createAlarmForTask(prunePeriodicity.getText().toString(), Constants.ADD_TASK_PRUNE);
                } else {
                    ((EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextPrunePeriodicity)).getText().clear();
                    planta.removeTask(Constants.ADD_TASK_PRUNE);
                }
                if (((CheckBox) viewAddTaskCustomDialog.findViewById(R.id.check_task_fertilize)).isChecked()) {
                    EditText fertilizePeriodicity = (EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextFertilizePeriodicity);
                    planta.addTask(new Date(), Constants.ADD_TASK_FERTILIZE, Integer.valueOf(fertilizePeriodicity.getText().toString()));
                    createAlarmForTask(fertilizePeriodicity.getText().toString(), Constants.ADD_TASK_FERTILIZE);
                } else {
                    ((EditText) viewAddTaskCustomDialog.findViewById(R.id.editTextFertilizePeriodicity)).getText().clear();
                    planta.removeTask(Constants.ADD_TASK_FERTILIZE);
                }
                ((ViewGroup)viewAddTaskCustomDialog.getParent()).removeView(viewAddTaskCustomDialog);
                saveTaskToPlant();
            }
        });
        alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

    }

    private void savePlanta(View root) {
        DocumentReference docRef = db.collection(Constants.PLANT_COLLECTION).document(planta.getId());

        //Bind viewElements
        EditText especie = root.findViewById(R.id.editTextEspecie);
        EditText nombre = (EditText) root.findViewById(R.id.editTextNombre);
        EditText altura = root.findViewById(R.id.editTextAltura);
        EditText contenedor = (EditText)root.findViewById(R.id.editTextContenedor);
        EditText origen = (EditText)root.findViewById(R.id.editTextOrigen);
        EditText edad = (EditText)root.findViewById(R.id.editTextEdad);
        EditText fechaRegistro = (EditText)root.findViewById(R.id.editTextFechaRegistro);
        EditText ph = (EditText) root.findViewById(R.id.editTextPH);
        Switch aptoBonsai = (Switch) root.findViewById(R.id.switchAptoBonsai);
        Switch aptoVenta = (Switch) root.findViewById(R.id.switchAptoVenta);

        //Bind new values to store
        String newPlantName = nombre.getText().toString();
        String newPlantHeight = altura.getText().toString();
        String newPlantContainer = contenedor.getText().toString();
        String newPlantOrigin = origen.getText().toString();
        String newPlantAge = edad.getText().toString();
        String newPlantPH = ph.getText().toString();
        Boolean newPlantBonsaiStatus = aptoBonsai.isChecked();
        Boolean newPlantSaleableStatus = aptoVenta.isChecked();


        docRef.update(
                "age", newPlantAge,
                "bonsaiAble", newPlantBonsaiStatus,
                "container", newPlantContainer,
                "height", newPlantHeight,
                "name", newPlantName,
                "origin", newPlantOrigin,
                "ph", newPlantPH,
                "saleable", newPlantSaleableStatus
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Planta Modificada");
                        Toast.makeText(getContext(), R.string.msj_modificacion_ok, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al modificar Planta" + " " + e.getCause());
                        Toast.makeText(getContext(), R.string.msj_modificacion_error, Toast.LENGTH_LONG);

                    }
                });
        Navigation.findNavController(root).navigate(R.id.nav_plant);
    }

    private void saveTaskToPlant(){
        DocumentReference docRef = db.collection(Constants.PLANT_COLLECTION).document(planta.getId());
        docRef.update(
                "tareas", planta.getTareas()
        )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Tareas agregadas a la planta: " + planta.getId());
                        Toast.makeText(getContext(), R.string.msj_agregar_ok, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error al modificar Planta" + " " + e.getCause());
                        Toast.makeText(getContext(), R.string.msj_agregar_error + " " + e.getMessage(), Toast.LENGTH_LONG);

                    }
                });
    }

    private void eliminarPlanta(final View root, Plant planta) {
        DocumentReference docRef = db.collection(Constants.PLANT_COLLECTION).document(planta.getId());
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
        Navigation.findNavController(root).navigate(R.id.nav_plant);

    }

    private void habilitarEdicion(View root) {
        editMenuItem.setVisible(false);
        saveChangesMenuItem.setVisible(true);
        root.findViewById(R.id.editTextNombre).setEnabled(true);
        root.findViewById(R.id.editTextAltura).setEnabled(true);
        root.findViewById(R.id.editTextContenedor).setEnabled(true);
        root.findViewById(R.id.editTextOrigen).setEnabled(true);
        root.findViewById(R.id.editTextEdad).setEnabled(true);
        root.findViewById(R.id.editTextPH).setEnabled(true);
        root.findViewById(R.id.switchAptoBonsai).setEnabled(true);
        root.findViewById(R.id.switchAptoVenta).setEnabled(true);
    }

    private void crearDialogoEliminar() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
        builder.setMessage(R.string.dialog_delete)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eliminarPlanta(viewReference, planta);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                TAG.
                        System.out.println("Canceló eliminar");
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void crearDialogoGuardar() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
        builder.setMessage(R.string.dialog_edit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        savePlanta(viewReference);
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

    private void loadDetailValue(View root){
        EditText nombre = (EditText) root.findViewById(R.id.editTextNombre);
        nombre.setText(planta.getName());

        EditText especie = (EditText)root.findViewById(R.id.editTextEspecie);
        especie.setText(planta.getSpecies());

        EditText altura = (EditText)root.findViewById(R.id.editTextAltura);
        altura.setText(planta.getHeight());

        EditText contenedor = (EditText)root.findViewById(R.id.editTextContenedor);
        contenedor.setText(planta.getContainer());

        EditText origen = (EditText)root.findViewById(R.id.editTextOrigen);
        origen.setText(planta.getOrigin());

        EditText edad = (EditText)root.findViewById(R.id.editTextEdad);
        edad.setText(planta.getAge());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String registrationDate = dateFormat.format(planta.getRegistrationDate());

        EditText fechaRegistro = (EditText)root.findViewById(R.id.editTextFechaRegistro);
        fechaRegistro.setText(registrationDate);

        EditText ph = (EditText) root.findViewById(R.id.editTextPH);
        ph.setText(planta.getPh());

        Switch aptoBonsai = (Switch) root.findViewById(R.id.switchAptoBonsai);
        aptoBonsai.setChecked(planta.isBonsaiAble());

        Switch aptoVenta = (Switch) root.findViewById(R.id.switchAptoVenta);
        aptoVenta.setChecked(planta.isSaleable());

        ImageView imageViewPlant = root.findViewById(R.id.imageViewPlant);

        if (planta.getImageUri() != null && !planta.getImageUri().equals("")) {
            Picasso.get().load(planta.getImageUri()).into(imageViewPlant);
        }

    }

    private void createAlarmForTask(String diaVencimiento, String tarea){
        Intent intent = new Intent(getContext(),ReminderBroadcast.class);
        intent.putExtra("tarea", tarea);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

        Instant now =  Instant.now();
        Instant expiration = now.plus(Integer.parseInt(diaVencimiento), ChronoUnit.SECONDS);

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