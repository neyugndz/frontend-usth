package vn.edu.usth.connect.StudyBuddy;

import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Fragment_schedule_changing;

public class Study_Buddy_Activity extends AppCompatActivity {

    private ViewPager2 mviewPager;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_study_buddy);

        mviewPager = findViewById(R.id.view_study_buddy_pager);
        bottomNavigationView = findViewById(R.id.study_buddy_bottom_navigation);

        Fragment_Study_Buddy_changeing adapter = new Fragment_Study_Buddy_changeing(getSupportFragmentManager(), getLifecycle());
        mviewPager.setAdapter(adapter);
        mviewPager.setUserInputEnabled(false);

        mviewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.sb_page).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.chat_page).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.sb_profile_page).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.sb_page) {
                    mviewPager.setCurrentItem(0, true);
                    return true;
                }
                if (item.getItemId() == R.id.chat_page) {
                    mviewPager.setCurrentItem(1, true);
                    return true;
                }
                if (item.getItemId() == R.id.sb_profile_page) {
                    mviewPager.setCurrentItem(2, true);
                    return true;
                }
                return false;
            }
        });


    }
}