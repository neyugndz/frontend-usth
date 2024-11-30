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

        setup_function(v);

        return v;
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


}