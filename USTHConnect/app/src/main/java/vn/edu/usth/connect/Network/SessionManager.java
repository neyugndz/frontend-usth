package vn.edu.usth.connect.Network;

public class SessionManager {

    private static SessionManager instance;
    private String token;
    private String studentId;
    private String studyYear;
    private String major;

    // Private constructor to prevent instantiation
    private SessionManager() {}

    // Get the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Store token and student ID
//    public void saveSession(String token, String studentId) {
//        this.token = token;
//        this.studentId = studentId;
//    }

    public void saveSession(String token, String studentId, String studyYear, String major) {
        this.token = token;
        this.studentId = studentId;
        this.studyYear = studyYear;
        this.major = major;
    }

    // Get the token
    public String getToken() {
        return token;
    }

    // Get the student ID
    public String getStudentId() {
        return studentId;
    }

    // Get the study year
    public String getStudyYear() {
        return studyYear;
    }

    // Get the major
    public String getMajor() {
        return major;
    }


    // Clear session data (for logout)
    public void clearSession() {
        this.token = null;
        this.studentId = null;
        this.studyYear = null;
        this.major = null;
    }

    // Check if user is logged in (token is not null)
    public boolean isLoggedIn() {
        return token != null && !token.isEmpty();
    }
}

