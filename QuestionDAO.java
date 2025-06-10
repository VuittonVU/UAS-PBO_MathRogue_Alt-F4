import java.sql.*;
import java.util.*;

public class QuestionDAO {
    public static Question getRandomFilledQuestion() {
        String sql = "SELECT question_id, template FROM math_questions ORDER BY RANDOM()";

        try (Connection conn = DB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String qid = rs.getString("question_id");
                String tmpl = rs.getString("template");

                Map<String, Object> vars = QuestionLoader.randomizeVars(qid);
                String filled = QuestionLoader.fillTemplate(tmpl, vars);
                int answer = QuestionLoader.getAnswer(qid, vars);

                return new Question(filled, answer);
            }
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
        }

        return new Question("1 + 1", 2); // fallback
    }

    public static void initTable() {
        String create = """
            CREATE TABLE IF NOT EXISTS math_questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question_id TEXT,
                type TEXT,
                template TEXT
            );
        """;

        try (Connection conn = DB.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(create);
            System.out.println("✅ Table check complete.");
        } catch (SQLException e) {
            System.out.println("❌ Init table error: " + e.getMessage());
        }
    }
}
