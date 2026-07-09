package Controller;

import View.Database;
import Model.Comment;
import Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommentPost {
    
    private Database database;
    private Statement statement;
    
    public CommentPost() {
        database = new Database();
        statement = database.getStatement();
    }
    
    // Add comment
    public boolean addComment(int postId, int userId, String content) {
        try {
            String query = "INSERT INTO comments (postId, userId, content) VALUES ("
                + postId + ", " + userId + ", '" + content + "')";
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete comment
    public boolean deleteComment(int commentId) {
        try {
            String query = "DELETE FROM comments WHERE id = " + commentId;
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ✅ Update comment (FIXED)
    public boolean updateComment(int commentId, String newContent) {
        try {
            String query = "UPDATE comments SET content = '" + newContent + "' WHERE id = " + commentId;
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ✅ Check if user owns comment
    public boolean isCommentOwner(int commentId, int userId) {
        try {
            String query = "SELECT * FROM comments WHERE id = " + commentId + " AND userId = " + userId;
            ResultSet rs = statement.executeQuery(query);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get comments for a post
    public ArrayList<Comment> getCommentsForPost(int postId) {
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            String query = "SELECT c.*, u.firstName, u.lastName FROM comments c "
                + "JOIN users u ON c.userId = u.id "
                + "WHERE c.postId = " + postId + " ORDER BY c.dateTime DESC";
            
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setContent(rs.getString("content"));
                comment.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                
                User user = new User();
                user.setID(rs.getInt("userId"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                comment.setUser(user);
                
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    
    // Get comment count for a post
    public int getCommentCount(int postId) {
        try {
            String query = "SELECT COUNT(*) as count FROM comments WHERE postId = " + postId;
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
