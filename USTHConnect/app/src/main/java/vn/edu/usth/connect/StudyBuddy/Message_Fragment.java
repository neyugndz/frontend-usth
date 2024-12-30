package vn.edu.usth.connect.StudyBuddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudyBuddyService;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Message_RecyclerView.BoxChatAdapter;
import vn.edu.usth.connect.StudyBuddy.Message_RecyclerView.BoxChatItem;

public class Message_Fragment extends Fragment {

    private String username; // Username of SIP Account
    private String password; // Password of SIP Account

    private  List<BoxChatItem> items;
    private BoxChatAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_chat.xml
        View v = inflater.inflate(R.layout.fragment_chat_, container, false);

        // Get username and password
        // Idea: Get Username and Password after RegisterStudyBuddyFragment to login
//        username = requireActivity().getIntent().getStringExtra("sip_username");
//        password = requireActivity().getIntent().getStringExtra("sip_password");

        // Retrieve the connected buddy's ID using SessionManager
        String buddyId = SessionManager.getInstance().getConnectedBuddyId(requireContext());
        String buddyName = SessionManager.getInstance().getConnectedBuddyName(requireContext());

        getSipCredentials(buddyId, buddyName);

        // Setup RecyclerView
        setup_recyclerview_function(v);

        return v;
    }

    private void getSipCredentials(String buddyId, String buddyName) {
        String token = SessionManager.getInstance().getToken();
        String authHeader = "Bearer " + token;

        StudyBuddyService service = RetrofitClient.getInstance().create(StudyBuddyService.class);
        service.getSipCredentials(authHeader).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extract username and password from the response
                    Map<String, String> credentials = response.body();

                    username = credentials.get("sip_username");
                    password = credentials.get("sip_password");

                    // After fetching SIP credentials, update the chat items
                    updateChatItems(buddyId, buddyName);
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Toast.makeText(requireContext(), "Failed to fetch SIP credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateChatItems(String buddyId, String buddyName) {
        // Now that we have the SIP credentials, create the BoxChatItem list
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(new BoxChatItem(buddyId, buddyName, username, password)); // Add the connected buddy as the first item

        // Add more test or real chat items if necessary
//        items.add(new BoxChatItem("ducduyvx", "Duc Duy", username, password)); // Test: ducduyvx - 1234567890
//        items.add(new BoxChatItem("vietanhngx", "Viet Anh", username, password)); // Test: vietanhngx - 1234567890
//        items.add(new BoxChatItem("quangminhdo", "Quang Minh", username, password)); // Test: quangminhdo - 1234567890

        // Notify the adapter to update the list
        if (adapter != null) {
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    }

    // SetUp RecyclerView and SearchView
    // Folder: Message_RecyclerView: BoxChatItem, BoxChatAdapter, BoxChatViewHolder
    private void setup_recyclerview_function (View v){
        // RecyclerView point to Message_RecyclerView.ChatActivity
        RecyclerView recyclerView = v.findViewById(R.id.chat_recyclerview);

//        items = new ArrayList<BoxChatItem>();
//
//        items.add(new BoxChatItem("ducduyvx", username, password)); // Test: ducduyvx - 1234567890
//        items.add(new BoxChatItem("vietanhngx", username, password)); // Test: vietanhngx - 1234567890
//        items.add(new BoxChatItem("quangminhdo", username, password)); // Test: quangminhdo - 1234567890
//        items.add(new BoxChatItem("hoanglanvx", username, password)); // Account don't exist
//        items.add(new BoxChatItem("hoanganhpham", username, password)); // Account don't exist
//        items.add(new BoxChatItem("dangnguyennguyen", username, password)); // Account don't exist

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BoxChatAdapter(requireContext(), items);
        recyclerView.setAdapter(adapter);

        // SerachView
        SearchView searchView = v.findViewById(R.id.chat_searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

    }

    // Filter for SearchView
    private void filterList(String text) {
        List<BoxChatItem> filteredItems = new ArrayList<>();

        for (BoxChatItem item : items) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilter(filteredItems);
        }

    }
}