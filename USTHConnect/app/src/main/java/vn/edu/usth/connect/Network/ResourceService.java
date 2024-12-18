package vn.edu.usth.connect.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import vn.edu.usth.connect.Models.Moodle.Activity;
import vn.edu.usth.connect.Models.Moodle.Course;

public interface ResourceService {
    @GET("/api/v1/courses/activities-resources")
    Call<List<Course>> getResources(@Header("Authorization") String authHeader);

    @GET("/api/v1/course/{courseId}")
    Call<List<Activity>> getActivities(
            @Header("Authorization") String authHeader,
            @Path("courseId") String courseId);
}
