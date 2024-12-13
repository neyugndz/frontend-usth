package vn.edu.usth.connect.Resource;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.connect.R;

public class Resource extends AppCompatActivity {

    // ResourceActivity: Fragment show resource: lecture, slide, labwork,...
    // Activity phát sinh
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_resource2.xml
        setContentView(R.layout.activity_resource2);

        // Set text for RecyclerView in Header
        setup_text_recyclerview();

        // Back Button
        // Todo: RecyclerView (?)
        setup_function();
    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    private void setup_text_recyclerview(){
        TextView course_name = findViewById(R.id.task_name);

        String name = getIntent().getStringExtra("Task");

        course_name.setText(name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}