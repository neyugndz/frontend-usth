package vn.edu.usth.connect.Resource.CategoryRecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private List<CategoryItem> items;
//    private List<Course> courses = new ArrayList<>();;
    private CategoryAdapter adapter;

    private ImageView avatar_profile_image;
    private Handler handler = new Handler();

    // ResourceActivity != Resource
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_resource.xml
        setContentView(R.layout.activity_resource);

        // Setup Side-menu for Activity
        ImageButton mImageView = findViewById(R.id.menu_button);
        mDrawerLayout = findViewById(R.id.resource_page);

        // Function to open Side-menu
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Side-menu function
        navigator_drawer_function();

        // Setup function for RecyclerView and SearchView
        setup_recyclerview_function();

        // Load image in the Side-menu
        update_picture();
    }

    // SetUp RecyclerView and SearchView
    private void setup_recyclerview_function() {
        // RecyclerView point to First_Year.Semester_Activity
        // or
        // RecyclerView point to Second_Third_Year.Year_Activity
        RecyclerView recyclerView = findViewById(R.id.resource_recyclerview);

        items = new ArrayList<CategoryItem>();

        items.add(new CategoryItem("First Year - Sciences (three-year program)", 1));
        items.add(new CategoryItem("Information and Communication Technology", 2));
        items.add(new CategoryItem("Pharmacological Medical and Agronomical Biotechnology", 3));
        items.add(new CategoryItem("Advanced Materials Science and Nanotechnology", 4));
        items.add(new CategoryItem("Applied Engineering and Technology (AET)", 5));
        items.add(new CategoryItem("Applied Environmental Sciences", 6));
        items.add(new CategoryItem("Space and Applications", 7));
        items.add(new CategoryItem("Medical Science and Technology (MST)", 8));
        items.add(new CategoryItem("Food Science and Technology (FST)", 9));
        items.add(new CategoryItem("Aeronautical Maintenance and Engineering", 10));
        items.add(new CategoryItem("Chemistry", 11));
        items.add(new CategoryItem("Applied Mathematics", 12));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new Resource_course_Adapter(this, courses);
        adapter = new CategoryAdapter(this, items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = findViewById(R.id.resource_searchView);
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

    private void navigator_drawer_function(){
        LinearLayout to_home_activity = findViewById(R.id.to_home_page);
        to_home_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, vn.edu.usth.connect.MainActivity.class);
                startActivity(i);
            }
        });

        LinearLayout to_schedule_activity = findViewById(R.id.to_schedule_page);
        to_schedule_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, vn.edu.usth.connect.Schedule.Schedule_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_campus_activity = findViewById(R.id.to_map_page);
        to_campus_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, vn.edu.usth.connect.Campus.Campus_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_resource_activity = findViewById(R.id.to_resource_page);
        to_resource_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, CategoryActivity.class);
                startActivity(i);
            }
        });

        LinearLayout to_study_activity = findViewById(R.id.to_study_page);
        to_study_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.class);
                startActivity(i);
            }
        });
    }

    private void update_picture(){
        avatar_profile_image = findViewById(R.id.avatar_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("ProfileImage", MODE_PRIVATE);
        String url = sharedPreferences.getString("Image_URL", null);
        if (url != null) {
            new UpdateImage(url).start();
        }
    }

    class UpdateImage extends Thread {
        private String url;
        private Bitmap bitmap;

        public UpdateImage(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            handler.post(() -> {
                if (bitmap != null) {
                    avatar_profile_image.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(CategoryActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Filter for SearchView
//    private void filterList(String text) {
//        List<Course> filteredCourses = new ArrayList<>();
//
//        for (Course course : courses) {
//            if (course.getCourseName().toLowerCase().contains(text.toLowerCase())) {
//                filteredCourses.add(course);
//            }
//        }
//
//        if (filteredCourses.isEmpty()) {
//            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
//        } else {
//            adapter = new Resource_course_Adapter(this, filteredCourses);
//            recyclerView.setAdapter(adapter);
//        }
//    }
    private void filterList(String text) {
        List<CategoryItem> filteredItems = new ArrayList<>();

        for (CategoryItem item : items) {
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
}