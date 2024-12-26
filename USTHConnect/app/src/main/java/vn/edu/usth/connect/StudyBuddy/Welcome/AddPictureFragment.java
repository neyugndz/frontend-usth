package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddyViewModel;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudyBuddyService;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity;

public class AddPictureFragment extends Fragment {

    private StudyBuddyViewModel studyBuddyViewModel;

    // Add Picture to Study Profile
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_add_picture.xml
         View v = inflater.inflate(R.layout.fragment_add_picture, container, false);

        // Initialize the ViewModel
        studyBuddyViewModel = new ViewModelProvider(requireActivity()).get(StudyBuddyViewModel.class);


        // Button Function
        setup_function(v);

        return v;
    }

    private void setup_function(View v) {
        ImageButton back_button = v.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        Button next_button = v.findViewById(R.id.next_button);
        next_button.setOnClickListener(view -> {
            // Retrieve all data from the ViewModel
            String name = studyBuddyViewModel.getName().getValue();
            String gender = studyBuddyViewModel.getGender().getValue();
            String personality = studyBuddyViewModel.getPersonality().getValue();
            String communicationStyle = studyBuddyViewModel.getCommunicationStyle().getValue();
            String lookingFor = studyBuddyViewModel.getLookingFor().getValue();
            List<String> interests = studyBuddyViewModel.getInterests().getValue();
            List<String> favoriteSubjects = studyBuddyViewModel.getFavoriteSubjects().getValue();
            List<String> preferredPlaces = studyBuddyViewModel.getPreferredPlaces().getValue();
            List<String> preferredTimes = studyBuddyViewModel.getPreferredTimes().getValue();

            // Save to backend
            saveToBackend(name, gender, personality, communicationStyle, lookingFor, interests, favoriteSubjects, preferredPlaces, preferredTimes);
            navigatorToNextFragment();
        });
    }

    private void saveToBackend(String name, String gender, String personality, String communicationStyle,
                               String lookingFor, List<String> interests, List<String> favoriteSubjects,
                               List<String> preferredPlaces, List<String> preferredTimes) {
        // Retrieve the token and studentId from SharedPreferences
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();

        // Check if token or studentId is missing
        if (token.isEmpty() || studentId.isEmpty()) {
            Toast.makeText(getContext(), "Authentication error: Token or Student ID is missing.", Toast.LENGTH_SHORT).show();
            return;
        }

        String authHeader = "Bearer " + token;

        // Prepare the StudyBuddy object
        StudyBuddy studyBuddy = new StudyBuddy(studentId, name, gender, personality, communicationStyle,
                lookingFor, interests, favoriteSubjects, preferredPlaces,
                preferredTimes);

        // Log the studyBuddy object as JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String studyBuddyJson = objectMapper.writeValueAsString(studyBuddy);
            Log.d("SaveToBackend", "Sending StudyBuddy JSON: " + studyBuddyJson);
        } catch (Exception e) {
            Log.e("SaveToBackend", "Error converting StudyBuddy to JSON", e);
        }

        StudyBuddyService studyBuddyService = RetrofitClient.getInstance().create(StudyBuddyService.class);

        studyBuddyService.saveStudyBuddy(authHeader, studyBuddy).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to save profile: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Move to another Fragment
    private void navigatorToNextFragment() {
        Fragment registerFragment = new Register_StudyBuddyFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, registerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}