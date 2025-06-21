package game;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final String DB_FOLDER = "data";
    private static final String DB_FILE = "Question.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_FOLDER + File.separator + DB_FILE;

    //Membuka koneksi ke database SQLite
    public static Connection connect() {
        ensureDatabaseFolderExists();

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("✅ Connected to SQLite database at: " + DB_URL);

            return conn;
        } catch (SQLException e) {
            System.out.println("❌ SQL Error saat koneksi: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("❌ SQLite JDBC driver tidak ditemukan: " + e.getMessage());
        }

        return null;
    }

    //Membuat folder database jika belum ada.
    private static void ensureDatabaseFolderExists() {
        File folder = new File(DB_FOLDER);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("📁 Folder database dibuat: " + DB_FOLDER);
            } else {
                System.out.println("⚠️ Gagal membuat folder database: " + DB_FOLDER);
            }
        }
    }
}
