package Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    
    // Copy image to uploads folder
    public String copyImage(String sourcePath) {
        try {
            File source = new File(sourcePath);
            String fileName = System.currentTimeMillis() + "_" + source.getName();
            
            // Create uploads folder if it doesn't exist
            File uploadDir = new File("uploads");
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            
            String destPath = "uploads/" + fileName;
            File dest = new File(destPath);
            
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Delete image file
    public void deleteImageFile(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
                System.out.println("✅ Image deleted: " + imagePath);
            }
        }
    }
    
    // Save post with image support
    public int savePost(Post post) {
        try {
            String query;
            if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
                // Copy image to uploads folder
                String savedPath = copyImage(post.getImagePath());
                if (savedPath == null) {
                    System.out.println("❌ Failed to copy image!");
                    return -1;
                }
                query = "INSERT INTO posts (content, image_path, userId, dateTime) VALUES ('"
                    + post.getContent() + "', '" + savedPath + "', " 
                    + post.getUser().getID() + ", NOW())";
            } else {
                query = "INSERT INTO posts (content, userId, dateTime) VALUES ('"
                    + post.getContent() + "', " + post.getUser().getID() + ", NOW())";
            }
            
            int result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            
            if (result > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // ✅ NEW: Update post with image
    public boolean updatePostWithImage(int postId, String newContent, String newImagePath) {
        try {
            // First, get existing image path
            String getImageQuery = "SELECT image_path FROM posts WHERE id = " + postId;
            ResultSet rs = statement.executeQuery(getImageQuery);
            String oldImagePath = null;
            if (rs.next()) {
                oldImagePath = rs.getString("image_path");
            }
            
            String query;
            if (newImagePath != null && !newImagePath.isEmpty()) {
                // Delete old image if exists
                deleteImageFile(oldImagePath);
                
                // Copy new image
                String savedPath = copyImage(newImagePath);
                if (savedPath == null) {
                    System.out.println("❌ Failed to copy new image!");
                    return false;
                }
                query = "UPDATE posts SET content = '" + newContent + "', image_path = '" + savedPath + "' WHERE id = " + postId;
            } else {
                query = "UPDATE posts SET content = '" + newContent + "' WHERE id = " + postId;
            }
            
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get all posts with image path
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
                post.setImagePath(rs.getString("image_path"));
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
    
    // Delete post
    public boolean deletePost(int postId) {
        try {
            // First get the image path to delete the file
            String getImageQuery = "SELECT image_path FROM posts WHERE id = " + postId;
            ResultSet rs = statement.executeQuery(getImageQuery);
            if (rs.next()) {
                String imagePath = rs.getString("image_path");
                deleteImageFile(imagePath);
            }
            
            String query = "DELETE FROM posts WHERE id = " + postId;
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Update post content only
    public boolean updatePost(int postId, String newContent) {
        try {
            String query = "UPDATE posts SET content = '" + newContent + "' WHERE id = " + postId;
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get post by ID
    public Post getPostById(int postId) {
        try {
            String query = "SELECT p.*, u.firstName, u.lastName FROM posts p "
                + "JOIN users u ON p.userId = u.id WHERE p.id = " + postId;
            
            ResultSet rs = statement.executeQuery(query);
            
            if (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setContent(rs.getString("content"));
                post.setImagePath(rs.getString("image_path"));
                post.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                
                User user = new User();
                user.setID(rs.getInt("userId"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                post.setUser(user);
                
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}