package game;

import java.util.*;

public class QuestionLoader {

    private static final Random rand = new Random();

    // Pemetaan template ID ke variabel yang dibutuhkan
    private static final Map<String, List<String>> QUESTION_VARS = new HashMap<>();

    static {
        QUESTION_VARS.put("q1", Arrays.asList("x", "y", "z", "a", "b"));
        QUESTION_VARS.put("q2", Arrays.asList("x", "y", "z"));
        // Tambahkan lagi jika kamu punya template ID lain
    }

    //Randomisasi nilai variabel untuk template tertentu.
    public static Map<String, Object> randomizeVars(String qid) {
        Map<String, Object> vars = new HashMap<>();
        List<String> keys = QUESTION_VARS.getOrDefault(qid, new ArrayList<>());

        for (String key : keys) {
            vars.put(key, rand.nextInt(10) + 1); // nilai antara 1–10
        }

        return vars;
    }

    //Mengisi template dengan variabel.
    public static String fillTemplate(String template, Map<String, Object> vars) {
        String result = template;
        for (Map.Entry<String, Object> entry : vars.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return result;
    }

    //Mengevaluasi string matematika dan mengembalikan jawabannya.
    public static int getAnswer(String qid, Map<String, Object> vars) {
        String template = getTemplateById(qid); // optional jika kamu ingin ambil lagi template
        String expression = fillTemplate(template, vars);

        try {
            return ExpressionEvaluator.evaluate(expression);
        } catch (Exception e) {
            System.out.println("❌ Error evaluating expression: " + expression);
            e.printStackTrace();
            return -1;
        }
    }

    //ambil template dari kode hardcoded (bisa diganti dengan DB juga).
    private static String getTemplateById(String qid) {
        switch (qid) {
            case "q1": return "({x} - {b}) + ({a} * {y} - {z})";
            case "q2": return "({x} + {y}) * {z}";
            default: return "(0 + 0)";
        }
    }
}
