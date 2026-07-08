package Controller;

import View.Database;
import Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginUser {
    
    private Database database;
    private Statement statement;
    
    public LoginUser() {
        database = new Database();
        statement = database.getStatement();
    }
    
    public User loginUser(String email, String password) {
        try {
            String query = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
            ResultSet rs = statement.executeQuery(query);
            
            if (rs.next()) {
                User user = new User();
                user.setID(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
