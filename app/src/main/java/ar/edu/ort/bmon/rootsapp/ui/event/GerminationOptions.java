package ar.edu.ort.bmon.rootsapp.ui.event;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.ui.plant.DatePickerFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GerminationOptions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GerminationOptions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Date userSelectedStrataDate;
    private EditText estimatedStrataDate;
    private View viewReference;

    public GerminationOptions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GerminationOptions.
     */
    // TODO: Rename and change types and number of parameters
    public static GerminationOptions newInstance(String param1, String param2) {
        GerminationOptions fragment = new GerminationOptions();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewReference = inflater.inflate(R.layout.fragment_germination_options, container, false);
        estimatedStrataDate = viewReference.findViewById(R.id.editTextFechaEstrata);
        estimatedStrataDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogStrata(getChildFragmentManager());
            }
        });
        return viewReference;
    }

    public Bundle getData() {
        Bundle bundle = new Bundle();
        bundle.putString("EstimatedStrata", userSelectedStrataDate.toString());
        return bundle;
    }

    private void showDatePickerDialogStrata(FragmentManager fragmentManager) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(true, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
                estimatedStrataDate.setText(selectedDate);
                userSelectedStrataDate = getUserSelectedDate(year, month, day);
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