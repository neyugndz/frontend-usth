package vn.edu.usth.connect.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import vn.edu.usth.connect.Models.Student;

public interface StudentService {
    @GET("/api/v1/students/{id}")
    Call<Student> getStudentProfile(
            @Header("Authorization") String authorizationHeader,
            @Path("id") String id
    );
}
