package vn.edu.usth.connect.Schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.FirstCourseFragment;
import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.SecondCourseFragment;
import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.ThirdCourseFragment;
import vn.edu.usth.connect.Schedule.ScheduleCourse_RecyclerView.ScheduleCourseFragment;
import vn.edu.usth.connect.Schedule.Favorite_RecyclerView.Favorite_Fragment;
import vn.edu.usth.connect.Schedule.TimeTable.Timetable_Fragment;

public class Fragment_schedule_changing extends FragmentStateAdapter {

    private String studyYear;

    public Fragment_schedule_changing(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String studyYear) {
        super(fragmentManager, lifecycle);
        this.studyYear = studyYear;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Timetable_Fragment();
            case 1:
                return new Favorite_Fragment();
            case 2:
                if("B1".equals(studyYear)) {
                    return new FirstCourseFragment();
                } else if("B2".equals(studyYear)) {
                    return new SecondCourseFragment();
                } else if("B3".equals(studyYear)) {
                    return new ThirdCourseFragment();
                } else {
                    return new ScheduleCourseFragment();
                }
            default:
                return new Timetable_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}