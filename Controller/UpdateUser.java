package Controller;

import View.Database;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateUser {
    
    private Database database;
    private Statement statement;
    
    public UpdateUser() {
        database = new Database();
        statement = database.getStatement();
    }
    
    public boolean updateUser(int userId, String firstName, String lastName, String email, String password) {
        try {
            String query = "UPDATE users SET firstName = '" + firstName + "', lastName = '" + lastName + "', email = '" + email + "'";
            if (password != null && !password.isEmpty()) {
                query += ", password = '" + password + "'";
            }
            query += " WHERE id = " + userId;
            
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
