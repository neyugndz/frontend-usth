package vn.edu.usth.connect.Schedule.Course;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.RecyclerView.CourseAdapter;
import vn.edu.usth.connect.Schedule.Course.RecyclerView.CourseItem;

public class First_Course_Activity extends AppCompatActivity {

    private List<CourseItem> items;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_first_course.xml
        setContentView(R.layout.activity_first_course);

        // Setup Recyclerview for Course and SearchView
        setup_recyclerview_function();

        // Set text for RecyclerView
        setup_text_recyclerview();

        // Function for backbutton
        setup_function();

    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setup_text_recyclerview(){
        TextView program_name = findViewById(R.id.program_name);

        String name = getIntent().getStringExtra("Program Name");

        program_name.setText(name);
    }

    // SetUp RecyclerView and SearchView
    // Folder: RecyclerView: CourseItem, CourseAdapter, CourseViewHolder
    private void setup_recyclerview_function(){
        // RecyclerView point to List_Class_in_Course_Activity
        RecyclerView recyclerView = findViewById(R.id.first_year_recyclerview);

        items = new ArrayList<CourseItem>();

        items.add(new CourseItem("Introduction to Informatics", "Dr."));
        items.add(new CourseItem("Fundamental Physics 1", "Dr."));
        items.add(new CourseItem("General Chemistry 1", "Dr."));
        items.add(new CourseItem("Cellular Biology", "Dr."));
        items.add(new CourseItem("Calculus 1", "Dr."));
        items.add(new CourseItem("Calculus 2", "Dr."));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseAdapter(this, items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = findViewById(R.id.first_year_course_searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    // Filter for SearchView
    private void filterList(String text) {
        List<CourseItem> filteredItems = new ArrayList<>();

        for (CourseItem item : items) {
            if (item.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilter(filteredItems);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}