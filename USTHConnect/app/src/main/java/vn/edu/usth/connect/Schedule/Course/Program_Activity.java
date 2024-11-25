package vn.edu.usth.connect.Schedule.Course;

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

public class Program_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_program);

        setup_function();
   }

   private void setup_function(){
       ImageButton back_button = findViewById(R.id.back_button);
       back_button.setOnClickListener(view -> {
           onBackPressed();
       });

       LinearLayout second_year = findViewById(R.id.second_year_logic);
       second_year.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(vn.edu.usth.connect.Schedule.Course.Program_Activity.this, vn.edu.usth.connect.Schedule.Course.Course_Activity.class);
               startActivity(i);
           }
       });
   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}