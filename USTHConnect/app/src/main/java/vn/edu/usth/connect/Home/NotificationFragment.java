package vn.edu.usth.connect.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.connect.R;

public class NotificationFragment extends Fragment {

    // Function todo: Show notification and Have PushNoti
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_notification.xml
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        return v;
    }

}