package vn.edu.usth.connect.Workers;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.Student.Student;
import vn.edu.usth.connect.Network.EventService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.StudentService;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class FetchEventsWorker extends Worker {

    public FetchEventsWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("FetchEventsWorker", "Starting to fetch events...");

        // Fetch token and studentId from SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ToLogin", Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("Token", "");
        String studentId = sharedPreferences.getString("StudentId", "");

        if (token.isEmpty() || studentId.isEmpty()) {
            Log.e("FetchEventsWorker", "Token or Student ID is missing.");
            return Result.failure();
        }

        String authHeader = "Bearer " + token;

        try {
            // Create Retrofit instance and call the service
            StudentService studentService = RetrofitClient.getInstance().create(StudentService.class);
            Response<Student> response = studentService.getStudentProfile(authHeader, studentId).execute();

            if (response.isSuccessful() && response.body() != null) {
                Student currentStudent = response.body();
                Log.d("FetchEventsWorker", "Student data: " + currentStudent);
                String studyYear = currentStudent.getStudyYear();
                String major = currentStudent.getMajor();
                Integer organizerId = calculateOrganizerId(studyYear, major);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("StudyYear", studyYear);
                editor.putString("Major", major);
                editor.apply();
                // Fetch events for the student

                fetchEvents(authHeader, organizerId);
                return Result.success();
            } else {
                Log.e("FetchEventsWorker", "Failed to fetch student data: " + response.message());
                return Result.retry(); // Retry in case of failure
            }
        } catch (Exception e) {
            Log.e("FetchEventsWorker", "Error fetching student data", e);
            return Result.failure();
        }
    }

    private void fetchEvents(String token, Integer organizerId) {
        EventService eventService = RetrofitClient.getInstance().create(EventService.class);
        eventService.getEvents(token, organizerId).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body();
                    Log.d("FetchEventsWorker", "Fetched " + events.size() + " events");

                    Event.eventsList.clear();
                    Event.eventsList.addAll(events);

                    // You can store the fetched events in local storage or notify the app (via a notification or broadcast)
                } else {
                    Log.e("FetchEventsWorker", "Failed to fetch events, response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("FetchEventsWorker", "Error fetching events: " + t.getMessage());
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
