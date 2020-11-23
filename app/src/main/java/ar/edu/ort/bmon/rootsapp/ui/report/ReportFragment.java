package ar.edu.ort.bmon.rootsapp.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;

import ar.edu.ort.bmon.rootsapp.R;

public class ReportFragment extends Fragment {

    AnyChartView anyChartView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_report, container, false);

        anyChartView = root.findViewById(R.id.anyChartEventReport);



//        setupPieChart();

        return root;
    }

    public void setupPieChart(){
        String[] months = {"Enero", "Febrero", "Marzo"};
        int[] ganancias = {100, 300, 600};
        Pie pie = AnyChart.pie();

        ArrayList<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0 ; i < months.length; i++){
            dataEntries.add(new ValueDataEntry(months[i], ganancias[i]));

        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }

}