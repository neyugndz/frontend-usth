package vn.edu.usth.connect.StudyBuddy.Message_RecyclerView;

public class BoxChatItem {

    private String name;
    private String buddyId;
    private String username; // Username for SIP Account
    private String password; // Password for SIP Account

    public BoxChatItem(String name, String buddyId, String username, String password) {
        this.name = name;
        this.buddyId = buddyId;
        this.username = username;
        this.password = password;
    }

    public String getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(String buddyId) {
        this.buddyId = buddyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
