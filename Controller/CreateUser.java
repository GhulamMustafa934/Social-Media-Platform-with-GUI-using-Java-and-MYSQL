package Controller;

import View.Database;
import View.Alert;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUser {
    
    private Database database;
    private Statement statement;
    
    public CreateUser() {
        database = new Database();
        statement = database.getStatement();
    }
    
    public boolean registerUser(String firstName, String lastName, String email, String password) {
        try {
            String query = "INSERT INTO users (firstName, lastName, email, password) VALUES ('"
                + firstName + "', '" + lastName + "', '" + email + "', '" + password + "')";
            
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(e.getMessage(), null);
            return false;
        }
    }
}