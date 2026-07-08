package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    // ✅ XAMPP credentials
    private String user = "root";
    private String pass = "";
    private String url = "jdbc:mysql://localhost:3306/socialmedia";
    private Statement statement;

    public Database() {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            System.out.println("✅ Database connected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Database connection failed!");
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
