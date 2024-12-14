package vn.edu.usth.connect.Schedule.TimeTable.Event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.Student.Student;
import vn.edu.usth.connect.Network.EventService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.StudentService;
import vn.edu.usth.connect.R;

public class EventActivity extends AppCompatActivity {

    private ListView eventListView;
    private EventAdapter eventAdapter;
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        eventListView = findViewById(R.id.eventListView);

        // Fetch Student Profile and Events
        fetchUserProfileAndEvents();
    }

    private void fetchUserProfileAndEvents() {
        // Get token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ToLogin", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", "");
        String studentId = sharedPreferences.getString("StudentId", "");

        if (!token.isEmpty() && !studentId.isEmpty()) {
            String authHeader = "Bearer " + token;

            // Create an instance of Retrofit and call the API
            StudentService studentService = RetrofitClient.getInstance().create(StudentService.class);
            Call<Student> call = studentService.getStudentProfile(authHeader, studentId);
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        currentStudent = response.body();
                        Log.d("EventActivity", "Student data: " + currentStudent);

                        // Fetch events using student data
                        String studyYear = currentStudent.getStudyYear();
                        String major = currentStudent.getMajor();
                        Integer organizerId = calculateOrganizerId(studyYear, major);

                        // Fetch events based on the organizer ID
                        fetchEvents(authHeader, organizerId);
                    } else {
                        Toast.makeText(EventActivity.this, "Failed to load user profile", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(EventActivity.this, "Error fetching profile: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("EventActivity", "Token or StudentId is empty");
        }
    }

    private void fetchEvents(String token, Integer organizerId) {
        EventService eventService = RetrofitClient.getInstance().create(EventService.class);

        // Log the request data
        Log.d("EventActivity", "Fetching events for organizerId: " + organizerId);

        eventService.getEvents(token, organizerId).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                // Log the response code and response body
                Log.d("EventActivity", "Event fetch response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body();
                    Log.d("EventActivity", "Fetched " + events.size() + " events");

                    Event.eventsList.clear(); // Clear the existing events if you want to refresh
                    Event.eventsList.addAll(events); // Add the fetched events to the static list

                    // Set up the adapter for the event list view
                    eventAdapter = new EventAdapter(EventActivity.this, events);
                    eventListView.setAdapter(eventAdapter);
                } else {
                    // Log when the response is not successful
                    Log.d("EventActivity", "Failed to fetch events, response body is null or response code: " + response.code());
                    Toast.makeText(EventActivity.this, "Failed to fetch events!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // Log the failure case
                Log.e("EventActivity", "Error fetching events: " + t.getMessage(), t);
                Toast.makeText(EventActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Calculate the OrganizerID dynamically based on StudyYear and Major
    private int calculateOrganizerId(String studyYear, String major) {
        if("ICT".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2": return 686;
                case "B3": return 2;
            }
        } else if("CS".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2": return 18;
                case "B3": return 689;
            }
        } else if("DS".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2": return 688;
                case "B3": return 690;
            }
        }
        return 999;
    }

}
