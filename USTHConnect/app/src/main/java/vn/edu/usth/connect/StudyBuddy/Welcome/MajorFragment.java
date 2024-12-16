package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import vn.edu.usth.connect.R;

public class MajorFragment extends Fragment {

    private Button first_year, second_year, third_year, next_button;

    private String[] majors;

    private String hint = "Select your major";
    private String selectedYear = "";
    private String selectedMajor = "";

    // Select Major for Study Buddy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_major.xml
        View v = inflater.inflate(R.layout.fragment_major, container, false);

        // Call ID Button
        first_year = v.findViewById(R.id.year_b1);
        second_year = v.findViewById(R.id.year_b2);
        third_year = v.findViewById(R.id.year_b3);

        next_button = v.findViewById(R.id.next_button);

        majors = getResources().getStringArray(R.array.major);

        // Button Function
        setup_function(v);

        // Spinner Function
        SpinnerSelect(v);

        return v;
    }

    // Choose Major using Spinner
    private void SpinnerSelect(View v){
        Spinner spinner = v.findViewById(R.id.select_major);

        List<String> majorList = Arrays.asList(majors);
        SpinnerAdapter adapter = new SpinnerAdapter(requireContext(), majorList, hint);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMajor = adapterView.getItemAtPosition(i).toString();
                checkSelect();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedMajor = hint;
                checkSelect();
            }
        });
    }

    // Select Year
    private void yearSelect(String year) {
        first_year.setBackgroundResource(R.drawable.rounded_border);
        second_year.setBackgroundResource(R.drawable.rounded_border);
        third_year.setBackgroundResource(R.drawable.rounded_border);

        switch (year) {
            case "b1":
                first_year.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "b2":
                second_year.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "b3":
                third_year.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
        }

        selectedYear = year;

        checkSelect();
    }

    // Enable Button
    private void checkSelect(){
        if (!selectedMajor.equals(hint) && !selectedYear.isEmpty()) {
            next_button.setEnabled(true);
        } else {
            next_button.setEnabled(false);
        }
    }

    private void setup_function(View v) {
        ImageButton back_button = v.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        next_button.setEnabled(false);
        next_button.setOnClickListener(view -> {
            navigatorToNextFragment();
        });

        // Select Year Button
        first_year.setOnClickListener(view -> {
            yearSelect("b1");
        });

        second_year.setOnClickListener(view -> {
            yearSelect("b2");
        });

        third_year.setOnClickListener(view -> {
            yearSelect("b3");
        });

    }

    // Move to another Fragment
    private void navigatorToNextFragment() {
        Fragment majorFragment = new PersonalityFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, majorFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}