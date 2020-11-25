package ar.edu.ort.bmon.rootsapp.ui.report;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.JumpLine;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;

public class ReportFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<DocumentSnapshot> documents;
    private ArrayList<Event> eventos = new ArrayList<>();
    private AnyChartView anyChartView;
    private TextView textoCutting;
    private TextView textWeather;
    private final ArrayList<String> speciesList = new ArrayList<>();
    private int selectedSpeciesId;
    private TextView selectedSpeciesName;
    private String selectedEventSpecie;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.root = inflater.inflate(R.layout.fragment_report, container, false);
        textoCutting = root.findViewById(R.id.text_view_header_cutting);
        anyChartView = root.findViewById(R.id.anyChartEventReport);

        textWeather = root.findViewById(R.id.text_view_germination_weather);

        selectedSpeciesName = root.findViewById(R.id.text_view_germination_weather);
        getSpeciesList();

        textoCutting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(view).navigate(R.id.nav_cutting_report);
                    }
                }
        );

        textWeather.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createSpeciesListDialog();
                    }
                }
        );

        anyChartView.setZoomEnabled(true);
        Task<QuerySnapshot> future = db.collection(Constants.EVENTS_COLLECTION).get();

        future.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    documents = task.getResult().getDocuments();
                    for (DocumentSnapshot document : task.getResult()) {
                        Event evento = document.toObject(Event.class);
                        addToList(evento);
                    }
                    setupVerticalChart();
                }
            }
        });

        return root;
    }

    private void getSpeciesList() {
        db.collection(Constants.SPECIES_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            speciesList.add(document.getString("name"));
                        }
                    }
                });
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
                selectedSpeciesId = (Integer) selectionList.getTag();
                selectedEventSpecie = speciesList.get(selectedSpeciesId);
                if (thereAreEventsOfThis(selectedEventSpecie) != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("especie", selectedEventSpecie);

                    Navigation.findNavController(root).navigate(R.id.nav_germination_conditions_report, bundle);
//                    setupWeatherPieChart(selectedEvent);
                } else {
                    MaterialAlertDialogBuilder errorDialog = new MaterialAlertDialogBuilder(getActivity());
                    errorDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg));
                    errorDialog.setTitle("Ups");
                    errorDialog.setMessage("No hay eventos para esta especie");
                    errorDialog.setNegativeButton(Constants.ACCEPT_BUTTON, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    errorDialog.create().show();
                }
                dialog.dismiss();
            }
        });
        speciesDialog.create().show();
    }

    public void setupVerticalChart() {
        String[] especies = speciesOnEvents();
        Integer[] cantidades = quantitiesOnEvents();

        Cartesian vertical = AnyChart.vertical();

        vertical.animation(true)
                .title("Rendimiento por especies");

        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < especies.length; i++) {
            dataEntries.add(new ValueDataEntry(especies[i], cantidades[i]));
        }


        Set set = Set.instantiate();
        set.data(dataEntries);
        Mapping barData = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping jumpLineData = set.mapAs("{ x: 'x', value: 'jumpLine' }");

        Bar bar = vertical.bar(barData);
        bar.labels().format("{%Value} %");

        JumpLine jumpLine = vertical.jumpLine(jumpLineData);
        jumpLine.stroke("2 #60727B");
        jumpLine.labels().enabled(false);

        vertical.yScale().minimum(0d);

        vertical.labels(true);

        vertical.tooltip()
                .displayMode(TooltipDisplayMode.UNION)
                .positionMode(TooltipPositionMode.POINT)
                .unionFormat(
                        "function() {\n" +
                                "      return 'Rendimiento:' + this.points[1].value + '%'" +
                                "    }");

        vertical.interactivity().hoverMode(HoverMode.BY_X);

        vertical.xAxis(true);
        vertical.yAxis(true);
        vertical.yAxis(0).labels().format("{%Value} %");

        anyChartView.setChart(vertical);
    }


    private Event thereAreEventsOfThis(final String especie) {
        return this.eventos.stream().filter(new Predicate<Event>() {
            @Override
            public boolean test(Event evento) {
                return evento.getEspecie().equals(especie);
            }
        })
                .findAny()
                .orElse(null);
    }

    private String[] speciesOnEvents() {
        final ArrayList<String> especies = new ArrayList<String>();

        this.eventos.stream().distinct().forEach(new Consumer<Event>() {
            @Override
            public void accept(Event evento) {
                especies.add(evento.getEspecie());
            }
        });

        String[] arrayEspecies = especies.toArray(new String[especies.size()]);

        return arrayEspecies;
    }

    private Integer[] quantitiesOnEvents() {
        final ArrayList<Integer> cantidades = new ArrayList<Integer>();


        this.eventos.stream().distinct().forEach(new Consumer<Event>() {
            @Override
            public void accept(Event evento) {
                float promedio = ((float) evento.getCantidadActivas() / (float) evento.getCantidadInicial()) * 100;
                cantidades.add((int) promedio);
            }
        });

        Integer[] arrayEspecies = cantidades.toArray(new Integer[cantidades.size()]);

        return arrayEspecies;
    }

    private void addToList(Event evento) {
        if (evento.getTipo().equals("Germinaciones")) {
            this.eventos.add(evento);
        }
    }


}