package vn.edu.usth.connect.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import vn.edu.usth.connect.R;

public class ProfileFragment extends Fragment {

    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageButton mImageView = v.findViewById(R.id.menu_button);

        mDrawerLayout = v.findViewById(R.id.profile_page);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        navigator_drawer_function(v);

        setup_function(v);

        return v;
    }

    public void closeDrawer() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void setup_function(View v){
        Button edit_profile = v.findViewById(R.id.edit_profile_button);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Home.EditProfile.Edit_Profile_Activity.class);
                startActivity(i);
            }
        });
    }

    private void navigator_drawer_function(View v){
        LinearLayout to_home_activity = v.findViewById(R.id.to_home_page);
        to_home_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.MainActivity.class);
                startActivity(i);
            }
        });

        LinearLayout to_schedule_activity = v.findViewById(R.id.to_schedule_page);
        to_schedule_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Schedule.Schedule_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_campus_activity = v.findViewById(R.id.to_map_page);
        to_campus_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Campus.Campus_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_resource_activity = v.findViewById(R.id.to_resource_page);
        to_resource_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Resource.Resource_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_study_activity = v.findViewById(R.id.to_study_page);
        to_study_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.class);
                startActivity(i);
            }
        });
    }
}