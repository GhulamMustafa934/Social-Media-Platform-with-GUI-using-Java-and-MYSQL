package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {

    private int ID;
    private String content;
    private LocalDateTime dateTime;
    private User user;
    private ArrayList<Comment> comments;
    private ArrayList<User> likes;

    public Post() {
        // ✅ Initialize lists to avoid null
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public Post(String content, User user) {
        this.content = content;
        this.user = user;
        this.dateTime = LocalDateTime.now();
        this.comments = new ArrayList<>();  // ✅ Initialize
        this.likes = new ArrayList<>();     // ✅ Initialize
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    // ✅ Add these for compatibility
    public int getId() {
        return ID;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }
}