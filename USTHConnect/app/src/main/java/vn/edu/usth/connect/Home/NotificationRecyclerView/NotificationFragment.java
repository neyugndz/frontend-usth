package vn.edu.usth.connect.Home.NotificationRecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.Notification;
import vn.edu.usth.connect.Models.Student.Student;
import vn.edu.usth.connect.Network.NotificationService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.StudentService;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Utils.NotificationUtils;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationList;
    // Notification List for comparison
    private List<Notification> previousNotificationList = new ArrayList<>();

    // Handler to manage periodic fetching
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable fetchNotificationsRunnable;

    private static final int FETCH_INTERVAL = 60000; // 10 mins

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_notification.xml
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        // Set up recycler view
        recyclerView = v.findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set up adapter
        adapter = new NotificationAdapter();
        recyclerView.setAdapter(adapter);

        // Fetch Notifications
        startFetchingNotifications();

        return v;
    }

    private void startFetchingNotifications() {
        fetchNotificationsRunnable = new Runnable() {
            @Override
            public void run() {
                fetchNotifications();
                // Schedule the next fetch after the specified interval
                handler.postDelayed(this, FETCH_INTERVAL);
            }
        };

        // Start the periodic task
        handler.post(fetchNotificationsRunnable);
    }

    private void stopFetchingNotifications() {
        if (fetchNotificationsRunnable != null) {
            handler.removeCallbacks(fetchNotificationsRunnable);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Stop fetching notifications when the fragment is destroyed
        stopFetchingNotifications();
    }

    public void fetchNotificationsFromActivity() {
        fetchNotifications();
    }

    private void fetchNotifications() {
        // Fetch token and studentId from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ToLogin", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", "");
        String studentId = sharedPreferences.getString("StudentId", "");

        if (!token.isEmpty() && !studentId.isEmpty()) {
            String authHeader = "Bearer " + token;

            // Fetch Student Profile
            StudentService studentService = RetrofitClient.getInstance().create(StudentService.class);
            studentService.getStudentProfile(authHeader, studentId).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Student currentStudent = response.body();
                        Log.d("NotificationFragment", "Fetched student profile: " + currentStudent);

                        String studyYear = currentStudent.getStudyYear();
                        String major = currentStudent.getMajor();
                        Integer organizerId = calculateOrganizerId(studyYear, major);

                        // Fetch notifications using organizerId
                        fetchNotificationsForOrganizer(authHeader, organizerId);
                    } else {
                        Log.e("NotificationFragment", "Failed to fetch student profile");
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Log.e("NotificationFragment", "Error fetching student profile: " + t.getMessage());
                }
            });
        } else {
            Log.d("NotificationFragment", "Token or StudentId is empty");
        }
    }

    private void fetchNotificationsForOrganizer(String authHeader, Integer organizerId) {
        NotificationService notificationService = RetrofitClient.getInstance().create(NotificationService.class);
        notificationService.getNotificationsForOrganizerId(authHeader, organizerId).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    notificationList = response.body();

                    // Sort notifications by descending createdAt
                    notificationList.sort((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()));

                    Log.d("NotificationFragment", "Fetched " + notificationList.size() + " notifications");
                    adapter.updateNotifications(notificationList);
                    // Check if the notifications are different from the previous ones
                    if (notificationsHaveChanged(notificationList, previousNotificationList)) {
                        // Update the adapter with new notifications
                        adapter.updateNotifications(notificationList);

                        // Trigger notification for the user if there are new notifications
                        if (getActivity() != null) {
                            NotificationUtils.showNotification(getActivity(), "New Notification", "There are some event changes.");
                        }

                        // Update the previous notification list to the current list
                        previousNotificationList = new ArrayList<>(notificationList);
                    } else {
                        Log.d("NotificationFragment", "No new notifications available.");
                    }
                } else {
                    Log.e("NotificationFragment", "Failed to fetch notifications, response code: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.e("NotificationFragment", "Error fetching notifications: " + t.getMessage());
            }
        });
    }

    // Method to compare new notifications with previous ones
    private boolean notificationsHaveChanged(List<Notification> newNotifications, List<Notification> oldNotifications) {
        if (newNotifications.size() != oldNotifications.size()) {
            return true; // If the size of the lists is different, they are considered changed
        }

        // Compare each notification's createdAt or any other unique field
        for (int i = 0; i < newNotifications.size(); i++) {
            Notification newNotification = newNotifications.get(i);
            Notification oldNotification = oldNotifications.get(i);

            // Check if the createdAt or another field differs
            if (!newNotification.getCreatedAt().equals(oldNotification.getCreatedAt())) {
                return true; // Notifications have changed if createdAt differs
            }
        }

        return false; // No changes detected
    }
    // Calculate OrganizerID dynamically based on StudyYear and Major
    private int calculateOrganizerId(String studyYear, String major) {
        if ("ICT".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2":
                    return 691;
                case "B3":
                    return 2;
            }
        } else if ("CS".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2":
                    return 18;
                case "B3":
                    return 689;
            }
        } else if ("DS".equalsIgnoreCase(major)) {
            switch (studyYear) {
                case "B2":
                    return 688;
                case "B3":
                    return 690;
            }
        }
        return 999;
    }
}
