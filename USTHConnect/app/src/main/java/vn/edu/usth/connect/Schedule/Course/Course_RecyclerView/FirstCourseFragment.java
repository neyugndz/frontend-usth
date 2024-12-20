package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class FirstCourseFragment extends BaseCourseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first_course;
    }

    @Override
    protected List<CourseItem> getCourseData() {
        List<CourseItem> courses = new ArrayList<>();
        courses.add(new CourseItem("Introduction to Informatics", "Dr.", false));
        courses.add(new CourseItem("Fundamental Physics 1", "Dr.", false));
        // Add more courses...
        return courses;
    }

    @Override
    protected String getFavouriteKey() {
        return "favourite_courses_first";
    }
}
