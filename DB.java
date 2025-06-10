import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:mathrogue.db");
        } catch (SQLException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
            return null;
        }
    }
}
