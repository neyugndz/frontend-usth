package vn.edu.usth.connect.Resource.First_Year;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.usth.connect.R;

public class Semester_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_semester);

        setup_function();

    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

        LinearLayout semester_1 = findViewById(R.id.semester_1);
        semester_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Semester_Activity.this, vn.edu.usth.connect.Resource.First_Year.Course_Semester_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout semester_2 = findViewById(R.id.semester_2);
        semester_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Resource.First_Year.Semester_Activity.this, vn.edu.usth.connect.Resource.First_Year.Course_Semester_Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}