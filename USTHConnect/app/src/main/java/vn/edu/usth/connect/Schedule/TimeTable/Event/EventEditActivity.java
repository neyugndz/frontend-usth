//package vn.edu.usth.connect.Schedule.TimeTable.Event;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//import vn.edu.usth.connect.R;
//import vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils;
//
//public class EventEditActivity extends AppCompatActivity {
//
//    private LocalDateTime eventStart;
//    private LocalDateTime eventEnd;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_event_edit);
//
//        // Use current date and time for the event
//        eventStart = LocalDateTime.now();
//        eventEnd = eventStart.plusHours(1); // Example: default duration of 1 hour
//
//        // Event: name event: "testing", time: tại thời điểm bấm, date = ngày click
//        Event newEvent = new Event(
//                null, // eventId (can be generated in the backend or later)
//                "Testing Event", // eventName
//                "This is a test event", // eventDescription
//                eventStart, // eventStart
//                eventEnd, // eventEnd
//                "Default Location", // location
//                1 // organizerId (can be dynamic based on the logged-in user)
//        );
//
//        // Add into ListView in Event
//        Event.eventsList.add(newEvent);
//
//        // End Task
//        finish();
//    }
//}