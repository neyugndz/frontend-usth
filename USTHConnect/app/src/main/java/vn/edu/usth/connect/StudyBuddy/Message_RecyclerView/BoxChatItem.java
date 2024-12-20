package vn.edu.usth.connect.StudyBuddy.Message_RecyclerView;

public class BoxChatItem {

    String name;
    String username; // Username for SIP Account
    String password; // Password for SIP Account

    public BoxChatItem(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
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
