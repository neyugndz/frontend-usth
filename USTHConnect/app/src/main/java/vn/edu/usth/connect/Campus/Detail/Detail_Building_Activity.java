package vn.edu.usth.connect.Campus.Detail;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.edu.usth.connect.Campus.Date_adapter;
import vn.edu.usth.connect.R;

public class Detail_Building_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_building);

        date_tablayout();
    }

    private void date_tablayout(){
        TabLayout tabLayout = findViewById(R.id.building_detail_tablayout);
        ViewPager2 viewPager2 = findViewById(R.id.building_detail_viewPager2);

        Date_adapter adapter = new Date_adapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Mon");
                        break;
                    case 1:
                        tab.setText("Tue");
                        break;
                    case 2:
                        tab.setText("Wes");
                        break;
                    case 3:
                        tab.setText("Thu");
                        break;
                    case 4:
                        tab.setText("Fri");
                        break;
                    case 5:
                        tab.setText("Sat");
                        break;
                    case 6:
                        tab.setText("Sun");
                        break;
                }
            }
        }).attach();
    }
}