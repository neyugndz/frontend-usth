package vn.edu.usth.connect.StudyBuddy.Audio;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.linphone.core.Core;

import vn.edu.usth.connect.R;

public class OutgoingCall extends AppCompatActivity {

    private Core core;

    private String username; // Username of Sip Account
    private String password; // Password of Sip Account

    private String box_chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_outgoing_call.xml
        setContentView(R.layout.activity_outgoing_call);


    }


}