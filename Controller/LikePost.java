package Controller;

import View.Database;
import Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LikePost {
    
    private Database database;
    private Statement statement;
    
    public LikePost() {
        database = new Database();
        statement = database.getStatement();
    }
    
    // Add like
    public boolean addLike(int postId, int userId) {
        try {
            String query = "INSERT INTO likes (postId, userId) VALUES (" + postId + ", " + userId + ")";
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Remove like
    public boolean removeLike(int postId, int userId) {
        try {
            String query = "DELETE FROM likes WHERE postId = " + postId + " AND userId = " + userId;
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check if user already liked a post
    public boolean hasLiked(int postId, int userId) {
        try {
            String query = "SELECT * FROM likes WHERE postId = " + postId + " AND userId = " + userId;
            ResultSet rs = statement.executeQuery(query);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get likes count for a post
    public int getLikesCount(int postId) {
        try {
            String query = "SELECT COUNT(*) as count FROM likes WHERE postId = " + postId;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
