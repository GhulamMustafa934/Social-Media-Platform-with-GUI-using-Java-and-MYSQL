package Model;  // ✅ Changed to Model

import java.time.LocalDateTime;
import Model.User;  // ✅ ADD THIS

public class Comment {

    private int ID;
    private String content;
    private User user;
    private LocalDateTime dateTime;

    public Comment() {}

    public Comment(String content, User user) {
        this.content = content;
        this.user = user;
        this.dateTime = LocalDateTime.now();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}