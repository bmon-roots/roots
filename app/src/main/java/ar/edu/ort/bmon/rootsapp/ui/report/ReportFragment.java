package ar.edu.ort.bmon.rootsapp.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;
import ar.edu.ort.bmon.rootsapp.model.Event;

public class ReportFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<DocumentSnapshot> documents;
    private ArrayList<Event> eventos = new ArrayList<>();
    private AnyChartView anyChartView;
    private TextView textoCutting;
    private TextView title;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_report, container, false);
        textoCutting = root.findViewById(R.id.text_view_header_cutting);
        anyChartView = root.findViewById(R.id.anyChartEventReport);
        title = root.findViewById(R.id.text_view_germination_title);

        textoCutting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(view).navigate(R.id.nav_cutting_report);
                    }
                }
        );

        title.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setupPieChart();
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
                    setupColumnChart();
                }
            }
        });

        return root;
    }

    public void setupPieChart(){
        String[] especies = speciesOnEvents();
        Integer[] cantidades = quantitiesOnEvents();

        Pie pie = AnyChart.pie();
        ArrayList<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < especies.length; i++) {
            dataEntries.add(new ValueDataEntry(especies[i], cantidades[i]));
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }


    public void setupColumnChart() {
        String[] especies = speciesOnEvents();
        Integer[] cantidades = quantitiesOnEvents();

        Cartesian cartesian = AnyChart.column();
        ArrayList<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < especies.length; i++) {
            dataEntries.add(new ValueDataEntry(especies[i], cantidades[i]));
        }

        Column column = cartesian.column(dataEntries);
        cartesian.credits().enabled(false);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");


        cartesian.animation(true);
        cartesian.title("Rendimiento por especies");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Especies");
        cartesian.yAxis(0).title("Activas");

        anyChartView.setChart(cartesian);
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
                cantidades.add(evento.getCantidadActivas());
            }
        });

        Integer[] arrayEspecies = cantidades.toArray(new Integer[cantidades.size()]);

        return arrayEspecies;
    }


    private Event crearEventoDesdeHashMap(DocumentSnapshot document) {
        Event evento = new Event();
        evento.setTipo(document.getString("tipo"));
        evento.setCantidadInicial(document.getDouble("cantidadInicial").intValue());
        evento.setEspecie(document.getString("especie"));

        return evento;
    }

    private void addToList(Event evento) {
        if (evento.getTipo().equals("Germinaciones")) {
            this.eventos.add(evento);
        }
    }

}