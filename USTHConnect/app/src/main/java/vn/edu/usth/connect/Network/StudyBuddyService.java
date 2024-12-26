package vn.edu.usth.connect.Network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;

public interface StudyBuddyService {
    @POST("/api/v1/studyBuddy/save")
    Call<Void> saveStudyBuddy(
            @Header("Authorization") String authHeader,
            @Body StudyBuddy studyBuddy);

    @GET("/api/v1/students/sipCredentials")
    Call<Map<String, String>> getSipCredentials(@Header("Authorization") String token);

    @GET("/api/v1/students/isRegistered")
    Call<Map<String, Boolean>> isRegistered(@Header("Authorization") String token);
}
