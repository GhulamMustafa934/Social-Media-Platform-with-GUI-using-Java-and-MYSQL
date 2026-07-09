package Controller;

import View.Database;
import Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FriendController {
    
    private Database database;
    private Statement statement;
    
    public FriendController() {
        database = new Database();
        statement = database.getStatement();
    }
    
    public boolean sendRequest(int userId, int friendId) {
        try {
            String query = "INSERT INTO friends (userId, friendId, status) VALUES ("
                + userId + ", " + friendId + ", 'pending')";
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean acceptRequest(int userId, int friendId) {
        try {
            String query = "UPDATE friends SET status = 'accepted' WHERE userId = " + friendId + " AND friendId = " + userId;
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean rejectRequest(int userId, int friendId) {
        try {
            String query = "DELETE FROM friends WHERE userId = " + friendId + " AND friendId = " + userId;
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean removeFriend(int userId, int friendId) {
        try {
            String query = "DELETE FROM friends WHERE (userId = " + userId + " AND friendId = " + friendId + ") OR (userId = " + friendId + " AND friendId = " + userId + ")";
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<User> getFriends(int userId) {
        ArrayList<User> friends = new ArrayList<>();
        try {
            String query = "SELECT u.* FROM users u "
                + "JOIN friends f ON (f.userId = " + userId + " AND f.friendId = u.id) "
                + "OR (f.friendId = " + userId + " AND f.userId = u.id) "
                + "WHERE f.status = 'accepted'";
            
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                User user = new User();
                user.setID(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                friends.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }
    
    public ArrayList<User> getPendingRequests(int userId) {
        ArrayList<User> requests = new ArrayList<>();
        try {
            String query = "SELECT u.* FROM users u "
                + "JOIN friends f ON f.userId = u.id "
                + "WHERE f.friendId = " + userId + " AND f.status = 'pending'";
            
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                User user = new User();
                user.setID(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                requests.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    public String getFriendStatus(int userId, int friendId) {
        try {
            String query = "SELECT status FROM friends WHERE (userId = " + userId + " AND friendId = " + friendId + ") OR (userId = " + friendId + " AND friendId = " + userId + ")";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "none";
    }
    
    public ArrayList<User> searchUsers(String keyword, int currentUserId) {
        ArrayList<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users WHERE (firstName LIKE '%" + keyword + "%' OR lastName LIKE '%" + keyword + "%') AND id != " + currentUserId;
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                User user = new User();
                user.setID(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}