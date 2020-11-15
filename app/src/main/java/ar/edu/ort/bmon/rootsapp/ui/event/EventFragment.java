package ar.edu.ort.bmon.rootsapp.ui.event;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;

public class EventFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerViewGermination;
    private RecyclerView recyclerViewCutting;
    private EventAdapter eventAdapter;
    private MenuItem createNewEvent;
    private View viewReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewReference = inflater.inflate(R.layout.event_fragment, container, false);

        recyclerViewGermination = viewReference.findViewById(R.id.recyclerGerminacion);
        recyclerViewCutting = viewReference.findViewById(R.id.recyclerCutting);
        recyclerViewGermination.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCutting.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection(Constants.EVENTS_COLLECTION);

        FirestoreRecyclerOptions<Event> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Event>()
                        .setQuery(query, Event.class)
                        .build();

        eventAdapter = new EventAdapter(firestoreRecyclerOptions);

        recyclerViewGermination.setAdapter(eventAdapter);

        return viewReference;
>>>>>>> features/create_event
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_events_list, menu);
        createNewEvent = menu.findItem(R.id.menu_create_event);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        createNewEventDialog();        
        return super.onOptionsItemSelected(item);
    }

    private void createNewEventDialog() {
        AlertDialog.Builder createNewEventDialog = new AlertDialog.Builder(getActivity());
        createNewEventDialog.setTitle(Constants.CREATE_NEW_EVENT_TITLE);
        String[] eventOptions = new String[] { Constants.GERMINATION, Constants.CUTTING };
        createNewEventDialog.setSingleChoiceItems(eventOptions, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView selectionList = ((AlertDialog) dialog).getListView();
                selectionList.setTag(Integer.valueOf(which));
            }
        });
        createNewEventDialog.setNegativeButton(Constants.CANCEL_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        createNewEventDialog.setPositiveButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                ListView selectionList = ((AlertDialog) dialog).getListView();
                Integer selectedItemId = (Integer)selectionList.getTag();
                bundle.putString(Constants.SELECTED_EVENT, selectedItemId.toString());
                Navigation.findNavController(viewReference).navigate(R.id.action_nav_event_to_nav_create_event, bundle);
                dialog.dismiss();
            }
        });
        createNewEventDialog.create().show();
    }
}