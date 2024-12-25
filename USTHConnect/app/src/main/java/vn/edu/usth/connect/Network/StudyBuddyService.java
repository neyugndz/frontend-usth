package vn.edu.usth.connect.Network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;

public interface StudyBuddyService {
    @POST("/api/v1/studyBuddy/save")
    Call<Void> saveStudyBuddy(
            @Header("Authorization") String authHeader,
            @Body StudyBuddy studyBuddy);
}
