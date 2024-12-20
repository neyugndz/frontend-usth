package vn.edu.usth.connect.Schedule.Course.Event_RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class List_Class_in_Course_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_list_class_in_course.xml
        setContentView(R.layout.activity_list_class_in_course);

        // Button Function
        setup_function();

        // Set text for RecyclerView
        setup_text_recycler();

    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setup_text_recycler(){
        TextView course_name = findViewById(R.id.course_name_timetable);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_events);
        String name = getIntent().getStringExtra("Course Name");

        course_name.setText(name);

        progressBar.setVisibility(View.VISIBLE);

        // Filter events by course name
        new Thread(() -> {
            ArrayList<Event> filteredEvents = Event.eventsForCourse(name);

            // Sort events by their start time, with upcoming events first
            filteredEvents.sort((event1, event2) -> {
                // Compare by event start date: upcoming events first
                if (event1.getEventStartDateTime().isBefore(LocalDateTime.now()) &&
                        event2.getEventStartDateTime().isAfter(LocalDateTime.now())) {
                    return 1; // Passed event goes after the upcoming event
                }
                if (event1.getEventStartDateTime().isAfter(LocalDateTime.now()) &&
                        event2.getEventStartDateTime().isBefore(LocalDateTime.now())) {
                    return -1; // Upcoming event goes before the passed event
                }
                // If both events are either passed or upcoming, sort by time
                return event1.getEventStartDateTime().compareTo(event2.getEventStartDateTime());
            });

            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new EventForCourseAdapter(filteredEvents));

            });
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}