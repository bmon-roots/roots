package ar.edu.ort.bmon.rootsapp.ui.event;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;

public class CreateEventFragment extends Fragment {

    private CreateEventViewModel mViewModel;
    private View viewReference;
    private TextView selectedEventId;
    private ImageView eventImage;
    private Fragment fragmentOptions;
    private int selectedOption;

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
        String[] eventOptions = new String[] { Constants.GERMINATION, Constants.CUTTING };
        selectedEventId = viewReference.findViewById(R.id.textViewSelectedEventType);
        eventImage = viewReference.findViewById(R.id.eventImageView);
        selectedOption = Integer.parseInt(getArguments().getString(Constants.SELECTED_EVENT));
        fragmentOptions = initFragmentOptions(selectedOption);
        getChildFragmentManager().beginTransaction().add(R.id.frameOptionsLayout, fragmentOptions).commit();
        selectedEventId.setText(eventOptions[selectedOption]);
        Uri eventImageUri = getImageForEventType(selectedOption);
        eventImage.setImageURI(eventImageUri);
        return viewReference;
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
        saveEventToFirebase(selectedOption);
        return super.onOptionsItemSelected(item);
    }

    private void saveEventToFirebase(int selectedOption) {

    }

    private Fragment initFragmentOptions(int selectedOption) {
        Fragment fragmentOptions = selectedOption == 0 ? new GerminationOptions() : new CuttingOptions();
        return fragmentOptions;
    }

    private Uri getImageForEventType(int selectedOption) {
        String imageForEvent = selectedOption == 0 ? "ic_sprouts" : "ic_germination";
        return Uri.parse("android.resource://ar.edu.ort.bmon.rootsapp/drawable/" + imageForEvent);
    }

}