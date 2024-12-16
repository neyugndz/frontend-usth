package vn.edu.usth.connect.StudyBuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import vn.edu.usth.connect.MainActivity;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Fragment_schedule_changing;
import vn.edu.usth.connect.StudyBuddy.Welcome.WelcomeFragment;

public class Study_Buddy_Activity extends AppCompatActivity {

    private ViewPager2 mviewPager;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout mDrawerLayout;

    private ImageView avatar_profile_image;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_study_buddy.xml
        setContentView(R.layout.activity_study_buddy);

        // ViewPager2: Change fragments: MessageFragment, StudyBuddyFragment, StudyBuddyProfileFragment
        mviewPager = findViewById(R.id.view_study_buddy_pager);

        // BottomNavigation: Bottom Menu :D
        bottomNavigationView = findViewById(R.id.study_buddy_bottom_navigation);

        // Adapter: Fragment_Study_Buddy_changing
        Fragment_Study_Buddy_changeing adapter = new Fragment_Study_Buddy_changeing(getSupportFragmentManager(), getLifecycle());
        mviewPager.setAdapter(adapter);
        mviewPager.setUserInputEnabled(false);

        // ViewPager2 setup Function
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
                        bottomNavigationView.getMenu().findItem(R.id.audio_page).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.sb_profile_page).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        // BottomNavigation setup Function
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
                if (item.getItemId() == R.id.audio_page) {
                    mviewPager.setCurrentItem(2, true);
                    return true;
                }
                if (item.getItemId() == R.id.sb_profile_page) {
                    mviewPager.setCurrentItem(3, true);
                    return true;
                }
                return false;
            }
        });

        // Setup Side-menu for Activity
        mDrawerLayout= findViewById(R.id.sb_activity);

        // Function to open Side-menu
        ImageButton mImageView = findViewById(R.id.menu_button);
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

        // Load image in the Side-menu
        update_picture();

        // Register
        // todo: SharePreference
        navigatorToRegister();
    }

    private void navigator_drawer_function(){
        LinearLayout to_home_activity = findViewById(R.id.to_home_page);
        to_home_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.this, vn.edu.usth.connect.MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout to_schedule_activity = findViewById(R.id.to_schedule_page);
        to_schedule_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.this, vn.edu.usth.connect.Schedule.Schedule_Activity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout to_campus_activity = findViewById(R.id.to_map_page);
        to_campus_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.this, vn.edu.usth.connect.Campus.Campus_Activity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout to_resource_activity = findViewById(R.id.to_resource_page);
        to_resource_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.this, vn.edu.usth.connect.Resource.Resource_Activity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout to_study_activity = findViewById(R.id.to_study_page);
        to_study_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.this, vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout logout = findViewById(R.id.to_log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment loginFragment = new vn.edu.usth.connect.Login.LoginFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(android.R.id.content, loginFragment);
                transaction.commit();
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
                    Toast.makeText(Study_Buddy_Activity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void navigatorToRegister(){
        Fragment welcomeFragment = new WelcomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, welcomeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}