package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Post;
import Model.User;
import View.Database;

public class CreatePost {
    
    private Database database;
    private Statement statement;
    
    public CreatePost() {
        database = new Database();
        statement = database.getStatement();
    }
    
    // ✅ UPDATED: Save post and return the generated ID
    public int savePost(Post post) {
        try {
            String query = "INSERT INTO posts (content, userId, dateTime) VALUES ('"
                + post.getContent() + "', " + post.getUser().getID() + ", NOW())";
            
            int result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            
            if (result > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);  // Return the new post ID
                }
            }
            return -1;  // Failed to get ID
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // Get all posts
    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String query = "SELECT p.*, u.firstName, u.lastName FROM posts p "
                + "JOIN users u ON p.userId = u.id ORDER BY p.dateTime DESC";
            
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setContent(rs.getString("content"));
                post.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                
                User user = new User();
                user.setID(rs.getInt("userId"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                post.setUser(user);
                
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
