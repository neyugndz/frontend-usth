package vn.edu.usth.connect.StudyBuddy;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Message_Recycler.BoxChatAdapter;
import vn.edu.usth.connect.StudyBuddy.Message_Recycler.BoxChatItem;

public class Message_Fragment extends Fragment {

    private String username;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_chat.xml
        View v = inflater.inflate(R.layout.fragment_chat_, container, false);

        // Get username and password
        username = requireActivity().getIntent().getStringExtra("sip_username");
        password = requireActivity().getIntent().getStringExtra("sip_password");

        // Setup RecyclerView
        setup_recyclerview_function(v);

        return v;
    }

    private void setup_recyclerview_function (View v){
        RecyclerView recyclerView = v.findViewById(R.id.chat_recyclerview);

        List<BoxChatItem> items = new ArrayList<>();

        items.add(new BoxChatItem("ducduyvx", username, password));
        items.add(new BoxChatItem("vietanhngx", username, password));
        items.add(new BoxChatItem("quangminhdo", username, password));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        BoxChatAdapter adapter = new BoxChatAdapter(requireContext(), items);
        recyclerView.setAdapter(adapter);
    }
}