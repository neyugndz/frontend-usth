package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class ThirdCourseFragment extends BaseCourseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_third_course;
    }

    @Override
    protected List<CourseItem> getCourseData() {
        List<CourseItem> courses = new ArrayList<>();
        courses.add(new CourseItem("Web Application Development", "Msc. Kieu Quoc Viet & Msc. Huynh Vinh Nam", false));
        courses.add(new CourseItem("Object-oriented system analysis and design", "Dr. Do Trung Dung", false));
        courses.add(new CourseItem("Mobile Application Development", "Dr. Tran Giang Son & Msc. Kieu Quoc Viet", false));

        // Add more courses...
        return courses;
    }

    @Override
    protected String getFavouriteKey() {
        return "favourite_courses_second";
    }
}
