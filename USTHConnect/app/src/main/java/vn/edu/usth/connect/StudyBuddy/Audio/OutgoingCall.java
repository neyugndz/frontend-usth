package vn.edu.usth.connect.StudyBuddy.Audio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.linphone.core.Account;
import org.linphone.core.AccountParams;
import org.linphone.core.Address;
import org.linphone.core.AuthInfo;
import org.linphone.core.Call;
import org.linphone.core.CallParams;
import org.linphone.core.Core;
import org.linphone.core.CoreListenerStub;
import org.linphone.core.Factory;
import org.linphone.core.MediaEncryption;
import org.linphone.core.RegistrationState;
import org.linphone.core.TransportType;

import vn.edu.usth.connect.R;

public class OutgoingCall extends AppCompatActivity {

    private Core core;

    private String username; // Username of Sip Account
    private String password; // Password of Sip Account

    private String box_chat;

    // Outgoing Call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_outgoing_call.xml
        setContentView(R.layout.activity_outgoing_call);

        // Get username, password, boxChat name
        Intent intent = getIntent();
        username = intent.getStringExtra("sip_username");
        password = intent.getStringExtra("sip_password");
        box_chat = intent.getStringExtra("BoxChat_Name");

        // Create Function & Core:
        // Outgoing call
        Factory factory = Factory.instance();
        core = factory.createCore(null, null, this);

        // Login Outgoing
        login(username, password);

    }

    // Outgoing CoreListener
    private final CoreListenerStub outgoingCallCoreListener = new CoreListenerStub() {
        @Override
        public void onAccountRegistrationStateChanged(Core core, Account account, RegistrationState state, String message) {}

        @Override
        public void onCallStateChanged(Core core, Call call, Call.State state, String message) {
            if (state == Call.State.OutgoingInit) {
            } else if (state == Call.State.StreamsRunning) {
                findViewById(R.id.outgoing_hang_up).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_hang_up).setEnabled(true);

                findViewById(R.id.outgoing_pause).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_pause).setEnabled(true);
                ((Button) findViewById(R.id.outgoing_pause)).setText("Pause");

                findViewById(R.id.outgoing_toggle_video).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_toggle_video).setEnabled(true);

                findViewById(R.id.outgoing_toggle_camera).setVisibility(View.VISIBLE);
                findViewById(R.id.outgoing_toggle_camera).setEnabled(core.getVideoDevicesList().length > 2 && call.getCurrentParams().videoEnabled());
            } else if (state == Call.State.Paused) {
                ((Button) findViewById(R.id.outgoing_pause)).setText("Resume");
                findViewById(R.id.outgoing_toggle_video).setEnabled(false);
            } else if (state == Call.State.Released) {
                ((TextView) findViewById(R.id.outgoing_remote_address)).setText("");

                findViewById(R.id.outgoing_pause).setEnabled(false);
                ((Button) findViewById(R.id.outgoing_pause)).setText("Pause");

                findViewById(R.id.outgoing_toggle_video).setEnabled(false);

                findViewById(R.id.outgoing_hang_up).setEnabled(false);

                findViewById(R.id.outgoing_toggle_camera).setEnabled(false);

                findViewById(R.id.outgoing_call_layout).setVisibility(View.GONE);
            }
        }
    };

    // OutgoingCall
    private void outgoingCall() {
        String remoteSipUri = String.format("sip:%s@sip.linphone.org", box_chat);
        Address remoteAddress = Factory.instance().createAddress(remoteSipUri);
        if (remoteAddress == null) return;

        CallParams params = core.createCallParams(null);
        if (params == null) return;

        params.setMediaEncryption(MediaEncryption.None);
        core.inviteAddressWithParams(remoteAddress, params);
    }

    // Outgoing Hangup
    private void hangUp() {
        if (core.getCallsNb() == 0) return;

        // Get the current call or fallback to the first call in the list
        Call call = core.getCurrentCall() != null ? core.getCurrentCall() : core.getCalls()[0];
        if (call == null) return;

        // Terminate the call
        call.terminate();
    }

    // Outgoing ToggleVideo
    private void toggleVideo() {
        if (core.getCallsNb() == 0) return;

        Call call = core.getCurrentCall() != null ? core.getCurrentCall() : core.getCalls()[0];
        if (call == null) return;

        // Check for CAMERA permission
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
            return;
        }

        // Create new call parameters from the call object
        CallParams params = core.createCallParams(call);
        if (params != null) {
            // Toggle video state
            params.enableVideo(!call.getCurrentParams().videoEnabled());
            // Request the call update
            call.update(params);
        }
    }

    // Outgoing toggleCamera
    private void toggleCamera() {
        String currentDevice = core.getVideoDevice();

        for (String camera : core.getVideoDevicesList()) {
            if (!camera.equals(currentDevice) && !camera.equals("StaticImage: Static picture")) {
                core.setVideoDevice(camera);
                break;
            }
        }
    }

    // Outgoing Pause/Resume => Wait
    private void pauseOrResume() {
        if (core.getCallsNb() == 0) return;

        Call call = core.getCurrentCall() != null ? core.getCurrentCall() : core.getCalls()[0];
        if (call == null) return;

        // Pause or resume the call based on its state
        if (call.getState() != Call.State.Paused && call.getState() != Call.State.Pausing) {
            call.pause();
        } else if (call.getState() != Call.State.Resuming) {
            call.resume();
        }
    }

    private void login(String username, String password) {
        String domain = "sip.linphone.org";
        AuthInfo authInfo = Factory.instance().createAuthInfo(username, null, password, null, null, domain, null);

        Address identity = Factory.instance().createAddress("sip:" + username + "@" + domain);

        Address address = Factory.instance().createAddress("sip:" + domain);


        if (address != null) {
            address.setTransport(TransportType.Tls);
        }

        // OutgoingCall
        AccountParams outgoing_param = core.createAccountParams();

        outgoing_param.setIdentityAddress(identity);

        outgoing_param.setServerAddress(address);

        outgoing_param.setRegisterEnabled(true);

        Account outgoing_account = core.createAccount(outgoing_param);
        core.addAuthInfo(authInfo);
        core.addAccount(outgoing_account);
        // Outgoing Video
        core.getConfig().setBool("video", "auto_resize_preview_to_keep_ratio", true);
        core.setDefaultAccount(outgoing_account);
        core.addListener(outgoingCallCoreListener);
        core.start();

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }
    }

}