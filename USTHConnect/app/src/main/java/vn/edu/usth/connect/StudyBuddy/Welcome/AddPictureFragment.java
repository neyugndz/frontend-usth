package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity;

public class AddPictureFragment extends Fragment {

    // Add Picture to Study Profile
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_add_picture.xml
         View v = inflater.inflate(R.layout.fragment_add_picture, container, false);

         // Button Function
        setup_function(v);

        return v;
    }

    private void setup_function(View v) {
        ImageButton back_button = v.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Start
        Button next_button = v.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), Study_Buddy_Activity.class);
                startActivity(i);
            }
        });

    }

}