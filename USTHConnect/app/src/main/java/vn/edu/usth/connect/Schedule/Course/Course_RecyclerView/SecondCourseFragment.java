package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class SecondCourseFragment extends BaseCourseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_course;
    }

    @Override
    protected List<CourseItem> getCourseData() {
        List<CourseItem> courses = new ArrayList<>();
        courses.add(new CourseItem("Advanced Programming with Python", "Dr. Tran Giang Son", false));
        courses.add(new CourseItem("Algebraic Structures", "Dr. Doan Nhat Quang", false));
        // Add more courses...
        return courses;
    }

    @Override
    protected String getFavouriteKey() {
        return "favourite_courses_second";
    }

}
