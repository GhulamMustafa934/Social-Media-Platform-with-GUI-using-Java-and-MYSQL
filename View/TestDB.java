package View;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        Database db = new Database();
        
        if (db.getStatement() != null) {
            System.out.println("✅ Connection is working!");
        } else {
            System.out.println("❌ Connection failed!");
        }
    }
}
