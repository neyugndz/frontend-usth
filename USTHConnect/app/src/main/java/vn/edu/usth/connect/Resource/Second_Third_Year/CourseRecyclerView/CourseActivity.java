package vn.edu.usth.connect.Resource.Second_Third_Year.CourseRecyclerView;

import android.content.Intent;
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
import vn.edu.usth.connect.Resource.Second_Third_Year.ActivityRecyclerView.Year_Course_Resource_Activity;

public class CourseActivity extends AppCompatActivity {

    private List<Sty_Item> items;
    private Sty_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_course_year.xml
        setContentView(R.layout.activity_course_year);

        // Setup Recyclerview for Course and Searchview
        setupRecyclerView();

        // Set year name to header
        setupRecyclerView();

        // Button Function
//        setup_function();
        // Handle button clicks
        setupClickHandlers();
    }

    private void setupRecyclerView() {
        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.year_course_recyclerview);

        // Get year and subcategory from Intent
        String year = getIntent().getStringExtra("Year");
        int subcategoryId = getIntent().getIntExtra("subCategoryId", -1);

        // Assign courses based on year and subcategory
        items = getCoursesBasedOnYearAndSubcategory(year, subcategoryId);

        adapter = new Sty_Adapter(this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = findViewById(R.id.year_course_searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCourses(newText);
                return true;
            }
        });

        adapter.setOnItemClickListener(position -> {
            Sty_Item selectedItem = items.get(position);
            navigateToYearCourseResourceActivity(selectedItem.getHeading(), selectedItem.getSubhead());
        });
    }

    private void setupYearHeader() {
        TextView yearCourse = findViewById(R.id.year_course);
        String yearName = getIntent().getStringExtra("Year");
        yearCourse.setText(yearName);
    }

    private void setupClickHandlers() {
        // Back Button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void navigateToYearCourseResourceActivity(String courseName, String courseProf) {
        Intent intent = new Intent(this, Year_Course_Resource_Activity.class);
        intent.putExtra("Course Name", courseName);
        intent.putExtra("Course Instructor", courseProf);
        startActivity(intent);
    }

    private void filterCourses(String text) {
        List<Sty_Item> filteredItems = new ArrayList<>();
        for (Sty_Item item : items) {
            if (item.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilter(filteredItems);
        }
    }

    private List<Sty_Item> getCoursesBasedOnYearAndSubcategory(String year, int subCategoryId) {
        List<Sty_Item> courseList = new ArrayList<>();

        // Example static courses based on year and subcategory
        if ("Second Year".equals(year)) {
            switch (subCategoryId) {
                case 1:
                    courseList.add(new Sty_Item("Advanced Programming with Python", "Dr. Tran Giang Son"));
                    break;
                case 2:
                    courseList.add(new Sty_Item("Introduction to Cryptography", "Dr. Nguyen Minh Huong"));
                    break;
                case 3:
                    courseList.add(new Sty_Item("Linear Algebra", "Prof. Johnson"));
                    break;
                case 4:
                    courseList.add(new Sty_Item("Calculus", "Prof. Smith"));
                    break;
                default:
                    courseList.add(new Sty_Item("Not Found", "Not Available"));
                    break;
            }
        }
        if ("Third Year".equals(year)) {
            switch (subCategoryId) {
                case 1:
                    courseList.add(new Sty_Item("Mobile Application Development", "Dr. Tran Giang Son, Kieu Quoc Viet "));
                    courseList.add(new Sty_Item("Web Application Development", "Huynh Vinh Nam"));
                    break;
                case 2:
                    courseList.add(new Sty_Item("Introduction to Cryptography", "Dr. Nguyen Minh Huong"));
                    break;
                case 3:
                    courseList.add(new Sty_Item("Machine Learning in Medicine", "Dr. Tran Giang Son"));
                    break;
                case 4:
                    courseList.add(new Sty_Item("Calculus", "Prof. Smith"));
                    break;
                default:
                    courseList.add(new Sty_Item("Not Found", "Not Available"));
                    break;
            }
        }

        return courseList;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}