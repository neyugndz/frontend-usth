package vn.edu.usth.connect.Resource.Second_Third_Year.ActivityRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.Moodle.Activity;
import vn.edu.usth.connect.Network.ResourceService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.Resource;

public class Year_Course_Resource_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_year_course_resource.xml
        setContentView(R.layout.activity_year_course_resource);

        // Set text for RecyclerView
        setup_text_recyclerview();

        // Button Function
        setup_function();

        fetchActivities();
    }

    private void fetchActivities() {
        // Fetch token and studentId from SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ToLogin", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", "");
        String studentId = sharedPreferences.getString("StudentId", "");

        if (!token.isEmpty() && !studentId.isEmpty()) {
            String authHeader = "Bearer " + token;

            // Get courseId from the Intent that was passed
            String courseId = getIntent().getStringExtra("Course Id");

            // Make sure the courseId is valid before making the API call
            if (courseId != null && !courseId.isEmpty()) {
                // Create an instance of Retrofit and fetch activities for the specific course
                ResourceService resourceService = RetrofitClient.getInstance().create(ResourceService.class);
                Call<List<Activity>> call = resourceService.getActivities(authHeader, courseId);

                call.enqueue(new Callback<List<Activity>>() {
                    @Override
                    public void onResponse(Call<List<Activity>> call, Response<List<Activity>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Activity> activities = response.body();
                            updateRecyclerView(activities);
                        } else {
                            Log.e("FetchResources", "Response failed");
                            Log.e("FetchResources", "Status Code: " + response.code());
                            if (response.errorBody() != null) {
                                try {
                                    Log.e("FetchResources", "Error Body: " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(Year_Course_Resource_Activity.this, "Failed to fetch activities", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Activity>> call, Throwable t) {
                        Log.e("FetchResources", "Error fetching activities: " + t.getMessage());
                        Toast.makeText(Year_Course_Resource_Activity.this, "Error fetching activities", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Log.d("Resource_Activity", "CourseId is invalid or empty");
                Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("Resource_Activity", "Token or StudentId is empty");
        }
    }

    private void updateRecyclerView(List<Activity> activities) {
        if (activities != null) {
            activityAdapter.setActivities(activities);
        }
    }


    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
        // Setting up clickable resources for lectures, labwork, etc.
//        LinearLayout lecture = findViewById(R.id.lecture);
//        lecture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Year_Course_Resource_Activity.this, Resource.class);
//                i.putExtra("Task", "Lectures");
//                startActivity(i);
//            }
//        });
//
//        LinearLayout labwork = findViewById(R.id.labwork);
//        labwork.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Year_Course_Resource_Activity.this, Resource.class);
//                i.putExtra("Task", "Labwork");
//                startActivity(i);
//            }
//        });
//
//        LinearLayout sampleCode = findViewById(R.id.sampleCode);
//        sampleCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Year_Course_Resource_Activity.this, Resource.class);
//                i.putExtra("Task", "Sample Code");
//                startActivity(i);
//            }
//        });
//
//        LinearLayout document = findViewById(R.id.document);
//        document.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Year_Course_Resource_Activity.this, Resource.class);
//                i.putExtra("Task", "Document");
//                startActivity(i);
//            }
//        });
//
//        LinearLayout midtermExam = findViewById(R.id.midterm_exam);
//        midtermExam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Year_Course_Resource_Activity.this, Resource.class);
//                i.putExtra("Task", "Midterm Exam");
//                startActivity(i);
//            }
//        });
//
//        LinearLayout finalExam = findViewById(R.id.final_exam);
//        finalExam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Year_Course_Resource_Activity.this, Resource.class);
//                i.putExtra("Task", "Final Exam");
//                startActivity(i);
//            }
//        });
//
//        LinearLayout retakeExam = findViewById(R.id.retake_exam);
//        retakeExam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Year_Course_Resource_Activity.this, Resource.class);
//                i.putExtra("Task", "Retake Exam");
//                startActivity(i);
//            }
//        });
    }

    private void setupResourceClickListener(int resourceId, String taskType) {
        LinearLayout resourceButton = findViewById(resourceId);
        resourceButton.setOnClickListener(view -> {
            Intent intent = new Intent(Year_Course_Resource_Activity.this, Resource.class);
            intent.putExtra("Task", taskType);
            startActivity(intent);
        });
    }

    private void setup_text_recyclerview(){
        TextView course_name = findViewById(R.id.course_name);

        String name = getIntent().getStringExtra("Course Name");

        course_name.setText(name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}