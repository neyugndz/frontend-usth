package vn.edu.usth.connect.StudyBuddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudyBuddyService;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Schedule_Activity;
import vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Rcm_UserAdapter;
import vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Rcm_UserItem;

public class Study_Buddy_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_study_buddy_.xml
        View v = inflater.inflate(R.layout.fragment_study__buddy_, container, false);

        // Setup Horizontal Rcm_User
        setup_Rcm_User(v);

        return v;
    }

    // Setup RecyclerView
    // Folder: SB_Recycler: Rcm_UserAdapter, Rcm_UserItem, Rcm_UserViewHolder
    private void setup_Rcm_User(View v) {
        // RecyclerView point to Detail.UserDetailActivity
        RecyclerView recyclerView = v.findViewById(R.id.horizontal_recyclerview);

        List<Rcm_UserItem> items = new ArrayList<Rcm_UserItem>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Get token and Student ID
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();
        String authHeader = "Bearer " + token;

        StudyBuddyService service = RetrofitClient.getInstance().create(StudyBuddyService.class);
        service.getRecommendation(authHeader, studentId).enqueue(new Callback<List<StudyBuddy>>() {
            @Override
            public void onResponse(Call<List<StudyBuddy>> call, Response<List<StudyBuddy>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<StudyBuddy> studyBuddies = response.body();

                    // Convert StudyBuddy objects to Rcm_UserItem
                    List<Rcm_UserItem> items = new ArrayList<>();
                    for (StudyBuddy buddy : studyBuddies) {
                        items.add(new Rcm_UserItem(
                                buddy.getStudentId(),
                                buddy.getName(),
                                buddy.getGender(),
                                buddy.getMajor(),
                                buddy.getLookingFor(),
                                R.drawable.usthconnect // Replace with actual image URL or placeholder
                        ));
                    }

                    // Set up the adapter with data
                    Rcm_UserAdapter adapter = new Rcm_UserAdapter(requireContext(), items, new Rcm_UserAdapter.OnConnectListener() {
                        @Override
                        public void onConnect(Rcm_UserItem item) {
                            // Add logic for confirmation
                            confirmConnection(item);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    // Handle the case when the response is not successful or the body is null
                    Log.e("StudyBuddyFragment", "Failed to load recommended list or response body is null");
                    Toast.makeText(getContext(), "Error loading recommended list", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<StudyBuddy>> call, Throwable t) {
                Log.e("StudyBuddyFragment", "Error fetching recommended list", t);
                Toast.makeText(getContext(), "Error loading recommended list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmConnection(Rcm_UserItem item) {
        // Store the selected StudyBuddy in a shared preference or local storage
        SessionManager.getInstance().storeConnectedBuddy(item,requireContext());

        Bundle args = new Bundle();
        args.putString("buddy_id", item.getStudentId());
        args.putString("buddy_name", item.getName());
        
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        Fragment messageFragment = fragmentManager.findFragmentByTag("MessageFragment");
        Fragment audioFragment = fragmentManager.findFragmentByTag("AudioFragment");

        if (messageFragment != null) {
            messageFragment.setArguments(args);  // Set the arguments (StudyBuddy data)
            fragmentManager.beginTransaction()
                    .replace(R.id.study_buddy_layout, messageFragment)
                    .commit();
        }

        if (audioFragment != null) {
            audioFragment.setArguments(args);  // Set the arguments (StudyBuddy data)
            fragmentManager.beginTransaction()
                    .replace(R.id.study_buddy_layout, audioFragment)
                    .commit();
        }    }
}