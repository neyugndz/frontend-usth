package vn.edu.usth.connect.Campus.Detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.edu.usth.connect.Campus.Date_adapter;
import vn.edu.usth.connect.R;

public class Detail_Building_Activity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_detail_building.xml
        setContentView(R.layout.activity_detail_building);

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

    // Set text building's name in Header and building's location
    private void setup_text_recyclerview(){
        TextView building_name = findViewById(R.id.detail_building_name);
        TextView building_locate = findViewById(R.id.detail_building_locate);

        String name = getIntent().getStringExtra("Building Name");
        String locate = getIntent().getStringExtra("Building Locate");

        building_name.setText(name);
        building_locate.setText(locate);
    }

//     BackPress: backto BuildingFragment
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}