package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.net.Uri;
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
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;
import ar.edu.ort.bmon.rootsapp.ui.plant.DatePickerFragment;

public class CreateEventFragment extends Fragment {

    private CreateEventViewModel mViewModel;
    private View viewReference;
    private TextView selectedEventName;
    private ImageView eventImage;
    private Fragment fragmentOptions;
    private int selectedOption;
    private EditText quantity, tempRange, humidityRange, phRange, newSproutsDate;
    private Switch usedHormones;
    private TextView selectedSpeciesName;
    private final ArrayList<String> speciesList = new ArrayList<>();
    private int selectedSpeciesId;
    private Date userSelectedSproutDate;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewReference = inflater.inflate(R.layout.create_event_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        String[] eventOptions = new String[] { Constants.GERMINATION, Constants.CUTTING };
        selectedEventName = viewReference.findViewById(R.id.textViewSelectedEventType);
        eventImage = viewReference.findViewById(R.id.eventImageView);
        selectedOption = Integer.parseInt(getArguments().getString(Constants.SELECTED_EVENT));
        configureEventSubOptions();
        selectedEventName.setText(eventOptions[selectedOption]);
        setEventImage();
        initEditTexts();
        getSpeciesList();
        selectedSpeciesName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSpeciesListDialog();
            }
        });
        newSproutsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getChildFragmentManager());
            }
        });
        return viewReference;
    }

    private void createSpeciesListDialog() {
        MaterialAlertDialogBuilder speciesDialog = new MaterialAlertDialogBuilder(getActivity());
        speciesDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
        String[] speciesOptions = speciesList.toArray(new String[0]);
        speciesDialog.setTitle(Constants.SPECIES_SELECTION_DIALOG);
        speciesDialog.setSingleChoiceItems(speciesOptions, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                selectionList.setTag(Integer.valueOf(which));
            }
        });
        speciesDialog.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        speciesDialog.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                selectedSpeciesId = (Integer)selectionList.getTag();
                selectedSpeciesName.setText(speciesList.get(selectedSpeciesId));
                dialog.dismiss();
            }
        });
        speciesDialog.create().show();
    }

    private void getSpeciesList() {
        db.collection(Constants.SPECIES_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            speciesList.add(document.getString("name"));
                        }
                    }
                });
    }

    private void initEditTexts() {
        selectedSpeciesName = viewReference.findViewById(R.id.textViewSelectedSpecies);
        quantity = viewReference.findViewById(R.id.editTextCantidadEvento);
        tempRange = viewReference.findViewById(R.id.editTextRangoTemperaturas);
        humidityRange = viewReference.findViewById(R.id.editTextRangoHumedad);
        phRange = viewReference.findViewById(R.id.editTextRangoPH);
        newSproutsDate = viewReference.findViewById(R.id.editTextFechaNuevosBrotes);
    }

    private void setEventImage() {
        Uri eventImageUri = getImageForEventType(selectedOption);
        eventImage.setImageURI(eventImageUri);
    }

    private void configureEventSubOptions() {
        fragmentOptions = initFragmentOptions(selectedOption);
        getChildFragmentManager().beginTransaction().add(R.id.frameOptionsLayout, fragmentOptions).commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_event, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateEventViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectionId = item.getItemId();
        if (selectionId == R.id.menu_create_new_event) {
            saveEventToFirebase(selectedOption);
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEventToFirebase(int selectedOption) {
        Event eventToSave;
        if (selectedOption == 0) {
            Switch usoHormonas = viewReference.findViewById(R.id.switchHormones);
            GerminationOptions options = (GerminationOptions) fragmentOptions;
            eventToSave = new Event(
                    "Germinacion",
                    speciesList.get(selectedSpeciesId),
                    Integer.parseInt(quantity.getText().toString()),
                    new Date(),
                    userSelectedSproutDate,
                    Double.parseDouble(tempRange.getText().toString()),
                    Integer.parseInt(humidityRange.getText().toString()),
                    Double.parseDouble(phRange.getText().toString()),
                    options.getData().getBoolean("UsedHormones")
            );
        } else {
            CuttingOptions options = (CuttingOptions) fragmentOptions;
            eventToSave = new Event(
                    "Estratificacion",
                    speciesList.get(selectedSpeciesId),
                    Integer.parseInt(quantity.getText().toString()),
                    new Date(),
                    userSelectedSproutDate,
                    Double.parseDouble(tempRange.getText().toString()),
                    Integer.parseInt(humidityRange.getText().toString()),
                    Double.parseDouble(phRange.getText().toString()),
                    new Date(options.getData().getString("EstimatedStrata"))
            );
        }

        db.collection(Constants.EVENTS_COLLECTION).add(eventToSave)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getContext(), Constants.CREATE_EVENT_SUCCESS, Toast.LENGTH_LONG).show();
                        Navigation.findNavController(viewReference).navigate(R.id.action_nav_create_event_to_nav_event);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), Constants.CREATE_EVENT_ERROR, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private Fragment initFragmentOptions(int selectedOption) {
        Fragment fragmentOptions = selectedOption == 0 ? new GerminationOptions() : new CuttingOptions();
        return fragmentOptions;
    }

    private Uri getImageForEventType(int selectedOption) {
        String imageForEvent = selectedOption == 0 ? "ic_germination" : "ic_sprouts";
        return Uri.parse("android.resource://ar.edu.ort.bmon.rootsapp/drawable/" + imageForEvent);
    }

    private void showDatePickerDialog(FragmentManager fragmentManager) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
                newSproutsDate.setText(selectedDate);
                userSelectedSproutDate = getUserSelectedDate(year, month, day);
            }
        });
        newFragment.show(fragmentManager, "datePicker");
    }

    private Date getUserSelectedDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

}